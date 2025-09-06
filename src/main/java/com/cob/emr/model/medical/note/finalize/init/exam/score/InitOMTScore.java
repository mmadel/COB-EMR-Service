package com.cob.emr.model.medical.note.finalize.init.exam.score;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InitOMTScore {
    private String examName;
    private List<ScoreValue> scoreValue;
}
