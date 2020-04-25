package com.dkai.solarmonitor.summary;

import com.dkai.solarmonitor.powerdata.PowerData;
import com.dkai.solarmonitor.powerdata.PowerDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final PowerDataRepository powerDataRepository;

    private final SummaryDataRepository summaryDataRepository;

    @Scheduled(cron = "0 10 0 * * *")
    public void summarizePastData() {
        LocalDateTime startOfYesterday = LocalDate.now().atStartOfDay().plusDays(-1);
        List<PowerData> dataBeforeYesterday = powerDataRepository.findAllByDateTimeBefore(startOfYesterday);
        Map<LocalDate, List<PowerData>> powerDataPerDay = dataBeforeYesterday.stream()
                .collect(Collectors.groupingBy(it -> it.getDateTime().toLocalDate()));
        powerDataPerDay.forEach((day, powerDataList) -> {
            SummaryData summaryData = new SummaryData();
            summaryData.setDay(day);
            summaryData.setPowerData(powerDataList);
            summaryDataRepository.save(summaryData);
            powerDataRepository.deleteAll(powerDataList);
        });
    }

    public SummaryData getForDay(LocalDate day) {
        return summaryDataRepository.findByDay(day);
    }
}


