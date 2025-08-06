package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.series.AppointmentSeries;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppointmentSeriesRepository extends PagingAndSortingRepository<AppointmentSeries, Long> {

    Optional<AppointmentSeries> findBySeriesId(Integer seriesId);

    @Modifying
    @Query("delete from AppointmentSeries apps where apps.seriesId =:seriesId")
    void deleteBySeriesId(@Param(value = "seriesId") Integer seriesId);
}
