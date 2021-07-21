package com.dkai.solarmonitor.powerdata;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PowerDataInterface {
    long getId();

    void setId(long id);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    BigDecimal getSolarVoltage();

    void setSolarVoltage(BigDecimal solarVoltage);

    BigDecimal getSolarCurrent();

    void setSolarCurrent(BigDecimal solarCurrent);

    BigDecimal getSolarPower();

    void setSolarPower(BigDecimal solarPower);

    BigDecimal getBatteryVoltage();

    void setBatteryVoltage(BigDecimal batteryVoltage);

    BigDecimal getBatteryCurrent();

    void setBatteryCurrent(BigDecimal batteryCurrent);

    BigDecimal getBatteryPower();

    void setBatteryPower(BigDecimal batteryPower);

    BigDecimal getLoadVoltage();

    void setLoadVoltage(BigDecimal loadVoltage);

    BigDecimal getLoadCurrent();

    void setLoadCurrent(BigDecimal loadCurrent);

    BigDecimal getLoadPower();

    void setLoadPower(BigDecimal loadPower);

    BigDecimal getMaximumInputVoltageToday();

    void setMaximumInputVoltageToday(BigDecimal maximumInputVoltageToday);

    BigDecimal getMinimumInputVoltageToday();

    void setMinimumInputVoltageToday(BigDecimal minimumInputVoltageToday);

    BigDecimal getMaximumBatteryVoltageToday();

    void setMaximumBatteryVoltageToday(BigDecimal maximumBatteryVoltageToday);

    BigDecimal getMinimumBatteryVoltageToday();

    void setMinimumBatteryVoltageToday(BigDecimal minimumBatteryVoltageToday);

    BigDecimal getConsumedEnergyToday();

    void setConsumedEnergyToday(BigDecimal consumedEnergyToday);

    BigDecimal getConsumedEnergyThisMonth();

    void setConsumedEnergyThisMonth(BigDecimal consumedEnergyThisMonth);

    BigDecimal getConsumedEnergyThisYear();

    void setConsumedEnergyThisYear(BigDecimal consumedEnergyThisYear);

    BigDecimal getTotalConsumedEnergy();

    void setTotalConsumedEnergy(BigDecimal totalConsumedEnergy);

    BigDecimal getGeneratedEnergyToday();

    void setGeneratedEnergyToday(BigDecimal generatedEnergyToday);

    BigDecimal getGeneratedEnergyThisMonth();

    void setGeneratedEnergyThisMonth(BigDecimal generatedEnergyThisMonth);

    BigDecimal getGeneratedEnergyThisYear();

    void setGeneratedEnergyThisYear(BigDecimal generatedEnergyThisYear);

    BigDecimal getTotalGeneratedEnergy();

    void setTotalGeneratedEnergy(BigDecimal totalGeneratedEnergy);
}
