package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SummarizedPowerData {
    @Data
    @AllArgsConstructor
    public static class DailySummary {
        int year;
        int month;
        int day;
        BigDecimal energyThisDay;
    }

    @Data
    @AllArgsConstructor
    public static class MonthlySummary {
        int year;
        int month;
        BigDecimal energyThisMonth;
    }

    private List<DailySummary> dailySummary = new ArrayList<>();
    private List<MonthlySummary> monthlySummary = new ArrayList<>();
}
