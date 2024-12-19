package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarClinicEntity;
import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarClinicRepository extends CrudRepository<CalendarClinicEntity,Long> {
    @Query(value = "select cc.calendarEntity from CalendarClinicEntity cc " +
            "WHERE cc.clinic.id =:clinicId " +
            "AND cc.userUUID =:uuid")
    List<CalendarEntity> findCalendars(@Param("clinicId") Long clinicId , @Param("uuid") String uuid);
}
