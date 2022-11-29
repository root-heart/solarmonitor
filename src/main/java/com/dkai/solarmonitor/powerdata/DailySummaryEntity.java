package com.dkai.solarmonitor.powerdata;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "daily_summary_last_14_days")
public class DailySummaryEntity {
    @Id
    private long id;
    private int year;
    private int month;
    private int day;
    private BigDecimal energyThisDay;
    private BigDecimal energyThisMonth;
    private BigDecimal energyThisYear;
    private BigDecimal totalEnergy;
    private BigDecimal maxPower;
}
