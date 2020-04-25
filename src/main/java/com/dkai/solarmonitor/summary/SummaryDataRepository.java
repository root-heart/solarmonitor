package com.dkai.solarmonitor.summary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SummaryDataRepository extends JpaRepository<SummaryData, Long> {
    SummaryData findByDay(LocalDate day);
}
