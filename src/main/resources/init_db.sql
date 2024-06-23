create sequence power_data_id_seq increment by 50;

alter sequence power_data_id_seq owner to postgres;
alter sequence power_data_id_seq owned by power_data.id;

create table power_data
(
    id                            bigint default nextval('power_data_id_seq'::regclass) not null primary key,
    date_time                     timestamp,
    solar_voltage                 numeric(10, 2),
    solar_current                 numeric(10, 2),
    solar_power                   numeric(10, 2),
    battery_voltage               numeric(10, 2),
    battery_current               numeric(10, 2),
    battery_power                 numeric(10, 2),
    load_voltage                  numeric(10, 2),
    load_current                  numeric(10, 2),
    load_power                    numeric(10, 2),
    maximum_input_voltage_today   numeric(10, 2),
    minimum_input_voltage_today   numeric(10, 2),
    maximum_battery_voltage_today numeric(10, 2),
    minimum_battery_voltage_today numeric(10, 2),
    consumed_energy_today         numeric(10, 2),
    consumed_energy_this_month    numeric(10, 2),
    consumed_energy_this_year     numeric(10, 2),
    total_consumed_energy         numeric(10, 2),
    generated_energy_today        numeric(10, 2),
    generated_energy_this_month   numeric(10, 2),
    generated_energy_this_year    numeric(10, 2),
    total_generated_energy        numeric(10, 2)
);

alter table power_data
    owner to postgres;

create index idx_power_data_date_time
    on power_data (date_time);


create sequence daily_summary_id_seq increment by 50;

alter sequence daily_summary_id_seq owner to postgres;
alter sequence daily_summary_id_seq owned by power_data.id;

create table daily_summary
(
    id                      bigint default nextval('daily_summary_id_seq'::regclass) not null primary key,
    date                    date,
    generated_energy        numeric(10, 2),
    consumed_energy         numeric(10, 2),
    maximum_input_voltage   numeric(10, 2),
    minimum_input_voltage   numeric(10, 2),
    maximum_battery_voltage numeric(10, 2),
    minimum_battery_voltage numeric(10, 2)
);

alter table daily_summary
    owner to postgres;

create index daily_summary_date on daily_summary (date);