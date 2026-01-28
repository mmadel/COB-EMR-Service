package com.cob.emr.entity.appointment;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private Long startDate;
    @Column
    private Long endDate;
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column
    private String therapyUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status")
    private AppointmentStatus appointmentStatus;

    @OneToOne
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private PatientCase patientCase;

    @OneToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private CalendarEntity calendar;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "appointment", fetch = FetchType.EAGER)
    private AppointmentCancelNoShowReason appointmentCancelNoShowReason;

    @Column(name = "title")
    private String title;
    @Column
    private Integer seriesId;
}

