package com.cob.emr.usecases.patient.cases;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UpdatePatientCaseChartNoteUseCase {
    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Transactional
    public void update(Long caseId, String chartNote) throws PatientCaseException {
        PatientCase patientCase = patientCaseRepository.findById(caseId)
                .orElseThrow(() -> new PatientCaseException(HttpStatus.NOT_FOUND, "Patient case not found", null));
        patientCase.setChartNote(chartNote);
        patientCaseRepository.save(patientCase);
    }
}
