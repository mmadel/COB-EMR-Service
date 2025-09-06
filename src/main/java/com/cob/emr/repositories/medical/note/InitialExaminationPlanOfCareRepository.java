package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.finalize.init.exam.plan.InitialExaminationPlanOfCareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InitialExaminationPlanOfCareRepository extends JpaRepository<InitialExaminationPlanOfCareEntity, Long> {

    Optional<InitialExaminationPlanOfCareEntity> findByInitialExaminationNoteId(Long id);
}
