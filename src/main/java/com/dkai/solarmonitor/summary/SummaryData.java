package com.dkai.solarmonitor.summary;

import com.dkai.solarmonitor.powerdata.PowerDataEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Entity
public class SummaryData {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate day;
    @Convert(converter = PowerDataListToJsonStringConverter.class)
    @Column(length = 1000)
    private List<PowerDataEntity> powerData;

    public BigDecimal getGeneratedEnergy() {
        return getMax(PowerDataEntity::getGeneratedEnergyToday);
    }

    public BigDecimal getConsumedEnergy() {
        return getMax(PowerDataEntity::getConsumedEnergyToday);
    }

    public BigDecimal getMinBatteryVoltage() {
        return getMin(PowerDataEntity::getBatteryVoltage);
    }

    public BigDecimal getMaxBatteryVoltage() {
        return getMax(PowerDataEntity::getBatteryVoltage);
    }

    private BigDecimal getMax(Function<PowerDataEntity, BigDecimal> valueGetMethod) {
        return powerData.stream()
                .map(valueGetMethod)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMin(Function<PowerDataEntity, BigDecimal> valueGetMethod) {
        return powerData.stream()
                .map(valueGetMethod)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

}
