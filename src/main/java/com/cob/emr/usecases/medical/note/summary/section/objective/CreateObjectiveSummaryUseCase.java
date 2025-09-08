package com.cob.emr.usecases.medical.note.summary.section.objective;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.summary.soap.ObjectiveSummary;
import com.cob.emr.usecases.medical.note.post.init.note.score.CreateInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.summary.section.subjective.CreateSubjectiveSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateObjectiveSummaryUseCase {
    @Autowired
    CreateSubjectiveSummaryUseCase createSubjectiveSummaryUseCase;
    @Autowired
    CreateInitialExaminationScoreUseCase createInitialExaminationScoreUseCase;

    public ObjectiveSummary create(InitialEvaluation initialEvaluation) throws IOException {
        ObjectiveSummary objectiveSummary = new ObjectiveSummary();
        objectiveSummary.setOmtScores(createInitialExaminationScoreUseCase.create(initialEvaluation));
        return objectiveSummary;
    }

}
