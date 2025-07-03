package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class IMedicalNote {
    protected Long id;
    protected String createdBy;
    protected String finalizedBy;
}
