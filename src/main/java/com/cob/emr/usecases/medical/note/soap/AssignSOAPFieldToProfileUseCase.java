package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.ObjectiveProfileSOAPField;
import com.cob.emr.entity.medical.note.soap.profile.ObjectiveProfileEntity;
import com.cob.emr.repositories.medical.note.ObjectiveProfileRepository;
import com.cob.emr.repositories.medical.note.ObjectiveProfileSOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignSOAPFieldToProfileUseCase {
    @Autowired
    ObjectiveProfileRepository objectiveProfileRepository;
    @Autowired
    SOAPFieldNewRepository soapFieldNewRepository;
    @Autowired
    ObjectiveProfileSOAPFieldRepository objectiveProfileSOAPFieldRepository;

    public void assign(String profileName, String fieldId) {
        ObjectiveProfileEntity objectiveProfile = objectiveProfileRepository.findByName(profileName)
                .orElseThrow(() -> new IllegalArgumentException("Objective Profile not found"));
        NewSOAPFieldEntity newSOAPFieldEntity = soapFieldNewRepository.findByIdFieldId(fieldId);
        ObjectiveProfileSOAPField assignEntity = new ObjectiveProfileSOAPField();
        assignEntity.setObjectiveProfile(objectiveProfile);
        assignEntity.setSoapField(newSOAPFieldEntity);
        objectiveProfileSOAPFieldRepository.save(assignEntity);
    }
}
