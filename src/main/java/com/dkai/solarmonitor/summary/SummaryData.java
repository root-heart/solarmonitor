package com.dkai.solarmonitor.summary;

import com.dkai.solarmonitor.powerdata.PowerData;
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
    private List<PowerData> powerData;

    public BigDecimal getGeneratedEnergy() {
        return getMax(PowerData::getGeneratedEnergyToday);
    }

    public BigDecimal getConsumedEnergy() {
        return getMax(PowerData::getConsumedEnergyToday);
    }

    public BigDecimal getMinBatteryVoltage() {
        return getMin(PowerData::getBatteryVoltage);
    }

    public BigDecimal getMaxBatteryVoltage() {
        return getMax(PowerData::getBatteryVoltage);
    }

    private BigDecimal getMax(Function<PowerData, BigDecimal> valueGetMethod) {
        return powerData.stream()
                .map(valueGetMethod)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getMin(Function<PowerData, BigDecimal> valueGetMethod) {
        return powerData.stream()
                .map(valueGetMethod)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

}
