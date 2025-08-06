package com.cob.emr.usecases.medical.note.soap.profile;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.ObjectiveProfileSOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindObjectiveProfileByNameUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
    @Autowired
    ObjectiveProfileSOAPFieldRepository objectiveProfileSOAPFieldRepository;

    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;
    public Map<String, List<SOAPField>> find(String name){
        List<SOAPSectionCategoryEntity> list = soapSectionCategoryRepository.findByNameContaining(name);
        System.out.println("");
        return list.stream()
                .collect(Collectors.toMap(
                        SOAPSectionCategoryEntity::getName, // Key: Category Name
                        category -> category.getFields().stream()
                                .map(SOAPFieldEntity::getValue) // Extract SOAPField from SOAPFieldEntity
                                .collect(Collectors.toList()) // Collect as List<SOAPField>
                ));
    }

    public Map<String, List<SOAPField>> findFields(String name){

        Map<Long, String> fieldIdToCategoryMap = StreamSupport.stream(soapSectionCategoryNewRepository.findAll().spliterator(),false)
                .collect(Collectors.toList())
                .stream()
                .flatMap(category -> category.getFields().stream()
                        .map(field -> new AbstractMap.SimpleEntry<>(field.getId(), category.getName()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<NewSOAPFieldEntity> fields = objectiveProfileSOAPFieldRepository.findFieldsByProfileName(name);
        return fields.stream()
                .filter(field -> fieldIdToCategoryMap.containsKey(field.getId()))
                .collect(Collectors.groupingBy(
                        field -> fieldIdToCategoryMap.get(field.getId()),
                        Collectors.mapping(NewSOAPFieldEntity::getValue, Collectors.toList())
                ));
    }
}
