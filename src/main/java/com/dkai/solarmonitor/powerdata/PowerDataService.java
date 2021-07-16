package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PowerDataService {

    private static final Map<String, Function<PowerData, BigDecimal>> POWER_DATA_SUPPLIERS = new HashMap<>();

    static {
        POWER_DATA_SUPPLIERS.put("pvpower", PowerData::getSolarPower);
        POWER_DATA_SUPPLIERS.put("loadpower", PowerData::getLoadPower);
        POWER_DATA_SUPPLIERS.put("collectedEnergyToday", PowerData::getGeneratedEnergyToday);
        POWER_DATA_SUPPLIERS.put("consumedEnergyToday", PowerData::getConsumedEnergyToday);
        POWER_DATA_SUPPLIERS.put("batteryVoltage", PowerData::getBatteryVoltage);
    }

    private final PowerDataRepository powerDataRepository;

    public PowerData getCurrentPowerData() {
        return powerDataRepository.findLatest();
    }

    public PowerData savePowerData(PowerData powerData) {
        return powerDataRepository.save(powerData);
    }

    private BigDecimal parseValue(String s) {
        return BigDecimal.valueOf(Integer.valueOf(s, 16), 2);
    }

    public List<PowerData> getPowerDataForLast24Hours() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        return powerDataRepository.getDataOfLast24Hours(now.minusDays(1).toLocalDateTime());
    }

    public void delete(long id) {
        powerDataRepository.deleteById(id);
    }

    public Map<String, Map<LocalDateTime, BigDecimal>> getMeasurementsByNames(List<String> measurementNames) {
        List<PowerData> powerDataForLast24Hours  = getPowerDataForLast24Hours();
        Map<String, Map<LocalDateTime, BigDecimal>> measurementsByName = new LinkedHashMap<>();
        for (String name : measurementNames) {
            Function<PowerData, BigDecimal> getter = POWER_DATA_SUPPLIERS.get(name);
            Map<LocalDateTime, BigDecimal> abc = getMeasurements(powerDataForLast24Hours, getter);
            measurementsByName.put(name, abc);
        }
        return measurementsByName;
    }

    private Map<LocalDateTime, BigDecimal> getMeasurements(List<PowerData> powerDataForLast24Hours, Function<PowerData, BigDecimal> getter) {
        Map<LocalDateTime, BigDecimal> measurements = new LinkedHashMap<>();
        BigDecimal lastValue = null;
        for (PowerData pd : powerDataForLast24Hours) {
            BigDecimal currentValue = getter.apply(pd);
            if (currentValue != null && (lastValue == null || !lastValue.equals(currentValue))) {
                measurements.put(pd.getDateTime(), currentValue);
                lastValue = currentValue;
            }
        }
        return measurements;
    }

}
