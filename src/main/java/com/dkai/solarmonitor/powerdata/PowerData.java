package com.dkai.solarmonitor.powerdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PowerData {
    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime dateTime;
    private BigDecimal solarVoltage;
    private BigDecimal solarCurrent;
    private BigDecimal solarPower;
    private BigDecimal batteryVoltage;
    private BigDecimal batteryCurrent;
    private BigDecimal batteryPower;
}
