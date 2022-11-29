package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DailySummary {
    int year;
    int month;
    int day;
    BigDecimal energyThisDay;
}
