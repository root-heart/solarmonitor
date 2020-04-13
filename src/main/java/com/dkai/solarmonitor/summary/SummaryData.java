package com.dkai.solarmonitor.summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SummaryData {
    private LocalDateTime start;
    private LocalDateTime end;

    private BigDecimal minSolarPower;
    private BigDecimal maxSolarPower;
    private BigDecimal avgSolarPower;
    private BigDecimal minBatteryVoltage;
    private BigDecimal maxBatteryVoltage;
    private BigDecimal avgBatteryVoltage;
    private BigDecimal minBatteryPower;
    private BigDecimal maxBatteryPower;
    private BigDecimal avgBatteryPower;
    private BigDecimal minLoadPower;
    private BigDecimal maxLoadPower;
    private BigDecimal avgLoadPower;

    private BigDecimal maximumBatteryVoltageToday;
    private BigDecimal minimumBatteryVoltageToday;
    private BigDecimal consumedEnergyToday;
    private BigDecimal generatedEnergyToday;
}
