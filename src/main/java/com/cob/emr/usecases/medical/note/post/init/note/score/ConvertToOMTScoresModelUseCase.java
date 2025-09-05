package com.cob.emr.usecases.medical.note.post.init.note.score;

import com.cob.emr.model.medical.note.omt.test.score.OMTScore;
import com.cob.emr.model.medical.note.omt.test.score.ScoreValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ConvertToOMTScoresModelUseCase {
    public List<OMTScore> convert(
            Map<String, Object> rawResult,
            Map<String, List<String>> omtTests) {

        List<OMTScore> omtScores = new ArrayList<>();

        for (Map.Entry<String, Object> entry : rawResult.entrySet()) {
            String examName = entry.getKey();
            Object valuesObj = entry.getValue();

            if (valuesObj instanceof Map) {
                Map<String, Object> values = (Map<String, Object>) valuesObj;

                // expected labels from omtTests
                List<String> expectedLabels = omtTests.getOrDefault(examName, Collections.emptyList());

                List<ScoreValue> scoreValues = new ArrayList<>();

                for (Map.Entry<String, Object> valEntry : values.entrySet()) {
                    String rawKey = valEntry.getKey();
                    Object rawValue = valEntry.getValue();

                    // try to match the suffix with an expected label
                    String simplifiedName = expectedLabels.stream()
                            .filter(label -> rawKey.toLowerCase().endsWith(label.toLowerCase()))
                            .findFirst()
                            .orElse(rawKey); // fallback: keep rawKey if no match
                    if (simplifiedName == null || simplifiedName.trim().isEmpty()
                            || simplifiedName.equalsIgnoreCase("empty")
                            || simplifiedName.equalsIgnoreCase("empty_test")) {
                        simplifiedName = "score";
                    }
                    scoreValues.add(new ScoreValue(
                            simplifiedName,
                            rawValue instanceof Number ? ((Number) rawValue).doubleValue() : null
                    ));
                }

                OMTScore omtScore = new OMTScore();
                omtScore.setExamName(examName);
                omtScore.setScoreValue(scoreValues);

                omtScores.add(omtScore);
            }
        }

        return omtScores;
    }
}
