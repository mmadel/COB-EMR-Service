package com.cob.emr.model.medical.note.soap.field.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingFieldRequest {
    private String label;
    private String value;
    private String type;
}
