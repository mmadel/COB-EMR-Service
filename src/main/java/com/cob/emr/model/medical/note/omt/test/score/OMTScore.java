package com.cob.emr.model.medical.note.omt.test.score;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OMTScore {
    private String examName;
    private List<ScoreValue> scoreValue;
}
