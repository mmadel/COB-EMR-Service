package com.cob.emr.model.medical.note;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MedicalResponse {
    private Long medicalNotId;
    private String visitNumber;

}
