package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentTypeRepository extends PagingAndSortingRepository<AppointmentType,Long> {
}
