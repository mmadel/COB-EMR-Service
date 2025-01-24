package com.cob.emr.model.appointment.series;

import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppointmentFullSeries {
    private String patientName;
    private List<ClinicModel> patientClinics ;
    private List<PatientCaseModel> patientCases;
    private Long startTime;
    private Long endTime;
    private AppointmentTypeModel appointmentType;
    private String therapy;
    private ClinicModel clinic;
}
