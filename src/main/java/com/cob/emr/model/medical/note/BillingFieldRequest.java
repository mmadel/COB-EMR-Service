package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingFieldRequest {
    private String label;
    private String value;
    private String type;
}
