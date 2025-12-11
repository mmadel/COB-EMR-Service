package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class SleepActivities {
    private final ActivityLevel sleepQuality;
    private final List<String> sleepingPositions;
    private final String surfaceType;
    private final boolean hasDisturbedSleep;
}
