package com.dkai.solarmonitor.powerdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeControllerRegistersDto {
    private long secondsSince1970;
    private String registers3100To3107 = "";
    private String registers310cTo3111 = "";
    private String registers3300To330b = "";
    private String registers330cTo3313 = "";
}
