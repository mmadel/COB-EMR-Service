package com.cob.emr.usecases.medical.note.summary;

import com.cob.emr.entity.medical.note.finalize.init.exam.score.InitialExaminationScoreEntity;
import com.cob.emr.model.medical.note.finalize.init.exam.score.InitOMTScore;
import com.cob.emr.repositories.medical.note.InitialExaminationScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindInitialExaminationScoreUseCase {

    @Autowired
    InitialExaminationScoreRepository initialExaminationScoreRepository;
    public List<InitOMTScore> find(Long id){
        InitialExaminationScoreEntity entity = initialExaminationScoreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Score not found for id " + id));
        return entity.getScoreData();
    }
}
