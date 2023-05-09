package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentTypeRepository extends PagingAndSortingRepository<AppointmentType, Long> {
    Optional<List<AppointmentType>> findByClinicId(Long id);

    Optional<AppointmentType> findByIdAndClinic_Id(Long id, Long clinicId);
}
