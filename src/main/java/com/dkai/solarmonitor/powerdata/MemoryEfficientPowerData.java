package com.dkai.solarmonitor.powerdata;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MemoryEfficientPowerData {
    private long id;
    private long dateTime;
    private int solarVoltage;
    private int solarCurrent;
    private int solarPower;
    private int batteryVoltage;
    private int batteryCurrent;
    private int batteryPower;

    private int loadVoltage;
    private int loadCurrent;
    private int loadPower;

    private int maximumInputVoltageToday;
    private int minimumInputVoltageToday;
    private int maximumBatteryVoltageToday;
    private int minimumBatteryVoltageToday;
    private int consumedEnergyToday;
    private int consumedEnergyThisMonth;
    private int consumedEnergyThisYear;
    private int totalConsumedEnergy;
    private int generatedEnergyToday;
    private int generatedEnergyThisMonth;
    private int generatedEnergyThisYear;
    private int totalGeneratedEnergy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneOffset.UTC);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static void main(String[] args) {
        BigDecimal bd = new BigDecimal(BigInteger.valueOf(12345), 2);
        System.out.println(bd);
    }

    public BigDecimal getSolarVoltage() {
        return new BigDecimal(BigInteger.valueOf(solarVoltage), 2);
    }

    private void throwExceptionIfScaleIsNotTwo(BigDecimal value) {
        if (value.scale() != 2) {
            throw new IllegalArgumentException("scale needs to be two to work");
        }
    }

    public void setSolarVoltage(BigDecimal solarVoltage) {
        throwExceptionIfScaleIsNotTwo(solarVoltage);
        this.solarVoltage = solarVoltage.unscaledValue().intValue();
    }

    public BigDecimal getSolarCurrent() {
        return new BigDecimal(BigInteger.valueOf(solarCurrent), 2);
    }

    public void setSolarCurrent(BigDecimal solarCurrent) {
        throwExceptionIfScaleIsNotTwo(solarCurrent);
        this.solarCurrent = solarCurrent.unscaledValue().intValue();
    }

    public BigDecimal getSolarPower() {
        return new BigDecimal(BigInteger.valueOf(solarPower), 2);
    }

    public void setSolarPower(BigDecimal solarPower) {
        throwExceptionIfScaleIsNotTwo(solarPower);
        this.solarPower = solarPower.unscaledValue().intValue();
    }

    public BigDecimal getBatteryVoltage() {
        return new BigDecimal(BigInteger.valueOf(batteryVoltage), 2);
    }

    public void setBatteryVoltage(BigDecimal batteryVoltage) {
        throwExceptionIfScaleIsNotTwo(batteryVoltage);
        this.batteryVoltage = batteryVoltage.unscaledValue().intValue();
    }

    public BigDecimal getBatteryCurrent() {
        return new BigDecimal(BigInteger.valueOf(batteryCurrent), 2);
    }

    public void setBatteryCurrent(BigDecimal batteryCurrent) {
        throwExceptionIfScaleIsNotTwo(batteryCurrent);
        this.batteryCurrent = batteryCurrent.unscaledValue().intValue();
    }

    public BigDecimal getBatteryPower() {
        return new BigDecimal(BigInteger.valueOf(batteryPower), 2);
    }

    public void setBatteryPower(BigDecimal batteryPower) {
        throwExceptionIfScaleIsNotTwo(batteryPower);
        this.batteryPower = batteryPower.unscaledValue().intValue();
    }

    public BigDecimal getLoadVoltage() {
        return new BigDecimal(BigInteger.valueOf(loadVoltage), 2);
    }

    public void setLoadVoltage(BigDecimal loadVoltage) {
        throwExceptionIfScaleIsNotTwo(loadVoltage);
        this.loadVoltage = loadVoltage.unscaledValue().intValue();
    }

    public BigDecimal getLoadCurrent() {
        return new BigDecimal(BigInteger.valueOf(loadCurrent), 2);
    }

    public void setLoadCurrent(BigDecimal loadCurrent) {
        throwExceptionIfScaleIsNotTwo(loadCurrent);
        this.loadCurrent = loadCurrent.unscaledValue().intValue();
    }

    public BigDecimal getLoadPower() {
        return new BigDecimal(BigInteger.valueOf(loadPower), 2);
    }

    public void setLoadPower(BigDecimal loadPower) {
        throwExceptionIfScaleIsNotTwo(loadPower);
        this.loadPower = loadPower.unscaledValue().intValue();
    }

    public BigDecimal getMaximumInputVoltageToday() {
        return new BigDecimal(BigInteger.valueOf(maximumInputVoltageToday), 2);
    }

    public void setMaximumInputVoltageToday(BigDecimal maximumInputVoltageToday) {
        throwExceptionIfScaleIsNotTwo(maximumInputVoltageToday);
        this.maximumInputVoltageToday = maximumInputVoltageToday.unscaledValue().intValue();
    }

    public BigDecimal getMinimumInputVoltageToday() {
        return new BigDecimal(BigInteger.valueOf(minimumInputVoltageToday), 2);
    }

    public void setMinimumInputVoltageToday(BigDecimal minimumInputVoltageToday) {
        throwExceptionIfScaleIsNotTwo(minimumInputVoltageToday);
        this.minimumInputVoltageToday = minimumInputVoltageToday.unscaledValue().intValue();
    }

    public BigDecimal getMaximumBatteryVoltageToday() {
        return new BigDecimal(BigInteger.valueOf(maximumBatteryVoltageToday), 2);
    }

    public void setMaximumBatteryVoltageToday(BigDecimal maximumBatteryVoltageToday) {
        throwExceptionIfScaleIsNotTwo(maximumBatteryVoltageToday);
        this.maximumBatteryVoltageToday = maximumBatteryVoltageToday.unscaledValue().intValue();
    }

    public BigDecimal getMinimumBatteryVoltageToday() {
        return new BigDecimal(BigInteger.valueOf(minimumBatteryVoltageToday), 2);
    }

    public void setMinimumBatteryVoltageToday(BigDecimal minimumBatteryVoltageToday) {
        throwExceptionIfScaleIsNotTwo(minimumBatteryVoltageToday);
        this.minimumBatteryVoltageToday = minimumBatteryVoltageToday.unscaledValue().intValue();
    }

    public BigDecimal getConsumedEnergyToday() {
        return new BigDecimal(BigInteger.valueOf(consumedEnergyToday), 2);
    }

    public void setConsumedEnergyToday(BigDecimal consumedEnergyToday) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyToday);
        this.consumedEnergyToday = consumedEnergyToday.unscaledValue().intValue();
    }

    public BigDecimal getConsumedEnergyThisMonth() {
        return new BigDecimal(BigInteger.valueOf(consumedEnergyThisMonth), 2);
    }

    public void setConsumedEnergyThisMonth(BigDecimal consumedEnergyThisMonth) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyThisMonth);
        this.consumedEnergyThisMonth = consumedEnergyThisMonth.unscaledValue().intValue();
    }

    public BigDecimal getConsumedEnergyThisYear() {
        return new BigDecimal(BigInteger.valueOf(consumedEnergyThisYear), 2);
    }

    public void setConsumedEnergyThisYear(BigDecimal consumedEnergyThisYear) {
        throwExceptionIfScaleIsNotTwo(consumedEnergyThisYear);
        this.consumedEnergyThisYear = consumedEnergyThisYear.unscaledValue().intValue();
    }

    public BigDecimal getTotalConsumedEnergy() {
        return new BigDecimal(BigInteger.valueOf(totalConsumedEnergy), 2);
    }

    public void setTotalConsumedEnergy(BigDecimal totalConsumedEnergy) {
        throwExceptionIfScaleIsNotTwo(totalConsumedEnergy);
        this.totalConsumedEnergy = totalConsumedEnergy.unscaledValue().intValue();
    }

    public BigDecimal getGeneratedEnergyToday() {
        return new BigDecimal(BigInteger.valueOf(generatedEnergyToday), 2);
    }

    public void setGeneratedEnergyToday(BigDecimal generatedEnergyToday) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyToday);
        this.generatedEnergyToday = generatedEnergyToday.unscaledValue().intValue();
    }

    public BigDecimal getGeneratedEnergyThisMonth() {
        return new BigDecimal(BigInteger.valueOf(generatedEnergyThisMonth), 2);
    }

    public void setGeneratedEnergyThisMonth(BigDecimal generatedEnergyThisMonth) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyThisMonth);
        this.generatedEnergyThisMonth = generatedEnergyThisMonth.unscaledValue().intValue();
    }

    public BigDecimal getGeneratedEnergyThisYear() {
        return new BigDecimal(BigInteger.valueOf(generatedEnergyThisYear), 2);
    }

    public void setGeneratedEnergyThisYear(BigDecimal generatedEnergyThisYear) {
        throwExceptionIfScaleIsNotTwo(generatedEnergyThisYear);
        this.generatedEnergyThisYear = generatedEnergyThisYear.unscaledValue().intValue();
    }

    public BigDecimal getTotalGeneratedEnergy() {
        return new BigDecimal(BigInteger.valueOf(totalGeneratedEnergy), 2);
    }

    public void setTotalGeneratedEnergy(BigDecimal totalGeneratedEnergy) {
        throwExceptionIfScaleIsNotTwo(totalGeneratedEnergy);
        this.totalGeneratedEnergy = totalGeneratedEnergy.unscaledValue().intValue();
    }
}
