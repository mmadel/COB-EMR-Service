package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class SurgeryHistory {
    private final boolean hasSurgery;
    private final LocalDate dateOfSurgery;
    private final String typeOfSurgery;
    private final boolean hadHospitalization;
}
