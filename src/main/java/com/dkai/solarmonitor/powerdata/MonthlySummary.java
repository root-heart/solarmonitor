package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlySummary {
    int year;
    int month;
    BigDecimal energyThisMonth;
}
