package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import com.cob.emr.repositories.medical.note.SOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
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
    SOAPFieldNewRepository soapFieldRepository;
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryRepository;
    @Autowired
    PersistSOAPFieldsUseCase persistSOAPFieldsUseCase;

    public List<Map<String, String>> update(List<FieldRequest> requests, String fieldID, String categoryName) {
        NewSOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        NewSOAPFieldEntity ddd = soapFieldRepository.findByIdFieldId(fieldID);
        Map<Integer, List<NewSOAPFieldEntity>> levelMap = new HashMap<>();
        if (ddd != null) {
            traverseFieldRequests(requests, levelMap, fieldID, 0);
        }
        return persistSOAPFieldsUseCase.persist(sectionCategory, levelMap);

    }

    private void traverseFieldRequests(
            List<FieldRequest> dependencies,
            Map<Integer, List<NewSOAPFieldEntity>> levelMap,
            String uuid,
            int level) {
        if (dependencies == null || dependencies.isEmpty()) {
            return;
        }

        for (FieldRequest fieldRequest : dependencies) {
            NewSOAPFieldEntity toBeCreated = createField(fieldRequest, uuid);

            // Add to the appropriate level in the map
            levelMap.computeIfAbsent(level, k -> new ArrayList<>()).add(toBeCreated);

            // Recursive call with the next level
            traverseFieldRequests(fieldRequest.getDependencies(), levelMap,
                    toBeCreated.getValue().getIdField().getId(), level + 1);
        }
    }

    private NewSOAPFieldEntity createField(FieldRequest fieldRequest, String uuid) {
        NewSOAPFieldEntity dependent = CreateSoapField.create(fieldRequest, uuid);
        dependent.setFieldName(dependent.getFieldName());
        dependent.setFieldDisplayName(fieldRequest.getFieldName());
        return dependent;
    }
}
