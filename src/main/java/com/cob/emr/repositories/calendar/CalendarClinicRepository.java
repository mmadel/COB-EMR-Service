package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarClinicEntity;
import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarClinicRepository extends CrudRepository<CalendarClinicEntity,Long> {
    @Query(value = "select cc.calendarEntity from CalendarClinicEntity cc " +
            "WHERE cc.clinic.id =:clinicId " +
            "AND cc.userUUID =:uuid")
    List<CalendarEntity> findCalendars(@Param("clinicId") Long clinicId , @Param("uuid") String uuid);

    @Modifying
    @Query("delete from CalendarClinicEntity cc " +
            "where cc.userUUID = :uuid " +
            "and cc.clinic.id =:clinicId " +
            "and cc.calendarEntity.id in :calendarIds")
    void unAttachCalendar(@Param("clinicId") Long clinicId , @Param("uuid") String uuid , @Param("calendarIds") List<Long> calendarIds);

    void deleteByClinic_Id(Long clinicId);
}
