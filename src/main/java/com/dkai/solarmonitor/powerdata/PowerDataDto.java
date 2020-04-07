package com.dkai.solarmonitor.powerdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerDataDto {
    private long secondsSince1970;
    private String currentPowerData;
    private String historicalPowerData;
}
