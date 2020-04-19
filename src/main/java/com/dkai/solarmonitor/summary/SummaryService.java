package com.dkai.solarmonitor.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

// TODO this class is really hacked together, needs heavy refactoring!
@Service
@RequiredArgsConstructor
public class SummaryService {
    private static final String QUERY_SUMMARY_DATA =
            "select to_char(date_time, 'YYYYMMDDHH24') as begin, "
                    + " min(solar_power) as min_solar, "
                    + " max(solar_power) as max_solar, "
                    + " avg(solar_power) as avg_solar, "
                    + " min(battery_voltage) as min_battery_voltage, "
                    + " max(battery_voltage) as max_battery_voltage, "
                    + " avg(battery_voltage) as avg_battery_voltage, "
                    + " min(battery_power) as min_battery_power, "
                    + " max(battery_power) as max_battery_power, "
                    + " avg(battery_power) as avg_battery_power, "
                    + " min(load_power) as min_load, "
                    + " max(load_power) as max_load, "
                    + " avg(load_power) as avg_load "
                    + " from power_data "
                    + " where date_time between :from and :to "
                    + " group by to_char(date_time, 'YYYYMMDDHH24') "
                    + " order by to_char(date_time, 'YYYYMMDDHH24')";

    private static final DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHH");

    private final EntityManager entityManager;

    public List<SummaryData> getForDay(LocalDate day) {
        List<?> resultList = entityManager
                .createNativeQuery(QUERY_SUMMARY_DATA)
                .setParameter("from", day)
                .setParameter("to", day.plusDays(1))
                .getResultList();
        return resultList.stream()
                .map(Object[].class::cast)
                .map(this::mapFromResultRow)
                .collect(Collectors.toList());
    }

    public List<SummaryData> getForPast24Hours() {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime intervalEnd = today.atTime(time.getHour(), 0);
        List<?> resultList = entityManager
                .createNativeQuery(QUERY_SUMMARY_DATA)
                .setParameter("from", intervalEnd.minusDays(1))
                .setParameter("to", intervalEnd)
                .getResultList();
        return resultList.stream()
                .map(Object[].class::cast)
                .map(this::mapFromResultRow)
                .collect(Collectors.toList());
    }

    private SummaryData mapFromResultRow(Object[] resultRow) {
        LocalDateTime start = LocalDateTime.parse((String) resultRow[0], f);
        LocalDateTime end = start.plusHours(1);
        return SummaryData.builder()
                .start(start)
                .end(end)
                .minSolarPower((BigDecimal) resultRow[1])
                .maxSolarPower((BigDecimal) resultRow[2])
                .avgSolarPower((BigDecimal) resultRow[3])
                .minBatteryVoltage((BigDecimal) resultRow[4])
                .maxBatteryVoltage((BigDecimal) resultRow[5])
                .avgBatteryVoltage((BigDecimal) resultRow[6])
                .minBatteryPower((BigDecimal) resultRow[7])
                .maxBatteryPower((BigDecimal) resultRow[8])
                .avgBatteryPower((BigDecimal) resultRow[9])
                .minLoadPower((BigDecimal) resultRow[10])
                .maxLoadPower((BigDecimal) resultRow[11])
                .avgLoadPower((BigDecimal) resultRow[12])
                .build();
    }
}


