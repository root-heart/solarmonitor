package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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

    public List<PowerData> getPowerDataForLast24Hours() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        return powerDataRepository.getDataOfLast24Hours(now.minusDays(1).toLocalDateTime());
    }

    public void delete(long id) {
        powerDataRepository.deleteById(id);
    }
}
