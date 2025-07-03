package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
public class ProgressNoteCreator implements MedicalNoteCreator{
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        Optional<MedicalNote> optional = medicalNoteRepository.findByPatientCase_IdAndTypeAndStatus(medicalNoteRequest.getCaseId(), MedicalNoteType.INITIAL_EVALUATION, MedicalNoteStatus.Finalized);
        if (optional.isPresent()) {
            InitialEvaluation initialEvaluation = (InitialEvaluation) optional.get();
            ProgressNote progressNote = new ProgressNote();
            progressNote.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Case not found")));
            progressNote.setType(MedicalNoteType.PROGRESS_NOTE);
            progressNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
            progressNote.setNoteDate(medicalNoteRequest.getNoteDate());
            progressNote.setSubjective(initialEvaluation.getSubjective());
            progressNote.setObjective(initialEvaluation.getObjective());
            progressNote.setBilling(initialEvaluation.getBilling());
            progressNote.setAssessment(initialEvaluation.getAssessment());
            progressNote.setPlanOfCare(initialEvaluation.getPlanOfCare());
            return progressNote;
        }else{
            throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
        }
    }
}
