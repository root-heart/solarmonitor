package com.dkai.solarmonitor.powerdata;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "monthly_summary")
public class MonthlySummaryEntity {
    @Id
    private long id;
    private int year;
    private int month;
    private BigDecimal energyThisMonth;
    private BigDecimal energyThisYear;
    private BigDecimal totalEnergy;
    private BigDecimal maxPower;
}
