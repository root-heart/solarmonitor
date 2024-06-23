package com.dkai.solarmonitor.powerdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "daily_summary")
public class DailySummaryEntity {
    @Id
    private long id;
    private LocalDate date;
    private BigDecimal generatedEnergy;
    private BigDecimal consumedEnergy;
    private BigDecimal maximumInputVoltage;
    private BigDecimal minimumInputVoltage;
    private BigDecimal maximumBatteryVoltage;
    private BigDecimal minimumBatteryVoltage;
}
