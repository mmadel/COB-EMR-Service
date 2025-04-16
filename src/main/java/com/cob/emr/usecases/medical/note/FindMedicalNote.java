package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.entity.medical.note.types.QuickDischargeNote;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.model.medical.note.IMedicalNote;
import com.cob.emr.model.medical.note.types.daily.note.DailyNoteModel;
import com.cob.emr.model.medical.note.types.discharge.QuickDischargeNoteModel;
import com.cob.emr.model.medical.note.types.initial.evaluation.InitialExamination;
import com.cob.emr.model.medical.note.types.progress.note.ProgressNoteModel;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FindMedicalNote {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    ModelMapper mapper;

    public IMedicalNote find(Long id) {

        MedicalNote medicalNote = medicalNoteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("medical note not found"));

        if (medicalNote instanceof InitialEvaluation)
            return declareInitialEvaluation(medicalNote);
        if (medicalNote instanceof DailyNote)
            return declareDailyNote(medicalNote);
        if(medicalNote instanceof ProgressNote)
            return declareProgressNote(medicalNote);
        if(medicalNote instanceof QuickDischargeNote)
            return declareQuickDischargeNote(medicalNote);
        return null;
    }

    private MedicalNoteType fromLabel(String label) {
        return Arrays.stream(MedicalNoteType.values())
                .filter(e -> e.label.equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }

    private InitialExamination declareInitialEvaluation(MedicalNote medicalNote) {
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNote;
        InitialExamination initialExamination = new InitialExamination();
        initialExamination.setSubjective(initialEvaluation.getSubjective());
        initialExamination.setPlanOfCare(initialEvaluation.getPlanOfCare());
        initialExamination.setBilling(initialEvaluation.getBilling());
        initialExamination.setAssessment(initialEvaluation.getAssessment());
        initialExamination.setObjective(initialEvaluation.getObjective());
        return initialExamination;
    }

    private DailyNoteModel declareDailyNote(MedicalNote medicalNote) {
        DailyNote entity = (DailyNote) medicalNote;
        DailyNoteModel dailyNote = new DailyNoteModel();
        dailyNote.setSubjective(entity.getSubjective());
        dailyNote.setObjective(entity.getObjective());
        dailyNote.setAssessment(entity.getAssessment());
        dailyNote.setPlanOfCare(entity.getPlanOfCare());
        return dailyNote;
    }
    private ProgressNoteModel declareProgressNote(MedicalNote medicalNote){
        ProgressNote entity = (ProgressNote )medicalNote;
        ProgressNoteModel progressNote = new ProgressNoteModel();
        progressNote.setSubjective(entity.getSubjective());
        progressNote.setObjective(entity.getObjective());
        progressNote.setAssessment(entity.getAssessment());
        progressNote.setPlanOfCare(entity.getPlanOfCare());
        progressNote.setBilling(entity.getBilling());
        return progressNote;
    }
    private QuickDischargeNoteModel declareQuickDischargeNote(MedicalNote medicalNote){
        QuickDischargeNote entity = (QuickDischargeNote )medicalNote;
        return mapper.map(entity,QuickDischargeNoteModel.class);
    }
}
