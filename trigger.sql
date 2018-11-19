create or replace trigger trig_bidTimeUpdate
after insert
on bidlog
for each row
begin
    update ourSysDate
    set c_date = c_date + (5/24/60/60);
end;
/


create or replace trigger trig_updateHighBid
after insert
on bidlog
for each row
begin
    update product
    set amount = :new.amount
    where auction_id = :new.auction_id;
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

create or replace procedure proc_putProduct (prod_name in varchar2, cat_name in varchar2, num_days in integer, des in varchar2)
as
    new_auction_id integer;
    curr_date date;
    dummy varchar2(20);
begin
    select c_date into curr_date from ourSysDate;
    select max(auction_id)+1 into new_auction_id from product;
    
    insert into product(auction_id, name, description, start_date, number_of_days, status)
    values(new_auction_id, prod_name, des, curr_date, num_days, 'under auction');
    
    select name into dummy
    from category
    where name = cat_name;
exception
    when no_data_found then
    insert into category(name) values(cat_name);
end;
/
/*
select auction_id, name from product order by auction_id;
select * from category;

call proc_putProduct('skatasdes', 'asdfasdf', 1, 'loud');

