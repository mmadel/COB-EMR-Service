package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
    @Query(value = "select cal  from CalendarEntity cal " +
            "where cal.createdBy =:uuid " +
            "OR cal.isPublic=true ")
    Page<CalendarEntity> getCalendars(Pageable paging, @Param("uuid") String uuid);
}
