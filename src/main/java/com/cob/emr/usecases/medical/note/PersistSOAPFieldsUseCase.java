package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersistSOAPFieldsUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
    public List<Map<String, String>> persist(SOAPSectionCategoryEntity sectionCategory, Map<Integer, List<SOAPFieldEntity>> levelMap){
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
}
