package com.cob.emr.model.audit;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditLogModel {
    private Long id;
    private Long clinicId;
    private String clinicName;
    private String entityName;
    private Long entityId;
    private String action;
    private List<AuditFieldChange> changes;
    private String performedByUuid;
    private String performedByName;
    private Long performedAt;
}
