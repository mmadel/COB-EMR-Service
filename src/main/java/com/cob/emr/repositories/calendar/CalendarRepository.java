package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
}
