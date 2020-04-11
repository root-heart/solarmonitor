package com.dkai.solarmonitor.powerdata;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final PowerDataService powerDataService;

    @GetMapping("/day/{day}")
    public List<PowerData> getForDay(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return powerDataService.getSummaryForDay(day);
    }
}
