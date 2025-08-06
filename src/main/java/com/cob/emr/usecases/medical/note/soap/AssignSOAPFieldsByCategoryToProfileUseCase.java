package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.ObjectiveProfileSOAPField;
import com.cob.emr.entity.medical.note.soap.profile.ObjectiveProfileEntity;
import com.cob.emr.repositories.medical.note.ObjectiveProfileRepository;
import com.cob.emr.repositories.medical.note.ObjectiveProfileSOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignSOAPFieldsByCategoryToProfileUseCase {
    @Autowired
    ObjectiveProfileSOAPFieldRepository objectiveProfileSOAPFieldRepository;
    @Autowired
    ObjectiveProfileRepository objectiveProfileRepository;
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;

    public void assign(String profileName, Long categoryId) {
        List<ObjectiveProfileSOAPField> assignList = new ArrayList<>();
        ObjectiveProfileEntity objectiveProfile = objectiveProfileRepository.findByName(profileName)
                .orElseThrow(() -> new IllegalArgumentException("Objective Profile not found"));
        NewSOAPSectionCategoryEntity category = soapSectionCategoryNewRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));
        category.getFields().stream()
                .forEach(newSOAPFieldEntity -> {
                    ObjectiveProfileSOAPField assignEntity = new ObjectiveProfileSOAPField();
                    assignEntity.setObjectiveProfile(objectiveProfile);
                    assignEntity.setSoapField(newSOAPFieldEntity);
                    assignList.add(assignEntity);
                });
        objectiveProfileSOAPFieldRepository.saveAll(assignList);
    }
}
