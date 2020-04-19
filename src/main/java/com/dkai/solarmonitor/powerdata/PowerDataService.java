package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerDataService {
    private final PowerDataRepository powerDataRepository;

    public PowerData getCurrentPowerData() {
        return powerDataRepository.findLatest();
    }

    public PowerData savePowerData(PowerData powerData) {
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
