package com.dkai.solarmonitor.powerdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PowerDataRepository extends JpaRepository<PowerDataEntity, Long> {
    List<PowerDataEntity> findAllByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("select p from PowerDataEntity p where p.id = (select max(id) from PowerDataEntity)")
    PowerDataEntity findLatest();

    @Query(
            value = "select * from power_data " +
                    "where date_time > :from " +
                    "order by date_time",
            nativeQuery = true)
    List<PowerDataEntity> getDataOfLast24Hours(@Param("from") LocalDateTime from);

    List<PowerDataEntity> findAllByDateTimeBefore(LocalDateTime when);
}
