package com.cob.emr.usecases.medical.note.summary;

import com.cob.emr.entity.medical.note.finalize.init.exam.plan.InitialExaminationPlanOfCareEntity;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.repositories.medical.note.InitialExaminationPlanOfCareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindInitialExaminationPlanOfCareUseCase {
    @Autowired
    InitialExaminationPlanOfCareRepository initialExaminationPlanOfCareRepository;

    public InitPlanOfCare find(Long id) {
        InitialExaminationPlanOfCareEntity entity = initialExaminationPlanOfCareRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan of care not found for id " + id));
        return entity.getInitPlanOfCareData();
    }
}
