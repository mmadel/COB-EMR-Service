package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.WorkStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.DutyLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OccupationalHistory {
    private final String occupation;
    private final WorkStatus workStatus;
    private final LocalDate outOfWorkSince;
    private final LocalDate expectedReturnToWorkDate;
    private final DutyLevel physicalDemandLevel;
    private final boolean isWorkersCompensation;
    private final boolean hasLitigation;
    
    public void validate() {
        if (workStatus == WorkStatus.OFF_WORK && outOfWorkSince == null) {
            throw new IllegalStateException("Off work status requires date");
        }
    }
    
    public boolean isEligibleForWorkersComp() {
        if (workStatus != WorkStatus.OFF_WORK || outOfWorkSince == null) {
            return false;
        }
        long daysOffWork = java.time.temporal.ChronoUnit.DAYS
            .between(outOfWorkSince, LocalDate.now());
        return daysOffWork <= 365 && isWorkersCompensation;
    }
}
