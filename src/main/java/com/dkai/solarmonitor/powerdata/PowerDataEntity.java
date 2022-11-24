package com.dkai.solarmonitor.powerdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "power_data")
public class PowerDataEntity implements PowerData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "power_data_id_seq")
    private long id;
    private LocalDateTime dateTime;
    private BigDecimal solarVoltage;
    private BigDecimal solarCurrent;
    private BigDecimal solarPower;
    private BigDecimal batteryVoltage;
    private BigDecimal batteryCurrent;
    private BigDecimal batteryPower;

    private BigDecimal loadVoltage;
    private BigDecimal loadCurrent;
    private BigDecimal loadPower;

    private BigDecimal maximumInputVoltageToday;
    private BigDecimal minimumInputVoltageToday;
    private BigDecimal maximumBatteryVoltageToday;
    private BigDecimal minimumBatteryVoltageToday;
    private BigDecimal consumedEnergyToday;
    private BigDecimal consumedEnergyThisMonth;
    private BigDecimal consumedEnergyThisYear;
    private BigDecimal totalConsumedEnergy;
    private BigDecimal generatedEnergyToday;
    private BigDecimal generatedEnergyThisMonth;
    private BigDecimal generatedEnergyThisYear;
    private BigDecimal totalGeneratedEnergy;
}
