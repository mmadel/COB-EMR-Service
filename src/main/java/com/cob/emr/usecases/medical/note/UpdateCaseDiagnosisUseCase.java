package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Basic;
import com.cob.emr.model.patient.cases.CaseDiagnosis;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.usecases.medical.note.converter.DiagnosisConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCaseDiagnosisUseCase {
    @Autowired
    private PatientCaseRepository patientCaseRepository;

    public void update(Long caseId, Basic basic) {
        boolean changed = false;
        List<CaseDiagnosis> notedDiagnoses;
        PatientCase patientCase =
                patientCaseRepository.findById(caseId).orElseThrow(() -> new IllegalArgumentException("Case not found"));
        List<CaseDiagnosis> caseDiagnoses = patientCase.getCaseDiagnosis();
        if (basic != null && basic.getIcdtenCodeDiagnosis() != null) {
            notedDiagnoses = DiagnosisConverter.convertToCaseDiagnosisList(basic.getIcdtenCodeDiagnosis());
            for (CaseDiagnosis noted : notedDiagnoses) {
                if (!caseDiagnoses.contains(noted)) {
                    caseDiagnoses.add(noted);
                    changed = true;
                }
            }
        }
        if (changed)
            patientCaseRepository.save(patientCase);
    }


}
