package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DailyNoteCreator implements MedicalNoteCreator {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        Optional<MedicalNote> optional = medicalNoteRepository.findByPatientCase_IdAndTypeAndStatus(58L, MedicalNoteType.INITIAL_EVALUATION, MedicalNoteStatus.Draft);
        if (optional.isPresent()) {
            InitialEvaluation initialEvaluation = (InitialEvaluation) optional.get();
            DailyNote dailyNote = new DailyNote();
            dailyNote.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Case not found")));
            dailyNote.setType(MedicalNoteType.DAILY_NOTE);
            dailyNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
            dailyNote.setNoteDate(medicalNoteRequest.getNoteDate());
            dailyNote.setSubjective(initialEvaluation.getSubjective());
            dailyNote.setObjective(initialEvaluation.getBilling());
            dailyNote.setAssessment(initialEvaluation.getAssessment());
            dailyNote.setPlanOfCare(initialEvaluation.getPlanOfCare());
            return dailyNote;

        } else {
            //Create standalone daily note
            throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
        }
    }
}
