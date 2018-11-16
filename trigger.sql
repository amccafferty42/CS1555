
create or replace trigger AutoProductID
before insert
on product
for each row
begin
    select max(auction_id)+1 into :new.auction_id
    from product;
end;
/

create or replace trigger AutoBidNumber
before insert
on bidlog
for each row
begin
    select max(bidsn)+1 into :new.bidsn
    from bidlog;
end;
/

create or replace trigger CreateCategory
before insert
on belongsto
for each row
declare
    catCount number;
begin
    select count(:new.category) into catCount from category where name=:new.category;
    if catCount = 0 then
        insert into category values (:new.category, null);
    end if;
end;
/

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
    --check if auction should be closed and close it
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



