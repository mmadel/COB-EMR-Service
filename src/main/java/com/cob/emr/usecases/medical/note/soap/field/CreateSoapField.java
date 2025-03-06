package com.cob.emr.usecases.medical.note.soap.field;

import com.cob.emr.entity.medical.note.SOAPFieldEntity;
import com.cob.emr.model.medical.note.FieldRequest;
import com.cob.emr.model.medical.note.SOAPField;
import com.cob.emr.model.medical.note.SOAPIDField;

import java.util.UUID;

public class CreateSoapField {
    static int counter = 0;

    public static SOAPFieldEntity create(FieldRequest fieldRequest, String parentUUID) {
        SOAPField field = new SOAPField();
        String fieldName = (counter++) + "_" + createFieldName(fieldRequest.getFieldName());
        field.setName(fieldName);
        field.setDisplay(fieldRequest.isIgnoreDisplay() ? null : fieldRequest.getFieldName());
        field.setType(fieldRequest.getFieldType());
        field.setSelectValue(fieldRequest.getSelectValue());
        field.setValue(fieldRequest.getValue());
        SOAPIDField soapidField = new SOAPIDField();
        soapidField.setId(UUID.randomUUID().toString());
        soapidField.setValueChange(fieldRequest.getChangeValue());
        field.setIdField(soapidField);
        field.setParentField(parentUUID);
        SOAPFieldEntity soapFieldEntity = new SOAPFieldEntity();
        soapFieldEntity.setValue(field);
        soapFieldEntity.setFieldName(field.getName());
        return soapFieldEntity;
    }

    public static String creatFieldDisplayName(String input) {
        if (input != null && input.matches("\\w+")) {
            return null; // Single word, return null
        }
        return input; // Not a single word, return the input
    }

    private static String createFieldName(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        // Remove special characters and split by spaces
        String[] words = input.replaceAll("[^a-zA-Z ]", "").split(" ");

        if (words.length == 1) {
            return words[0].substring(0, 1).toLowerCase() + words[0].substring(1);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(words[0].substring(0, 1).toLowerCase()).append(words[0].substring(1));
            for (int i = 1; i < words.length; i++) {
                sb.append(words[i].substring(0, 1).toUpperCase()).append(words[i].substring(1));
            }
            return sb.toString();
        }
    }
}
