package com.cob.emr.model.webhook.valueobject;

import lombok.Value;

@Value
public class Diagnosis {
    String code;
    String description;
    int order;
}
