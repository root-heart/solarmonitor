package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ChargeControllerRegisterConverter {
    public PowerData convert(ChargeControllerRegistersDto registersDto) {
        PowerData powerData = new PowerData();
        powerData.setDateTime(LocalDateTime.ofEpochSecond(registersDto.getSecondsSince1970(), 0, ZoneOffset.UTC));

        if (registersDto.getRegisters3100To3107().length() >= 38) {
            Registers r = new Registers(registersDto.getRegisters3100To3107().substring(6, 38));
            powerData.setSolarVoltage(r.getValue(0));
            powerData.setSolarCurrent(r.getValue(1));
            powerData.setSolarPower(r.getValue(3, 2));
            powerData.setBatteryVoltage(r.getValue(4));
            powerData.setBatteryCurrent(r.getValue(5));
            powerData.setBatteryPower(r.getValue(7, 6));
        }

        if (registersDto.getRegisters310cTo3111().length() >= 30) {
            Registers r = new Registers(registersDto.getRegisters310cTo3111().substring(6, 30));
            powerData.setLoadVoltage(r.getValue(0));
            powerData.setLoadCurrent(r.getValue(1));
            powerData.setLoadPower(r.getValue(3, 2));
            // the next three are temperature values that I am currently not interested in
        }

        if (registersDto.getRegisters3300To330b().length() >= 54) {
            Registers r = new Registers(registersDto.getRegisters3300To330b().substring(6, 54));
            powerData.setMaximumInputVoltageToday(r.getValue(0));
            powerData.setMinimumInputVoltageToday(r.getValue(1));
            powerData.setMaximumBatteryVoltageToday(r.getValue(2));
            powerData.setMinimumBatteryVoltageToday(r.getValue(3));
            powerData.setConsumedEnergyToday(r.getValue(5, 4));
            powerData.setConsumedEnergyThisMonth(r.getValue(7, 6));
            powerData.setConsumedEnergyThisYear(r.getValue(9, 8));
            powerData.setTotalConsumedEnergy(r.getValue(11, 10));
        }

        if (registersDto.getRegisters330cTo3313().length() >= 38) {
            Registers r = new Registers(registersDto.getRegisters330cTo3313().substring(6, 38));
            powerData.setGeneratedEnergyToday(r.getValue(1, 0));
            powerData.setGeneratedEnergyThisMonth(r.getValue(3, 2));
            powerData.setGeneratedEnergyThisYear(r.getValue(5, 4));
            powerData.setTotalGeneratedEnergy(r.getValue(7, 6));
        }

        return powerData;
    }

    @RequiredArgsConstructor
    static class Registers {
        final String stringRepresentation;

        private String getSubstring(int start, int end) {
            return "";
        }

        public BigDecimal getValue(int... registers) {
            String collect = Arrays
                    .stream(registers)
                    .mapToObj(index -> stringRepresentation.substring(index * 4, index * 4 + 4))
                    .collect(Collectors.joining(""));
            return BigDecimal.valueOf(Integer.parseInt(collect, 16), 2);
        }
    }
}
