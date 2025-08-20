package com.cob.emr.usecases.medical.note.soap.filter;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPField;

import java.util.List;

public interface SOAPFieldFilterStrategy {
    List<NewSOAPFieldEntity> filter(List<NewSOAPFieldEntity> fields);
}
