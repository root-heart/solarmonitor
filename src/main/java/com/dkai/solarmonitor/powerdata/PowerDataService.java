package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public PowerDataEntity getCurrentPowerData() {
        return powerDataRepository.findLatest();
    }

    public PowerDataEntity savePowerData(PowerDataEntity powerData) {
        return powerDataRepository.save(powerData);
    }

    private BigDecimal parseValue(String s) {
        return BigDecimal.valueOf(Integer.valueOf(s, 16), 2);
    }

    public List<PowerDataEntity> getPowerDataForLast24Hours() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        return powerDataRepository.getDataOfLast24Hours(now.minusDays(1).toLocalDateTime());
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

}
