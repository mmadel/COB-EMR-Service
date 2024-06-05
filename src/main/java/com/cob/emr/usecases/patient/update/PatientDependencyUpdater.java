package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;

public interface PatientDependencyUpdater {
    void update(PatientModel model , Patient patient);
}
