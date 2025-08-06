package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;
import com.cob.emr.usecases.medical.note.util.TraverseFieldRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateCategoryFieldUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryRepository;
    @Autowired
    PersistSOAPFieldsUseCase persistSOAPFieldsUseCase;

    public List<Map<String, String>> create(List<FieldRequest> requests, String categoryName) {
        NewSOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        Map<Integer, List<NewSOAPFieldEntity>> levelMap = new HashMap<>();
        for (FieldRequest request : requests) {
            NewSOAPFieldEntity field = CreateSoapField.create(request, null);
            if (request.getDependencies() != null) {
                TraverseFieldRequests.traverse(request.getDependencies(), levelMap, field.getValue().getIdField().getId(), 0);
            }
            CreateSOAPFieldName.create(field, sectionCategory.getName());
            sectionCategory.getFields().add(field);
        }
        List<Map<String, String>> result = persistSOAPFieldsUseCase.persist(sectionCategory, levelMap);
        //Update SOAP Field Entity Name

        return result;
    }

}
