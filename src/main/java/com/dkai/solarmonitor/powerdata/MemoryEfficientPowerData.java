package com.dkai.solarmonitor.powerdata;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MemoryEfficientPowerData implements PowerData {
    private static final int SCALE = 2;

    private long id;
    private long dateTime;
    private short solarVoltage;
    private short solarCurrent;
    private int solarPower;
    private short batteryVoltage;
    private short batteryCurrent;
    private int batteryPower;

    private short loadVoltage;
    private short loadCurrent;
    private int loadPower;

    private short maximumInputVoltageToday;
    private short minimumInputVoltageToday;
    private short maximumBatteryVoltageToday;
    private short minimumBatteryVoltageToday;
    private short consumedEnergyToday;
    private short consumedEnergyThisMonth;
    private short consumedEnergyThisYear;
    private int totalConsumedEnergy;
    private short generatedEnergyToday;
    private short generatedEnergyThisMonth;
    private short generatedEnergyThisYear;
    private int totalGeneratedEnergy;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneOffset.UTC);
    }

    @Override
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    @Override
    public BigDecimal getSolarVoltage() {
        return bigDecimal(solarVoltage);
    }

    @Override
    public void setSolarVoltage(BigDecimal solarVoltage) {
        throwExceptionIfScaleIsNotTwo(solarVoltage);
        this.solarVoltage = solarVoltage.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getSolarCurrent() {
        return bigDecimal(solarCurrent);
    }

    @Override
    public void setSolarCurrent(BigDecimal solarCurrent) {
        throwExceptionIfScaleIsNotTwo(solarCurrent);
        this.solarCurrent = solarCurrent.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getSolarPower() {
        return bigDecimal(solarPower);
    }

    @Override
    public void setSolarPower(BigDecimal solarPower) {
        throwExceptionIfScaleIsNotTwo(solarPower);
        this.solarPower = solarPower.unscaledValue().intValue();
    }

    @Override
    public BigDecimal getBatteryVoltage() {
        return bigDecimal(batteryVoltage);
    }

    @Override
    public void setBatteryVoltage(BigDecimal batteryVoltage) {
        throwExceptionIfScaleIsNotTwo(batteryVoltage);
        this.batteryVoltage = batteryVoltage.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getBatteryCurrent() {
        return bigDecimal(batteryCurrent);
    }

    @Override
    public void setBatteryCurrent(BigDecimal batteryCurrent) {
        throwExceptionIfScaleIsNotTwo(batteryCurrent);
        this.batteryCurrent = batteryCurrent.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getBatteryPower() {
        return bigDecimal(batteryPower);
    }

    @Override
    public void setBatteryPower(BigDecimal batteryPower) {
        throwExceptionIfScaleIsNotTwo(batteryPower);
        this.batteryPower = batteryPower.unscaledValue().intValue();
    }

    @Override
    public BigDecimal getLoadVoltage() {
        return bigDecimal(loadVoltage);
    }

    @Override
    public void setLoadVoltage(BigDecimal loadVoltage) {
        throwExceptionIfScaleIsNotTwo(loadVoltage);
        this.loadVoltage = loadVoltage.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getLoadCurrent() {
        return bigDecimal(loadCurrent);
    }

    @Override
    public void setLoadCurrent(BigDecimal loadCurrent) {
        throwExceptionIfScaleIsNotTwo(loadCurrent);
        this.loadCurrent = loadCurrent.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getLoadPower() {
        return bigDecimal(loadPower);
    }

    @Override
    public void setLoadPower(BigDecimal loadPower) {
        throwExceptionIfScaleIsNotTwo(loadPower);
        this.loadPower = loadPower.unscaledValue().intValue();
    }

    @Override
    public BigDecimal getMaximumInputVoltageToday() {
        return bigDecimal(maximumInputVoltageToday);
    }

    @Override
    public void setMaximumInputVoltageToday(BigDecimal maximumInputVoltageToday) {
        throwExceptionIfScaleIsNotTwo(maximumInputVoltageToday);
        this.maximumInputVoltageToday = maximumInputVoltageToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getMinimumInputVoltageToday() {
        return bigDecimal(minimumInputVoltageToday);
    }

    @Override
    public void setMinimumInputVoltageToday(BigDecimal minimumInputVoltageToday) {
        throwExceptionIfScaleIsNotTwo(minimumInputVoltageToday);
        this.minimumInputVoltageToday = minimumInputVoltageToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getMaximumBatteryVoltageToday() {
        return bigDecimal(maximumBatteryVoltageToday);
    }

    @Override
    public void setMaximumBatteryVoltageToday(BigDecimal maximumBatteryVoltageToday) {
        throwExceptionIfScaleIsNotTwo(maximumBatteryVoltageToday);
        this.maximumBatteryVoltageToday = maximumBatteryVoltageToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getMinimumBatteryVoltageToday() {
        return bigDecimal(minimumBatteryVoltageToday);
    }

    @Override
    public void setMinimumBatteryVoltageToday(BigDecimal minimumBatteryVoltageToday) {
        throwExceptionIfScaleIsNotTwo(minimumBatteryVoltageToday);
        this.minimumBatteryVoltageToday = minimumBatteryVoltageToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getConsumedEnergyToday() {
        return bigDecimal(consumedEnergyToday);
    }

    @Override
    public void setConsumedEnergyToday(BigDecimal consumedEnergyToday) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyToday);
        this.consumedEnergyToday = consumedEnergyToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getConsumedEnergyThisMonth() {
        return bigDecimal(consumedEnergyThisMonth);
    }

    @Override
    public void setConsumedEnergyThisMonth(BigDecimal consumedEnergyThisMonth) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyThisMonth);
        this.consumedEnergyThisMonth = consumedEnergyThisMonth.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getConsumedEnergyThisYear() {
        return bigDecimal(consumedEnergyThisYear);
    }

    @Override
    public void setConsumedEnergyThisYear(BigDecimal consumedEnergyThisYear) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyThisYear);
        this.consumedEnergyThisYear = consumedEnergyThisYear.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getTotalConsumedEnergy() {
        return bigDecimal(totalConsumedEnergy);
    }

    @Override
    public void setTotalConsumedEnergy(BigDecimal totalConsumedEnergy) {
        throwExceptionIfScaleIsNotTwo(totalConsumedEnergy);
        this.totalConsumedEnergy = totalConsumedEnergy.unscaledValue().intValue();
    }

    @Override
    public BigDecimal getGeneratedEnergyToday() {
        return bigDecimal(generatedEnergyToday);
    }

    @Override
    public void setGeneratedEnergyToday(BigDecimal generatedEnergyToday) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyToday);
        this.generatedEnergyToday = generatedEnergyToday.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getGeneratedEnergyThisMonth() {
        return bigDecimal(generatedEnergyThisMonth);
    }

    @Override
    public void setGeneratedEnergyThisMonth(BigDecimal generatedEnergyThisMonth) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyThisMonth);
        this.generatedEnergyThisMonth = generatedEnergyThisMonth.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getGeneratedEnergyThisYear() {
        return bigDecimal(generatedEnergyThisYear);
    }

    @Override
    public void setGeneratedEnergyThisYear(BigDecimal generatedEnergyThisYear) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyThisYear);
        this.generatedEnergyThisYear = generatedEnergyThisYear.unscaledValue().shortValueExact();
    }

    @Override
    public BigDecimal getTotalGeneratedEnergy() {
        return bigDecimal(totalGeneratedEnergy);
    }

    @Override
    public void setTotalGeneratedEnergy(BigDecimal totalGeneratedEnergy) {
        throwExceptionIfScaleIsNotTwo(totalGeneratedEnergy);
        this.totalGeneratedEnergy = totalGeneratedEnergy.unscaledValue().intValue();
    }

    private void throwExceptionIfScaleIsNotTwo(BigDecimal value) {
        if (value.scale() != SCALE) {
            throw new IllegalArgumentException("scale needs to be two to work");
        }
    }

    private BigDecimal bigDecimal(int value) {
        return new BigDecimal(BigInteger.valueOf(value), SCALE);
    }
}