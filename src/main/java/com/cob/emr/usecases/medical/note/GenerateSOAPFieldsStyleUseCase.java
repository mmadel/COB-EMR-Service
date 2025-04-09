package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenerateSOAPFieldsStyleUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;

    public List<Map<String, String>> generate(Long categoryId) {
        List<NewSOAPFieldEntity> fields = soapSectionCategoryNewRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"))
                .getFields();
        return fields.stream()
                .map(entry -> {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("name", entry.getFieldName()); // Level + Field Name
                    obj.put("label_style", "white-space: nowrap; min-width: 500px;");
                    obj.put("style", "margin-left: " + (10 * (1)) + "px;"); // Adjust margin
                    return obj;
                })
                .collect(Collectors.toList());
    }
}
