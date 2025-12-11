package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FunctionalStatus {
    private final SelfCareActivities selfCare;
    private final MobilityActivities mobility;
    private final InstrumentalActivities instrumentalActivities;
    private final SleepActivities sleep;
    
    public int calculateDisabilityPoints() {
        int points = 0;
        if (selfCare != null) points += selfCare.getDisabilityPoints();
        if (mobility != null) points += mobility.getDisabilityPoints();
        if (instrumentalActivities != null) points += instrumentalActivities.getDisabilityPoints();
        return points;
    }
}
