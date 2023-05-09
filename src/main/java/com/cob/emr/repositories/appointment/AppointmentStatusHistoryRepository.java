package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.AppointmentStatusHistory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentStatusHistoryRepository extends PagingAndSortingRepository<AppointmentStatusHistory,Long> {
}
