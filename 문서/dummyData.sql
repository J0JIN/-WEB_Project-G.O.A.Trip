use goatrip;
SELECT * FROM location;
SELECT * FROM schedule;
SELECT * FROM schedule_content;


insert into schedule values (1,"ssafy","test01 일정",1);
insert into schedule_content values (1,1,1,now(),now());

insert into location_type values(1,"Type Location");

insert into location values(1,1,"test01","test01 content","test01 addr", 1.01,1.01);

# delete from schedule_content where sch_content_id = 1;

select sch_content_id, lo.location_id, sch_id, sch_content_date_end, sch_content_date_start, 
	lo.location_type, lo.type_name, lo.location_title, lo.location_content, lo.location_addr, lo.location_latitude, lo.location_longitude
	from schedule_content as sc left join 
    (select location_type.location_type, location_id, type_name, location_title, location_content, location_addr, location_latitude, location_longitude 
    from location left join location_type 
    on location.location_type = location_type.location_type
    ) as lo
    on sc.location_id = lo.location_id
    where sch_content_id = 1;
