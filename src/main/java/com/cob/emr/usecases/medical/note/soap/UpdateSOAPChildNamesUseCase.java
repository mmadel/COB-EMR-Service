package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateSOAPChildNamesUseCase {
    @Autowired
    SOAPFieldNewRepository soapFieldNewRepository;

    @Transactional
    public Map<String,String> update(String patientID) {
        Map<String,String> fieldNameMap = new HashMap<>();
        NewSOAPFieldEntity parentField= soapFieldNewRepository.findByIdFieldId(patientID);
        if (parentField == null) {
            throw new IllegalArgumentException("Parent field not found for ID: " + patientID);
        }
        String parentNamePart = capitalizeFirstLetter(parentField.getFieldName());
        System.out.println(parentNamePart);
        List<NewSOAPFieldEntity> child = soapFieldNewRepository.findChild(patientID);
        List<NewSOAPFieldEntity> childToBeUpdated = new ArrayList<>();
        child.forEach(
                childField -> {
                    String childNameCamelCase = toCamelCase(childField.getFieldDisplayName());
                    String childName = childField.getFieldName();
                    String updatedChildName = childNameCamelCase +parentNamePart;
                    childField.setFieldName(updatedChildName);
                    childField.getValue().setName(updatedChildName);
                    childToBeUpdated.add(childField);
                    fieldNameMap.put(childName, updatedChildName);
                });
        soapFieldNewRepository.saveAll(childToBeUpdated);
        return fieldNameMap;
    }
    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    public static String toCamelCase(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        String[] words = input.trim().split("[\\s_]+"); // split on space(s) or underscore(s)

        StringBuilder camelCase = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (i == 0) {
                camelCase.append(word);
            } else {
                camelCase.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));
            }
        }

        return camelCase.toString();
    }
}
