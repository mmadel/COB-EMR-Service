package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateCategoryFieldUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;

    public List<Map<String, String>> create(List<FieldRequest> requests, String categoryName) {
        SOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        Map<Integer, List<SOAPFieldEntity>> levelMap = new HashMap<>();
        for (FieldRequest request : requests) {
            SOAPFieldEntity field = CreateSoapField.create(request, null);
            if (request.getDependencies() != null) {
                traverseFieldRequests(request.getDependencies(), levelMap, field.getValue().getIdField().getId(), 0);
                //sectionCategory.getFields().addAll(dependenciesFields);
            }
            sectionCategory.getFields().add(field);
        }

        List<SOAPFieldEntity> allEntities = levelMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        sectionCategory.getFields().addAll(allEntities);
        soapSectionCategoryRepository.save(sectionCategory);
        List<Map<String, String>> formattedList = levelMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(field -> {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("name", field.getFieldName()); // Level + Field Name
                    obj.put("label_style", "white-space: nowrap; min-width: 500px;");
                    obj.put("style", "margin-left: " + (10 * (entry.getKey() + 1)) + "px;"); // Adjust margin
                    return obj;
                }))
                .collect(Collectors.toList());
        return formattedList;
    }

    private void traverseFieldRequests(
            List<FieldRequest> dependencies,
            Map<Integer, List<SOAPFieldEntity>> levelMap,
            String uuid,
            int level) {
        if (dependencies == null || dependencies.isEmpty()) {
            return;
        }

        for (FieldRequest fieldRequest : dependencies) {
            SOAPFieldEntity toBeCreated = createField(fieldRequest, uuid);

            // Add to the appropriate level in the map
            levelMap.computeIfAbsent(level, k -> new ArrayList<>()).add(toBeCreated);

            // Recursive call with the next level
            traverseFieldRequests(fieldRequest.getDependencies(), levelMap,
                    toBeCreated.getValue().getIdField().getId(), level + 1);
        }
    }

    private SOAPFieldEntity createField(FieldRequest fieldRequest, String uuid) {
        SOAPFieldEntity dependent = CreateSoapField.create(fieldRequest, uuid);
        dependent.setFieldName(dependent.getFieldName());
        dependent.setFieldDisplayName(fieldRequest.getFieldName());
        return dependent;
    }
}
