package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SummarizedPowerData {
    LocalDate day;
    BigDecimal energyThisDay;
    BigDecimal energyThisMonth;
    BigDecimal energyThisYear;
    BigDecimal totalEnergy;
    BigDecimal maxPower;
}
