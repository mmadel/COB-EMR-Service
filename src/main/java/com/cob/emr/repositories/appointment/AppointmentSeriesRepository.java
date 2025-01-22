package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentSeries;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentSeriesRepository extends PagingAndSortingRepository<AppointmentSeries, Long> {
}
