package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MobilityActivities {
    private final ActivityLevel walking;
    private final ActivityLevel transfers;
    private final ActivityLevel positionChanges;
    private final ActivityLevel negotiatingObstacles;
    private final String assistiveDeviceUsed;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (walking != null) points += walking.getPoints() * 2;
        if (transfers != null) points += transfers.getPoints();
        if (positionChanges != null) points += positionChanges.getPoints();
        if (negotiatingObstacles != null) points += negotiatingObstacles.getPoints();
        return points;
    }
}
