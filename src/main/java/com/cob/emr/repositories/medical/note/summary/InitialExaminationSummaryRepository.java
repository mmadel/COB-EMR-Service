package com.cob.emr.repositories.medical.note.summary;

import com.cob.emr.entity.medical.note.summary.InitialExaminationSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InitialExaminationSummaryRepository extends JpaRepository<InitialExaminationSummaryEntity, Long> {
    Optional<InitialExaminationSummaryEntity> findByNoteId(Long Id);
}
