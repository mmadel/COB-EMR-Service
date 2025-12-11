package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.SpecialtyProfile;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.VisitType;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.TreatmentSide;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExaminationContext {
    private final LocalDate dateOfExamination;
    private final LocalDate timeIn;
    private final LocalDate timeOut;
    private final VisitType visitType;
    private final List<TreatmentSide> treatmentSide;
    private final String physicianOrders;
    private final SpecialtyProfile specialtyProfile;

    public void validate() {
        if (dateOfExamination == null) {
            throw new IllegalStateException("Date of examination is required");
        }
    }
}
