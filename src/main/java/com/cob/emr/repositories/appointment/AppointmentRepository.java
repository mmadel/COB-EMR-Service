package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Long> {

    @Query("select a from Appointment a where " +
            "a.clinic.id = :clinicId " +
            "AND " + "a.startDate >= :startDate " +
            "AND " + "a.endDate <=:endDate")
    Optional<List<Appointment>> findAppointmentByDateRange(@Param(value = "startDate") long startDate, @Param(value = "endDate") long endDate,
                                                           @Param(value = "clinicId") long clinicId);
}
