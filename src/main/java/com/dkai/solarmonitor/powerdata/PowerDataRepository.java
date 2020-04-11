package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PowerDataRepository extends JpaRepository<PowerData, Long> {
    List<PowerData> getByDateTimeBetween(LocalDateTime from, LocalDateTime to);
}
