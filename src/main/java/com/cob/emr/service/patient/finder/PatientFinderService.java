package com.cob.emr.service.patient.finder;

import com.cob.emr.model.patient.PatientModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientFinderService {

    List<PatientModel> findAll(Pageable pageable, Long clinicId);

    PatientModel findById(Long clinicId, Long patientId);
}
