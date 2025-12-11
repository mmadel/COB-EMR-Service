package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ModalityType;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ModalityPurpose;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ModalityPrescription {
    private final ModalityType modalityType;
    private final String modalitySubtype;
    private final List<ModalityPurpose> purposes;
    private final String duration;
    private final String additionalNotes;
}
