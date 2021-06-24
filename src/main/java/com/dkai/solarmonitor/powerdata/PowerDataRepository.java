package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PowerDataRepository extends JpaRepository<PowerData, Long> {
    List<PowerData> findAllByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("select p from PowerData p where p.id = (select max(id) from PowerData)")
    PowerData findLatest();

    @Query(
            value = "select * from power_data " +
                    "where date_time > :from " +
                    "order by date_time",
            nativeQuery = true)
    List<PowerData> getDataOfLast24Hours(@Param("from") LocalDateTime from);

    List<PowerData> findAllByDateTimeBefore(LocalDateTime when);
}
