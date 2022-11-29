package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SummarizedPowerData {

    private List<DailySummaryEntity> dailySummary = new ArrayList<>();
    private List<MonthlySummaryEntity> monthlySummary = new ArrayList<>();
}
