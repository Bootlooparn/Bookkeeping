delimiter #
create trigger record_insert after insert on balances
for each row
begin insert into records(date, name, value, action) values (now(), new.name, new.value, "INSERT");
end #

create trigger record_update after update on balances
for each row
begin insert into records(date, name, value, action) values (now(), new.name, new.value, "UPDATE");
end #

delimiter ;