package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerDataService {
    private final PowerDataRepository powerDataRepository;

    public PowerData read(long id) {
        return powerDataRepository.getOne(id);
    }

    public PowerData savePowerData(PowerDataDto dto) {
        // TODO add unit test for this stuff
        PowerData powerData = new PowerData();
        powerData.setDateTime(LocalDateTime.ofEpochSecond(dto.getSecondsSince1970(), 0, ZoneOffset.UTC));
        powerData.setSolarVoltage(parseValue(dto.getCurrentPowerData().substring(6, 10)));
        powerData.setSolarCurrent(parseValue(dto.getCurrentPowerData().substring(10, 14)));
        powerData.setSolarPower(parseValue(dto.getCurrentPowerData().substring(14, 18)));
        powerData.setBatteryVoltage(parseValue(dto.getCurrentPowerData().substring(22, 26)));
        powerData.setBatteryCurrent(parseValue(dto.getCurrentPowerData().substring(26, 30)));
        powerData.setBatteryPower(parseValue(dto.getCurrentPowerData().substring(30, 34)));
        return powerDataRepository.save(powerData);
    }

    private BigDecimal parseValue(String s) {
        return BigDecimal.valueOf(Integer.valueOf(s, 16), 2);
    }

    public List<PowerData> getSummaryForDay(LocalDate day) {
        LocalDateTime start = day.atStartOfDay();
        LocalDateTime end = day.plusDays(1).atStartOfDay();
        return powerDataRepository.getByDateTimeBetween(start, end);
    }

    public void delete(long id) {
        powerDataRepository.deleteById(id);
    }
}
