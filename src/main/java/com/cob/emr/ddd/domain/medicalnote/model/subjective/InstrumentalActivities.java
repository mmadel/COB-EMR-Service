package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstrumentalActivities {
    private final ActivityLevel housekeeping;
    private final ActivityLevel shopping;
    private final ActivityLevel foodPreparation;
    private final ActivityLevel transportation;
    private final ActivityLevel financialManagement;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (housekeeping != null) points += housekeeping.getPoints();
        if (shopping != null) points += shopping.getPoints();
        if (foodPreparation != null) points += foodPreparation.getPoints();
        if (transportation != null) points += transportation.getPoints();
        return points;
    }
}
