package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.finalize.init.exam.score.InitialExaminationScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InitialExaminationScoreRepository extends JpaRepository<InitialExaminationScoreEntity, Long> {
    Optional<InitialExaminationScoreEntity> findByInitialExaminationNoteId(Long id);
}
