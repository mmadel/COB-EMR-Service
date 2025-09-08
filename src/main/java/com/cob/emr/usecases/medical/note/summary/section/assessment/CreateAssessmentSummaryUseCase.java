package com.cob.emr.usecases.medical.note.summary.section.assessment;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitAssessment;
import com.cob.emr.usecases.medical.note.post.init.note.plan.CreateInitialExaminationAssessmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateAssessmentSummaryUseCase {
    @Autowired
    CreateInitialExaminationAssessmentUseCase createInitialExaminationAssessmentUseCase;

    public InitAssessment create(InitialEvaluation initialEvaluation) throws IOException {
        return createInitialExaminationAssessmentUseCase.create(initialEvaluation.getAssessment());
    }
}
