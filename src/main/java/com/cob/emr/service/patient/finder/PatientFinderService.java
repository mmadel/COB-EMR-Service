package com.cob.emr.service.patient.finder;

import com.cob.emr.model.patient.PatientModel;

import java.util.List;

public interface PatientFinderService {

    List<PatientModel> findAll(Long clinicId);

    PatientModel findById(Long clinicId, Long patientId);
}
