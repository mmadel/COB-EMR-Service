package com.cob.emr.repositories.appointment;

import com.cob.emr.entity.appointment.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.patientCase.id = :patientCaseId " +
            "AND a.startDate  > :currentDate")
    Page<List<Appointment>> findIncomingAppointmentsByCase(@Param(value = "patientId") long patientId,
                                                           @Param(value = "clinicId") long clinicId,
                                                           @Param(value = "patientCaseId") long patientCaseId,
                                                           @Param(value = "currentDate") long currentDate, Pageable paging);

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.startDate  > :currentDate")
    Page<List<Appointment>> findAllIncomingAppointments(@Param(value = "patientId") long patientId,
                                                        @Param(value = "clinicId") long clinicId,
                                                        @Param(value = "currentDate") long currentDate, Pageable paging);

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.startDate  < :currentDate " +
            "OR  a.appointmentStatus = 'noshow'" +
            "OR  a.appointmentStatus = 'cancel'")
    Page<List<Appointment>> findAllPreviousAppointments(@Param(value = "patientId") long patientId,
                                                        @Param(value = "clinicId") long clinicId,
                                                        @Param(value = "currentDate") long currentDate, Pageable paging);

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.patientCase.id = :patientCaseId " +
            "AND a.startDate  < :currentDate ")
    Page<List<Appointment>> findPreviousAppointmentsByCase(@Param(value = "patientId") long patientId,
                                                           @Param(value = "clinicId") long clinicId,
                                                           @Param(value = "patientCaseId") long patientCaseId,
                                                           @Param(value = "currentDate") long currentDate, Pageable paging);

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.patientCase.id = :patientCaseId " +
            "AND  a.appointmentStatus = com.cob.emr.enums.AppointmentStatus.Cancel")
    Page<List<Appointment>> findAllCancelAppointments(@Param(value = "patientId") long patientId,
                                                      @Param(value = "clinicId") long clinicId,
                                                      @Param(value = "patientCaseId") long patientCaseId,
                                                      Pageable paging);

    @Query("select a from Appointment a where " +
            "a.patient.id = :patientId " +
            "AND a.clinic.id = :clinicId " +
            "AND a.patientCase.id = :patientCaseId " +
            "AND  a.appointmentStatus = com.cob.emr.enums.AppointmentStatus.NoShow")
    Page<List<Appointment>> findAllNoShowAppointments(@Param(value = "patientId") long patientId,
                                                      @Param(value = "clinicId") long clinicId,
                                                      @Param(value = "patientCaseId") long patientCaseId,
                                                      Pageable paging);
}
