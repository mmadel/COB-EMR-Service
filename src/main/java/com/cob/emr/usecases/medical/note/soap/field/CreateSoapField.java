package com.cob.emr.usecases.medical.note.soap.field;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.model.medical.note.soap.field.SOAPIDField;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;

import java.util.UUID;

public class CreateSoapField {

    public static NewSOAPFieldEntity create(FieldRequest fieldRequest, String parentUUID) {
        SOAPField field = new SOAPField();
        field.setDisplay(fieldRequest.isIgnoreDisplay() ? null : fieldRequest.getFieldName());
        field.setType(fieldRequest.getFieldType());
        field.setSelectValue(fieldRequest.getSelectValue());
        field.setValue(fieldRequest.getValue());
        field.setOmtFieldRequest(fieldRequest.getOmtFieldRequest());
        SOAPIDField soapidField = new SOAPIDField();
        soapidField.setId(UUID.randomUUID().toString());
        soapidField.setValueChange(fieldRequest.getChangeValue());
        field.setIdField(soapidField);
        field.setChangeValueSelect(fieldRequest.getChangeValueSelect());
        field.setParentField(parentUUID);
        NewSOAPFieldEntity soapFieldEntity = new NewSOAPFieldEntity();
        soapFieldEntity.setValue(field);
        soapFieldEntity.setFieldDisplayName(fieldRequest.getFieldName());

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

        // Allow numbers and letters but remove special characters
        String[] words = input.replaceAll("[^a-zA-Z0-9 ]", "").trim().split("\\s+");

        if (words.length == 0) { // Handle case where all characters are removed
            return "";
        }

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
    public static void createName(NewSOAPFieldEntity soapFieldEntity,
                                  String categoryName) {
        StringBuilder name = new StringBuilder();
        name.append(categoryName);
        if (soapFieldEntity.getValue().getParentField() != null)
            name.append("_" + soapFieldEntity.getValue().getParentField());
        if (soapFieldEntity.getValue().getDisplay() != null)
            name.append("_" + CreateSOAPFieldName.toCamelCase(soapFieldEntity.getValue().getDisplay()));
        soapFieldEntity.getValue().setName(name.toString());
        soapFieldEntity.setFieldName(name.toString());
    }

}
