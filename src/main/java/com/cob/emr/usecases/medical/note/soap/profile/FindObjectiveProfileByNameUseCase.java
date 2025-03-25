package com.cob.emr.usecases.medical.note.soap.profile;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FindObjectiveProfileByNameUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
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
}
