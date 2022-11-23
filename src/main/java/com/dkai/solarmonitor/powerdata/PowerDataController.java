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

    @GetMapping("/summarized/{year}")
    public List<SummarizedPowerData> getSummarizedPowerData(@PathVariable("year") int year) {
        String sql = "select extract(year from date_time)     as year, " +
                     " extract(month from date_time)    as month, " +
                     " extract(day from date_time)      as day, " +
                     " max(generated_energy_today)      as energy_this_day, " +
                     " max(generated_energy_this_month) as energy_this_month, " +
                     " max(generated_energy_this_year)  as energy_this_year, " +
                     " max(total_generated_energy)      as total_energy, " +
                     " max(solar_power)                 as max_power " +
                     " from power_data " +
                     " where extract(year from date_time) = ? " +
                     " group by extract(year from date_time), " +
                     "         extract(month from date_time), " +
                     "         extract(day from date_time) " +
                     " order by 1, 2, 3";
        var result = new ArrayList<SummarizedPowerData>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, year);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    var summarizedPowerData = new SummarizedPowerData(
                            LocalDate.of(rs.getInt("year"), rs.getInt("month"), rs.getInt("day")),
                            rs.getBigDecimal("energy_this_day"),
                            rs.getBigDecimal("energy_this_month"),
                            rs.getBigDecimal("energy_this_year"),
                            rs.getBigDecimal("total_energy"),
                            rs.getBigDecimal("max_power"));
                    result.add(summarizedPowerData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
