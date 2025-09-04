package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.omt.score.InitialExaminationScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InitialExaminationScoreRepository extends JpaRepository<InitialExaminationScoreEntity, Long> {
}
