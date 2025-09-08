package com.cob.emr.model.medical.note.summary.soap;

import com.cob.emr.model.medical.note.finalize.init.exam.score.InitOMTScore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ObjectiveSummary {
    List<InitOMTScore> omtScores;
}
