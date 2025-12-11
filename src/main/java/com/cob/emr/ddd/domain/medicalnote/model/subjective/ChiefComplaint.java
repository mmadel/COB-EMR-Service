package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.SymptomPattern;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ChiefComplaint {
    private final String primaryConcern;
    private final List<String> presentSymptoms;
    private final SymptomPattern symptomPattern;
    private final String functionalImpact;
    private final String accumulatedComplaints;

    public boolean hasComplaint() {
        return primaryConcern != null && !primaryConcern.trim().isEmpty();
    }
}
