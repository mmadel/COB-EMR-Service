package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
    @Query(value = "select cal  from CalendarEntity cal " +
            "where cal.createdBy =:uuid " +
            "OR cal.isPublic=true ")
    List<CalendarEntity> getCalendars(@Param("uuid") String uuid);
}
