package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PowerDataService {

    private static final Map<String, Function<PowerDataEntity, BigDecimal>> POWER_DATA_SUPPLIERS = new HashMap<>();

    static {
        POWER_DATA_SUPPLIERS.put("pvpower", PowerDataEntity::getSolarPower);
        POWER_DATA_SUPPLIERS.put("loadpower", PowerDataEntity::getLoadPower);
        POWER_DATA_SUPPLIERS.put("collectedEnergyToday", PowerDataEntity::getGeneratedEnergyToday);
        POWER_DATA_SUPPLIERS.put("consumedEnergyToday", PowerDataEntity::getConsumedEnergyToday);
        POWER_DATA_SUPPLIERS.put("batteryVoltage", PowerDataEntity::getBatteryVoltage);
    }

    private final PowerDataRepository powerDataRepository;
    private final DailySummaryRepository dailySummaryRepository;
    private final MonthlySummaryRepository monthlySummaryRepository;

    public PowerDataEntity getCurrentPowerData() {
        return powerDataRepository.findFirstByOrderByDateTimeDesc();
    }

    public PowerDataEntity savePowerData(PowerDataEntity powerData) {
        LocalDate date = powerData.getDateTime().toLocalDate();
        DailySummaryEntity dailySummary = dailySummaryRepository.findByDate(date);
        if (dailySummary == null) {
            dailySummary = new DailySummaryEntity();
            dailySummary.setDate(date);
        }
        DailySummaryFiller dailySummaryFiller = new DailySummaryFiller(powerData, dailySummary);

        dailySummary.setConsumedEnergy(dailySummaryFiller.getMax(PowerDataEntity::getConsumedEnergyToday, DailySummaryEntity::getConsumedEnergy));
        dailySummary.setGeneratedEnergy(dailySummaryFiller.getMax(PowerDataEntity::getGeneratedEnergyToday, DailySummaryEntity::getGeneratedEnergy));

        return powerDataRepository.save(powerData);
    }

    private BigDecimal parseValue(String s) {
        return BigDecimal.valueOf(Integer.valueOf(s, 16), 2);
    }

    public List<PowerDataEntity> getPowerDataForLast24Hours() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        return powerDataRepository.findAllByDateTimeAfter(now.minusDays(1).toLocalDateTime());
    }

    public List<DailySummaryEntity> getDailySummary() {
        return dailySummaryRepository.findAll();
    }

    public List<MonthlySummaryEntity> getOverallSummary() {
        return monthlySummaryRepository.findAll();
    }

    public void delete(long id) {
        powerDataRepository.deleteById(id);
    }

    public Map<String, List<Measurement>> getMeasurementsByNames(List<String> measurementNames) {
        List<PowerDataEntity> powerDataForLast24Hours = getPowerDataForLast24Hours();
        Map<String, List<Measurement>> measurementsByName = new LinkedHashMap<>();
        for (String name : measurementNames) {
            Function<PowerDataEntity, BigDecimal> getter = POWER_DATA_SUPPLIERS.get(name);
            List<Measurement> measurements = getMeasurements(powerDataForLast24Hours, getter);
            measurementsByName.put(name, measurements);
        }
        return measurementsByName;
    }

    private List<Measurement> getMeasurements(List<PowerDataEntity> powerDataForLast24Hours, Function<PowerDataEntity, BigDecimal> getter) {
        List<Measurement> measurements = new ArrayList<>();
        BigDecimal lastValue = null;
        for (PowerDataEntity pd : powerDataForLast24Hours) {
            BigDecimal currentValue = getter.apply(pd);
            if (currentValue != null && (lastValue == null || !lastValue.equals(currentValue))) {
                measurements.add(new Measurement(pd.getDateTime(), currentValue));
                lastValue = currentValue;
            }
        }
        return measurements;
    }

    @AllArgsConstructor
    private class DailySummaryFiller {
        private final PowerDataEntity powerData;
        private final DailySummaryEntity dailySummary;

        private BigDecimal getMax(Function<PowerDataEntity, BigDecimal> getter1,
                                  Function<DailySummaryEntity, BigDecimal> getter2) {
            BigDecimal powerDataValue = getter1.apply(powerData);
            BigDecimal dailySummaryValue = getter2.apply(dailySummary);
            if (powerDataValue != null && dailySummaryValue != null) {
                return powerDataValue.max(dailySummaryValue);
            }
            if (powerDataValue != null) {
                return powerDataValue;
            }
            return dailySummaryValue;
        }
    }
}
