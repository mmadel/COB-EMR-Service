package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelfCareActivities {
    private final ActivityLevel hygiene;
    private final ActivityLevel grooming;
    private final ActivityLevel bathing;
    private final ActivityLevel dressing;
    private final ActivityLevel toileting;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (hygiene != null) points += hygiene.getPoints();
        if (grooming != null) points += grooming.getPoints();
        if (bathing != null) points += bathing.getPoints();
        if (dressing != null) points += dressing.getPoints();
        if (toileting != null) points += toileting.getPoints();
        return points;
    }
}
