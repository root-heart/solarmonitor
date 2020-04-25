create sequence hibernate_sequence;

create table power_data
(
    id                            bigint not null primary key,
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

create table summary_data
(
    id         bigint not null primary key,
    day        date unique,
    power_data text
);
