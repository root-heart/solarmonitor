--drop table power_data;
create table power_data (
	id bigint not null primary key,
	date_time timestamp,
	solar_voltage numeric(10, 2),
    solar_current numeric(10, 2),
    solar_power numeric(10, 2),
    battery_voltage numeric(10, 2),
    battery_current numeric(10, 2),
    battery_power numeric(10, 2)
);
