package com.cob.emr.repositories.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
    @Query(value = "select cal  from CalendarEntity cal " +
            "where cal.createdBy =:uuid " +
            "OR cal.isPublic=true ")
    Page<CalendarEntity> getCalendars(Pageable paging, @Param("uuid") String uuid);

    @Query(value = "select cal  from CalendarEntity cal " +
            "where cal.createdBy =:uuid " +
            "AND cal.isPublic=true ")
    List<CalendarEntity> getPublicCalendars(@Param("uuid") String uuid);


    @Modifying
    @Query(value = "update CalendarEntity c set c.attached = true " +
            "WHERE c.id IN :ids")
    void attachCalendar(@Param("ids") List<Long> ids);

    @Query(value = "select cal  from CalendarEntity cal " +
            "where cal.clinic.id =:clinicId " +
            "OR cal.isPublic=true ")
    Page<CalendarEntity> listCalendars(Pageable paging, @Param("clinicId") Long clinicId);
}
