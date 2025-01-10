package com.cob.emr.model.appointment;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FullAppointmentModel extends AppointmentModel{
    private PatientModel patient ;
    private PatientCaseModel patientCase;
}
