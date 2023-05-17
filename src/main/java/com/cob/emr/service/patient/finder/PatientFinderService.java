package com.cob.emr.service.patient.finder;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.response.PatientResponse;
import org.springframework.data.domain.Pageable;

public interface PatientFinderService {

    PatientResponse findAll(Pageable pageable, Long clinicId);

    PatientModel findById(Long clinicId, Long patientId);
}
