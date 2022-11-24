package com.dkai.solarmonitor.powerdata;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/powerData")
@RequiredArgsConstructor
public class PowerDataController {


    private final PowerDataService powerDataService;

    private final ChargeControllerRegisterConverter chargeControllerRegisterConverter;

    private final DataSource dataSource;

    @GetMapping
    public PowerDataEntity getCurrentPowerData() {
        return powerDataService.getCurrentPowerData();
    }

    @GetMapping("/last24Hours")
    public List<PowerDataEntity> getPowerDataForLast24Hours() {
        return powerDataService.getPowerDataForLast24Hours();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NonNull Long id) {
        powerDataService.delete(id);
    }

    @PostMapping
    public PowerDataEntity postPowerData(@RequestBody ChargeControllerRegistersDto dto) {
        return powerDataService.savePowerData(chargeControllerRegisterConverter.convert(dto));
    }

    @GetMapping("/measurements")
    public Map<String, List<Measurement>> getMeasurements(@RequestParam List<String> names) {
        return powerDataService.getMeasurementsByNames(names);
    }

    @GetMapping("/summarized")
    public SummarizedPowerData getSummarizedPowerData() {
        String dailySummarySql = "select extract(year from date_time)     as year, " +
                                 " extract(month from date_time)    as month, " +
                                 " extract(day from date_time)      as day, " +
                                 " max(generated_energy_today)      as energy_this_day " +
                                 " from power_data " +
                                 " where date_time >= current_timestamp - interval '1 month' " +
                                 " group by extract(year from date_time), " +
                                 "         extract(month from date_time), " +
                                 "         extract(day from date_time) " +
                                 " order by 1, 2, 3";
        String monthlySummarySql = "select extract(year from date_time)     as year, " +
                                   " extract(month from date_time)    as month, " +
                                   " max(generated_energy_this_month) as energy_this_month " +
                                   " from power_data " +
                                   " group by extract(year from date_time), " +
                                   "         extract(month from date_time) " +
                                   " order by 1, 2";

        var dailySummaryList = new ArrayList<SummarizedPowerData.DailySummary>();
        var monthlySummaryList = new ArrayList<SummarizedPowerData.MonthlySummary>();
        try (var conn = dataSource.getConnection();
             var stmtDailySummary = conn.prepareStatement(dailySummarySql);
             var stmtMonthlySummary = conn.prepareStatement(monthlySummarySql)
        ) {
            try (var rs = stmtDailySummary.executeQuery()) {
                while (rs.next()) {
                    var dailySummary = new SummarizedPowerData.DailySummary(
                            rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getInt("day"),
                            rs.getBigDecimal("energy_this_day"));
                    dailySummaryList.add(dailySummary);
                }
            }
            try (var rs = stmtMonthlySummary.executeQuery()) {
                while (rs.next()) {
                    var monthlySummary = new SummarizedPowerData.MonthlySummary(
                            rs.getInt("year"),
                            rs.getInt("month"),
                            rs.getBigDecimal("energy_this_month"));
                    monthlySummaryList.add(monthlySummary);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new SummarizedPowerData(
                dailySummaryList,
                monthlySummaryList
        );
    }
}
