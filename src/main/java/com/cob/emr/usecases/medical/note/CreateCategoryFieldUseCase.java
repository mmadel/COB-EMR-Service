package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateCategoryFieldUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;

    public void create(List<FieldRequest> requests, String categoryName) {
        SOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        for (FieldRequest request : requests) {
            SOAPFieldEntity field = CreateSoapField.create(request, null);
            if (request.getDependencies() != null) {
                List<SOAPFieldEntity> dependenciesFields = new ArrayList<>();
                traverseFieldRequests(request.getDependencies(), dependenciesFields, field.getValue().getIdField().getId());
                sectionCategory.getFields().addAll(dependenciesFields);
            }
            sectionCategory.getFields().add(field);
        }

        soapSectionCategoryRepository.save(sectionCategory);
    }

    private void traverseFieldRequests(List<FieldRequest> dependencies, List<SOAPFieldEntity> dependenciesFields, String uuid) {
        for (FieldRequest fieldRequest : dependencies) {
            SOAPFieldEntity toBeCreated = createField(fieldRequest, uuid);
            dependenciesFields.add(toBeCreated);
            if (fieldRequest.getDependencies() != null && !fieldRequest.getDependencies().isEmpty()) {
                traverseFieldRequests(fieldRequest.getDependencies(), dependenciesFields, toBeCreated.getValue().getIdField().getId());
            }
        }
    }

    private SOAPFieldEntity createField(FieldRequest fieldRequest, String uuid) {
        SOAPFieldEntity dependent = CreateSoapField.create(fieldRequest, uuid);
        dependent.setFieldName(dependent.getFieldName());
        return dependent;
    }
}
