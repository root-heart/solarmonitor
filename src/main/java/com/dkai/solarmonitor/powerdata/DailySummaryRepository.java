package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySummaryRepository extends JpaRepository<DailySummaryEntity, Long> {
}
