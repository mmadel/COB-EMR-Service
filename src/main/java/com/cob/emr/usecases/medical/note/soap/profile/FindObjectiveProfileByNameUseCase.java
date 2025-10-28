package com.cob.emr.usecases.medical.note.soap.profile;

import com.cob.emr.entity.medical.note.soap.field.*;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.ObjectiveProfileSOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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

    public Map<String, List<SOAPField>> find(String name) {
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

    //    public Map<String, List<SOAPField>> findFields(String name){
//
//        Map<Long, String> fieldIdToCategoryMap = StreamSupport.stream(soapSectionCategoryNewRepository.findAll().spliterator(),false)
//                .collect(Collectors.toList())
//                .stream()
//                .flatMap(category -> category.getFields().stream()
//                        .map(field -> new AbstractMap.SimpleEntry<>(field.getId(), category.getName()))
//                )
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        List<NewSOAPFieldEntity> fields = objectiveProfileSOAPFieldRepository.findFieldsByProfileName(name);
//        return fields.stream()
//                .filter(field -> fieldIdToCategoryMap.containsKey(field.getId()))
//                .collect(Collectors.groupingBy(
//                        field -> fieldIdToCategoryMap.get(field.getId()),
//                        Collectors.mapping(NewSOAPFieldEntity::getValue, Collectors.toList())
//                ));
//    }
    public Map<String, List<SOAPField>> findFields(String profileName) {

        // Fetch all categories with their fields
        Map<Long, String> fieldIdToCategoryName = new HashMap<>();
        List<NewSOAPSectionCategoryEntity> categories = StreamSupport.stream(soapSectionCategoryNewRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
        for (NewSOAPSectionCategoryEntity category : categories) {
            for (NewSOAPFieldEntity field : category.getFields()) {
                fieldIdToCategoryName.put(field.getId(), category.getName());
            }
        }

        // Fetch ordered ObjectiveProfileSOAPFields
        List<ObjectiveProfileSOAPField> profileFields =
                objectiveProfileSOAPFieldRepository.findByProfileNameOrdered(profileName);

        // Group by category name, preserving order
        return profileFields.stream()
                .filter(opf -> {
                    Long fieldId = opf.getSoapField().getId();
                    return fieldIdToCategoryName.containsKey(fieldId);
                })
                .collect(Collectors.groupingBy(
                        opf -> fieldIdToCategoryName.get(opf.getSoapField().getId()),
                        LinkedHashMap::new,
                        Collectors.mapping(opf -> opf.getSoapField().getValue(), Collectors.toList())
                ));
    }
}
