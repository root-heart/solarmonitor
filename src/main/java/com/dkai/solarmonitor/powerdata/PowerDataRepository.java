package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PowerDataRepository extends JpaRepository<PowerData, Long> {
    List<PowerData> findAllByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("select p from PowerData p where p.id = (select max(id) from PowerData)")
    PowerData findLatest();

    @Query(
            value = "select * from power_data " +
                    "where cast(extract(minute from date_time) as int) % 6 = 0 " +
                    "and date_time > now() - interval '1 day'" +
                    "order by date_time",
            nativeQuery = true)
    List<PowerData> getDataOfLast24Hours();

    List<PowerData> findAllByDateTimeBefore(LocalDateTime when);
}
