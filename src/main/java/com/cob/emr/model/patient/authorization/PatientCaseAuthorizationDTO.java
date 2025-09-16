package com.cob.emr.model.patient.authorization;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Setter
@Getter
public class PatientCaseAuthorizationDTO {

    private Long id;

    @NotBlank(message = "authName is required")
    private String authName;

    @NotBlank(message = "authType is required")
    private String authType;

    @NotNull(message = "authNumber is required")
    @Min(value = 1, message = "authNumber must be >= 1")
    private Integer authNumber;

    // Dates as milliseconds
    @NotNull(message = "effectiveStart is required")
    private Long effectiveStart;

    @NotNull(message = "effectiveEnd is required")
    private Long effectiveEnd;

    // insurance subfields (flatted for simplicity)
    private String insuranceName;
    private Long insuranceId;

    public PatientCaseAuthorizationDTO() {}
}
