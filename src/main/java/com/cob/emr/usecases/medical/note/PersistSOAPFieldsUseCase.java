package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersistSOAPFieldsUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryRepository;

    public List<Map<String, String>> persist(NewSOAPSectionCategoryEntity sectionCategory, Map<Integer, List<NewSOAPFieldEntity>> levelMap) {
        List<NewSOAPFieldEntity> allEntities = levelMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        allEntities.stream()
                .forEach(newSOAPFieldEntity -> {
                    CreateSOAPFieldName.create(newSOAPFieldEntity, sectionCategory.getName());
                });
        sectionCategory.getFields().addAll(allEntities);
        soapSectionCategoryRepository.save(sectionCategory);
        List<Map<String, String>> formattedList = null;
        if(levelMap.size() > 0)
            formattedList = createStyles(levelMap);
        else{
            levelMap.put(0,sectionCategory.getFields());
            formattedList =createStyles(levelMap);
        }
        return formattedList;
    }

    private List<Map<String, String>> createStyles(Map<Integer, List<NewSOAPFieldEntity>> levelMap) {
        return levelMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(field -> {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("name", field.getFieldName()); // Level + Field Name
                    obj.put("label_style", "white-space: nowrap; min-width: 500px;");
                    obj.put("style", "margin-left: " + (10 * (entry.getKey() + 1)) + "px;"); // Adjust margin
                    return obj;
                }))
                .collect(Collectors.toList());
    }
}
