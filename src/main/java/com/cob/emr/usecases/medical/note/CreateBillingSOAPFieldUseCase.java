package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.BillingFieldRequest;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.soap.field.CreateBillingSoapField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateBillingSOAPFieldUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;

    public List<Map<String, String>> create(List<BillingFieldRequest> requests, String categoryName) {
        SOAPSectionCategoryEntity sectionCategory = soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        List<SOAPFieldEntity> entities = new ArrayList<>();
        requests.stream().forEach(billingFieldRequest -> {
            entities.add(CreateBillingSoapField.create(billingFieldRequest));
        });
        sectionCategory.getFields().addAll(entities);
        soapSectionCategoryRepository.save(sectionCategory);

        List<Map<String, String>> formattedList = entities.stream()
                .map(soapFieldEntity -> {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("name", soapFieldEntity.getFieldName()); // Level + Field Name
                    obj.put("label_style", "white-space: nowrap; min-width: 500px;");
                    obj.put("style", "margin-left: " + "10px;"); // Adjust margin
                    return obj;
                })
                .collect(Collectors.toList());
        return formattedList;
    }
}
