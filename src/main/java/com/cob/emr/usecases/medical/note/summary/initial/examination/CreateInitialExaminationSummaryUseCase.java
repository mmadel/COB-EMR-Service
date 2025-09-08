package com.cob.emr.usecases.medical.note.summary.initial.examination;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.summary.soap.InitialExaminationSummary;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.summary.initial.examination.sction.subjective.CreateSubjectiveSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateInitialExaminationSummaryUseCase {
    @Autowired
    CreateSubjectiveSummaryUseCase createSubjectiveSummaryUseCase;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public InitialExaminationSummary create(Long noteId) throws IOException {
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        InitialExaminationSummary initialExaminationSummary = new InitialExaminationSummary();
        initialExaminationSummary.setSubjective(createSubjectiveSummaryUseCase.create(initialEvaluation.getSubjective()));
        return initialExaminationSummary;
    }
}

