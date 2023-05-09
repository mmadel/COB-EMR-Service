package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentStatusHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentStatusHistoryRepository extends PagingAndSortingRepository<AppointmentStatusHistory, Long> {

    @Query("Select psh from AppointmentStatusHistory  psh WHERE " +
            "psh.appointment.id  = :appointmentId ")
    Optional<List<AppointmentStatusHistory>> find(@Param(value = "appointmentId") long appointmentId);
}
