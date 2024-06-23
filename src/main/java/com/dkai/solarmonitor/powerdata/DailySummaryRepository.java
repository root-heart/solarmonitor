package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailySummaryRepository extends JpaRepository<DailySummaryEntity, Long> {
    DailySummaryEntity findByDate(LocalDate date);
}
