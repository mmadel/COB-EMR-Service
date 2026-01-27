package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.calendar.UserCalendarAttachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCalendarAttachmentRepository extends CrudRepository<UserCalendarAttachment, Long> {
    Optional<UserCalendarAttachment> findByCalendar_IdAndUser(Long calendarId , String uuid);

    @Query(value="SELECT c from UserCalendarAttachment uca inner join uca.calendar c " +
            "where uca.user =:userUUID " +
            "and c.clinic.id =:clinicId " +
            "and uca.isAttached =true " )
    List<CalendarEntity> findAttached(@Param("clinicId") Long clinicId , @Param("userUUID") String userUUID);

    void deleteByCalendar_Clinic_Id(Long clinicId);
}
