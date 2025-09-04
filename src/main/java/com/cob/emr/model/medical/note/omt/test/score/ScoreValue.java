package com.cob.emr.model.medical.note.omt.test.score;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScoreValue {
    private String scoreName;
    private Double scoreValue;

    public ScoreValue(String key, Double aDouble) {
        this.scoreName = key;
        this.scoreValue = aDouble;
    }
}
