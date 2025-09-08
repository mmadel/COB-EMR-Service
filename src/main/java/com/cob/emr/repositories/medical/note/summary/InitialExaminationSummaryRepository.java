package com.cob.emr.repositories.medical.note.summary;

import com.cob.emr.entity.medical.note.summary.InitialExaminationSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InitialExaminationSummaryRepository extends JpaRepository<InitialExaminationSummaryEntity, Long> {
}
