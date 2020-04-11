package com.dkai.solarmonitor.powerdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/summary")
public class SummaryController {
    @Autowired
    private PowerDataRepository powerDataRepository;

    @GetMapping("/day/{day}")
    public List<PowerData> getForDay(@PathVariable LocalDate day) {
        LocalDateTime start = day.atStartOfDay();
        LocalDateTime end = day.plusDays(1).atStartOfDay();
        return powerDataRepository.getByDateTimeBetween(start, end);
    }
}
