package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.UserCalendarAttachment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCalendarAttachmentRepository extends CrudRepository<UserCalendarAttachment, Long> {
    Optional<UserCalendarAttachment> findByCalendar_IdAndUser(Long calendarId , String uuid);
}
