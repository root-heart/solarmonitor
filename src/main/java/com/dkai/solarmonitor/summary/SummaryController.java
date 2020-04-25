package com.dkai.solarmonitor.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/day/{day}")
    public SummaryData getForDay(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return summaryService.getForDay(day);
    }

    @PostMapping("/create")
    public void create() {
        summaryService.summarizePastData();
    }
}
