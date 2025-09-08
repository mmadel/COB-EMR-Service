package com.cob.emr.usecases.medical.note.summary;

import com.cob.emr.entity.medical.note.summary.InitialExaminationSummaryEntity;
import com.cob.emr.model.medical.note.summary.soap.init.InitialExaminationSummaryDTO;
import com.cob.emr.repositories.medical.note.summary.InitialExaminationSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindInitialExaminationSummaryUseCase {
    @Autowired
    InitialExaminationSummaryRepository initialExaminationSummaryRepository;

    public InitialExaminationSummaryDTO find(Long id) {
        InitialExaminationSummaryEntity entity = initialExaminationSummaryRepository.findByNoteId(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan of care not found for id " + id));
        return map(entity);
    }

    private InitialExaminationSummaryDTO map(InitialExaminationSummaryEntity entity) {
        InitialExaminationSummaryDTO dto = new InitialExaminationSummaryDTO();
        dto.setSubjective(entity.getSubjective());
        dto.setObjective(entity.getObjective());
        dto.setPlanOfCare(entity.getPlanOfCare());
        dto.setAssessmentSummary(entity.getAssessmentSummary());
        dto.setPatientData(entity.getPatientData());
        return dto;
    }
}
