/* GROUP 16 TRIGGER.SQL */

create or replace trigger trig_bidTimeUpdate
after insert
on bidlog
for each row
begin
    update ourSysDate
    set c_date = c_date + (5/24/60/60);
end;
/



--WONT ALLOW BID TO BE ENTERED IF THE INIT BID ISNT (>= min_Price or >HIGHEST CUR BID AMOUNT)
--IF IT allows insert it updates the highest bid amount into Product(amount)
create or replace trigger trig_updateHighBid
before insert
on bidlog
for each row
DECLARE

minPrice NUMBER;
amount NUMBER;
amountSmall EXCEPTION;

begin
    select min_price into minPrice from product where auction_id = :new.auction_id;
    select amount into amount from product where auction_id = :new.auction_id;
    
    if (minPrice <= :new.amount AND coalesce(amount,0) < :new.amount)then
        update product
        set amount = :new.amount
        where auction_id = :new.auction_id;
    
    else
        RAISE amountSmall;
    end if;
    
    exception
        when amountSmall
        then
        RAISE_APPLICATION_ERROR(-20001, 'Error(BID ENTRY): Bid Is Not High Enough!');
end;
/

create or replace trigger trig_closeAuctions
after update
on ourSysDate
for each row
begin
    update product
    set status = 'close'
    where :new.c_date > start_date + number_of_days;
end;
/



CREATE OR REPLACE FUNCTION func_productCount(x in integer, c in varchar2)return integer
IS
    a_count integer;
    pastDate date;
    curDate date;
begin
    select c_date into curdate from ourSysDATE;
    pastDate := ( ADD_MONTHS (curDate, -x));

    select count(auction_id) into a_count
    from Product p natural join (select auction_id from BelongsTo where category = c)b
    where p.sell_date > pastDate;
    
    return a_count;
end;
/

CREATE OR REPLACE FUNCTION fucn_bidCount(x in integer, u in varchar2)return integer
IS
    a_count integer;
    pastDate date;
    curDate date;
begin
    select c_date into curdate from ourSysDATE;
    pastDate := ( ADD_MONTHS (curDate, -x));

    select count(bidsn) into a_count
    from (select bidsn, bid_time from bidlog where bidder = u)
    where bid_time > pastDate;
    
    return a_count;
end;
/

CREATE OR REPLACE FUNCTION func_buyingAmount(x in integer, u in varchar2)return integer
IS
    a_sum integer;
    pastDate date;
    curDate date;
begin
    select c_date into curdate from ourSysDATE;
    pastDate := ( ADD_MONTHS (curDate, -x));

    select sum(amount) into a_sum
    from (select amount, sell_date from Product where buyer = u)
    where sell_date > pastDate;
    
    return a_sum;
end;
/


CREATE OR REPLACE FUNCTION initSellDate(x in NUMBER) return date
is
firstDate date;

begin
select (c_date + x) into firstDate from ourSysDate;

return firstDate;
end;
/


CREATE OR REPLACE FUNCTION getCurDate return date
is
curDate date;

begin
select c_date into curDate from ourSysDate;

return curDate;
end;
/

create or replace view view_parentCat as
select parent_category from category;

create or replace view view_catName as
select name from category;

create or replace procedure proc_putProduct (sellerID in varchar2, prod_name in varchar2, cat_names in varchar2, num_days in integer, des in varchar2, minPrice in integer)
is
    new_auction_id integer;

begin
    --initialSellDate := initSellDate(numD);
    
    select max(auction_id)+1 into new_auction_id from product;
    insert into product(auction_id, name, description, start_date, seller, number_of_days, status, sell_date, min_price)
    values(new_auction_id, prod_name, des, getCurDate, sellerID, num_days, 'under auction', initSellDate(num_days), minPrice);

    FOR i IN
       (SELECT level,
          trim(regexp_substr(cat_names, '[^,]+', 1, LEVEL)) str
        FROM dual
          CONNECT BY regexp_substr(cat_names , '[^,]+', 1, LEVEL) IS NOT NULL 
          AND (regexp_substr(cat_names , '[^,]+', 1, LEVEL) not in (select parent_category from category))
          AND (regexp_substr(cat_names , '[^,]+', 1, LEVEL) in (select name from category)))
        
    LOOP
        insert into belongsto values(new_auction_id, i.str);
    END LOOP;

end;
/

/*
select auction_id, name from product order by auction_id;
select * from category;

call proc_putProduct('skatasdes', 'asdfasdf', 1, 'loud');

