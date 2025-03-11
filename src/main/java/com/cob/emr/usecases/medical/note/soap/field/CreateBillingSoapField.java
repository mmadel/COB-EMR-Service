package com.cob.emr.usecases.medical.note.soap.field;

import com.cob.emr.entity.medical.note.SOAPFieldEntity;
import com.cob.emr.model.medical.note.BillingFieldRequest;
import com.cob.emr.model.medical.note.SOAPField;
import com.cob.emr.model.medical.note.SOAPIDField;

import java.util.UUID;

public class CreateBillingSoapField {
    public static SOAPFieldEntity create(BillingFieldRequest request) {
        SOAPField field = new SOAPField();
        field.setName(request.getValue());
        field.setDisplay(request.getLabel());
        field.setType(request.getType());
        SOAPIDField soapidField = new SOAPIDField();
        soapidField.setId(UUID.randomUUID().toString());
        soapidField.setValueChange(null);
        field.setIdField(soapidField);
        SOAPFieldEntity soapFieldEntity = new SOAPFieldEntity();
        soapFieldEntity.setValue(field);
        soapFieldEntity.setFieldName(field.getName());
        return soapFieldEntity;
    }
}
