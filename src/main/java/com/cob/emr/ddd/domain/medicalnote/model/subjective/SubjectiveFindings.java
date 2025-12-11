package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class SubjectiveFindings {
    private final ExaminationContext examinationContext;
    private final ChiefComplaint chiefComplaint;
    private final InjuryHistory injuryHistory;
    private final PainAssessment painAssessment;
    private final MedicalHistory medicalHistory;
    private final FunctionalAssessment functionalAssessment;

    public void validate() {
        if (examinationContext == null) {
            throw new IllegalStateException("Examination context is required");
        }
        examinationContext.validate();
        if (painAssessment != null) {
            painAssessment.validate();
        }
    }

    public SubjectiveFindings excludeHistoryOfPresentCondition() {
        InjuryHistory modifiedHistory = injuryHistory != null ? injuryHistory.toBuilder().mechanismOfInjury(null).build() : null;
        return this.toBuilder().injuryHistory(modifiedHistory).build();
    }
}
