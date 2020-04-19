package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PowerDataRepository extends JpaRepository<PowerData, Long> {
    List<PowerData> getByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("select p from PowerData p where p.id = (select max(id) from PowerData)")
    PowerData findLatest();
}
