package com.cob.emr.entity.appointment;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentRepetition;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.enums.AppointmentType;
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
    @Column
    private String note;
    @Column
    private String therapyUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status")
    private AppointmentStatus appointmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_repetition")
    private AppointmentRepetition appointmentRepetition;

    @OneToOne()
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    @OneToOne()
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne()
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private PatientCase patientCase;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type")
    private AppointmentType appointmentType;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "appointment", fetch = FetchType.EAGER)
    private AppointmentCancelNoShowReason appointmentCancelNoShowReason;
}

