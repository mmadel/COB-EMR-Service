package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitAssessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateInitialExaminationAssessmentUseCase {
    @Autowired
    private MedicalNoteRepository medicalNoteRepository;
    public InitAssessment create(Assessment assessment) throws IOException {
        InitAssessment initAssessment = new InitAssessment();
        initAssessment.setShortTermGoals(constructShortTerm(assessment.getGoals()));
        initAssessment.setLongTermGoals(constructLongTerm(assessment.getGoals()));
        initAssessment.setProblems(constructProblems(assessment.getProblems()));
        initAssessment.setAssessmentDiagnosis(assessment.getAssessmentDiagnosisAssessment().toString());
        initAssessment.setRehabPotential(assessment.getRehabPotentialAssessment());
        initAssessment.setPatientEducation(assessment.getParentPatientEducationAssessment().toString());
        return initAssessment;
    }

    private List<String> constructShortTerm(ArrayList<Object> goals) {
        return goals.stream()
                .map(g -> (Map<String, Object>) g)
                .filter(g -> "Short Term".equals(g.get("term")))
                .map(g -> g.get("period") + " | " + g.get("met") + " | " + g.get("description"))
                .collect(Collectors.toList());
    }

    private List<String> constructLongTerm(ArrayList<Object> goals) {
        return goals.stream()
                .map(g -> (Map<String, Object>) g)
                .filter(g -> "Long Term".equals(g.get("term")))
                .map(g -> g.get("period") + " | " + g.get("met") + " | " + g.get("description"))
                .collect(Collectors.toList());
    }
    private List<String> constructProblems(ArrayList<Object> problems){
        return problems.stream()
                .map(Object::toString)   // convert each Object to String
                .collect(Collectors.toList());
    }
}
