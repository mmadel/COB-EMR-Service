package com.cob.emr.model.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuditFieldChange {
    private String field;
    private Object oldValue;
    private Object newValue;
}
