package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.Appointment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment,Long> {
}
