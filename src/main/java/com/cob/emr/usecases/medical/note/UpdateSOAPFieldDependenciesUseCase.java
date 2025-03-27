package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateSOAPFieldDependenciesUseCase {
    @Autowired
    SOAPFieldRepository soapFieldRepository;
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
    @Autowired
    PersistSOAPFieldsUseCase persistSOAPFieldsUseCase;

    public List<Map<String, String>> update(List<FieldRequest> requests, String fieldID, String categoryName) {
        SOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        SOAPFieldEntity ddd = soapFieldRepository.findByIdFieldId(fieldID);
        Map<Integer, List<SOAPFieldEntity>> levelMap = new HashMap<>();
        if (ddd != null) {
            traverseFieldRequests(requests, levelMap, fieldID, 0);
        }
        return persistSOAPFieldsUseCase.persist(sectionCategory, levelMap);

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
