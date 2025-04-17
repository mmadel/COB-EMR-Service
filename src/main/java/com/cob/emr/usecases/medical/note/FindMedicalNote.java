package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.*;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.model.medical.note.IMedicalNote;
import com.cob.emr.model.medical.note.types.daily.note.DailyNoteModel;
import com.cob.emr.model.medical.note.types.discharge.FullDischargeNoteModel;
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
        if(medicalNote instanceof FullDischargeNote)
            return declareFullDischargeNote(medicalNote);
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
        initialExamination.setId(medicalNote.getId());
        initialExamination.setCreatedBy(medicalNote.getCreatedBy());
        initialExamination.setFinalizedBy(medicalNote.getFinalizedBy());
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
        dailyNote.setId(medicalNote.getId());
        dailyNote.setCreatedBy(medicalNote.getCreatedBy());
        dailyNote.setFinalizedBy(medicalNote.getFinalizedBy());
        dailyNote.setSubjective(entity.getSubjective());
        dailyNote.setObjective(entity.getObjective());
        dailyNote.setAssessment(entity.getAssessment());
        dailyNote.setPlanOfCare(entity.getPlanOfCare());
        return dailyNote;
    }
    private ProgressNoteModel declareProgressNote(MedicalNote medicalNote){
        ProgressNote entity = (ProgressNote )medicalNote;
        ProgressNoteModel progressNote = new ProgressNoteModel();
        progressNote.setId(medicalNote.getId());
        progressNote.setCreatedBy(medicalNote.getCreatedBy());
        progressNote.setFinalizedBy(medicalNote.getFinalizedBy());
        progressNote.setSubjective(entity.getSubjective());
        progressNote.setObjective(entity.getObjective());
        progressNote.setAssessment(entity.getAssessment());
        progressNote.setPlanOfCare(entity.getPlanOfCare());
        progressNote.setBilling(entity.getBilling());
        return progressNote;
    }
    private QuickDischargeNoteModel declareQuickDischargeNote(MedicalNote medicalNote){
        QuickDischargeNote entity = (QuickDischargeNote )medicalNote;
        QuickDischargeNoteModel quickDischargeNote=mapper.map(entity,QuickDischargeNoteModel.class);
        quickDischargeNote.setId(medicalNote.getId());
        quickDischargeNote.setCreatedBy(medicalNote.getCreatedBy());
        quickDischargeNote.setFinalizedBy(medicalNote.getFinalizedBy());
        return mapper.map(entity,QuickDischargeNoteModel.class);
    }
    private IMedicalNote declareFullDischargeNote(MedicalNote medicalNote) {
        FullDischargeNote entity = (FullDischargeNote )medicalNote;
        FullDischargeNoteModel fullDischargeNote = new FullDischargeNoteModel();
        fullDischargeNote.setId(medicalNote.getId());
        fullDischargeNote.setCreatedBy(medicalNote.getCreatedBy());
        fullDischargeNote.setFinalizedBy(medicalNote.getFinalizedBy());
        fullDischargeNote.setSubjective(entity.getSubjective());
        fullDischargeNote.setObjective(entity.getObjective());
        fullDischargeNote.setAssessment(entity.getAssessment());
        fullDischargeNote.setPlanOfCare(entity.getPlanOfCare());
        fullDischargeNote.setBilling(entity.getBilling());
        return fullDischargeNote;
    }
}
