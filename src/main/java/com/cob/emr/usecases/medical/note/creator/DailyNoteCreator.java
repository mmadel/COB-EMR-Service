package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DailyNoteCreator implements MedicalNoteCreator {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) {
        if (isInitialExaminationFinalized()) {
            //sync with InitialExamination Data
            throw new IllegalArgumentException("Unsupported Note Type");
        }else{
            //Create standalone daily note
            DailyNote dailyNote = new DailyNote();
            dailyNote.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Case not found")));
            dailyNote.setType(MedicalNoteType.DAILY_NOTE);
            dailyNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
            dailyNote.setNoteDate(medicalNoteRequest.getNoteDate());
            dailyNote.setSubjective(medicalNoteRequest.getSubjective());
            dailyNote.setAssessment(medicalNoteRequest.getAssessment());
            dailyNote.setPlanOfCare(medicalNoteRequest.getPlanOfCare());
            return dailyNote;
        }
    }

    private Boolean isInitialExaminationFinalized() {
        Optional<MedicalNote> initExamNote = medicalNoteRepository.findByPatientCase_IdAndTypeAndStatus(58L, MedicalNoteType.INITIAL_EVALUATION, MedicalNoteStatus.Finalized);
        return initExamNote.isPresent();
    }
}
