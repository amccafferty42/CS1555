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
/*
create or replace trig_bidTimeUpdate
after insert
on bidlog
begin
    --increment system time by 5 seconds
end;
*/

create or replace trig_updateHighBid
after insert
on bidlog
for each row
begin
    update product
    set amount = :old.amount
    where auction_id = :old.auction_id;
end;
/

/*
create or replace trigger trig_closeAuctions
after update
on ourSysDate
begin
    --check if auction should be closed and close it
end;
*/




