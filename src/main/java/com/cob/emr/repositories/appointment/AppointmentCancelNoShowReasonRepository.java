package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppointmentCancelNoShowReasonRepository extends CrudRepository<AppointmentCancelNoShowReason, Long> {
    Optional<AppointmentCancelNoShowReason> findByAppointment_Id(Long id);
}
