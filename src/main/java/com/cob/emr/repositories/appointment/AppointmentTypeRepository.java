package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentTypeRepository extends PagingAndSortingRepository<AppointmentType, Long> {
}
