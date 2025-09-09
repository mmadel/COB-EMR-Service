package com.cob.emr.usecases.medical.note.summary.section.objective;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.score.InitOMTScore;
import com.cob.emr.model.medical.note.summary.soap.ObjectiveSummary;
import com.cob.emr.usecases.medical.note.post.init.note.score.CreateInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.summary.FindInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.summary.section.subjective.CreateSubjectiveSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateObjectiveSummaryUseCase {
    @Autowired
    CreateSubjectiveSummaryUseCase createSubjectiveSummaryUseCase;
    @Autowired
    CreateInitialExaminationScoreUseCase createInitialExaminationScoreUseCase;
    @Autowired
    FindInitialExaminationScoreUseCase findInitialExaminationScoreUseCase;

    public ObjectiveSummary create(InitialEvaluation initialEvaluation, Long scoreId) throws IOException {
        ObjectiveSummary objectiveSummary = new ObjectiveSummary();
        if (scoreId != null) {
            List<InitOMTScore> omtScores = findInitialExaminationScoreUseCase.find(scoreId);
            objectiveSummary.setOmtScores(omtScores);
            return objectiveSummary;
        }
        return null;
    }

}
