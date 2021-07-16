package com.dkai.solarmonitor.powerdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Measurement {
    private final LocalDateTime dateTime;
    private final BigDecimal value;
}
