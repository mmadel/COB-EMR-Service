package com.cob.emr.usecases.medical.note.soap.profile;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.repositories.medical.note.ObjectiveProfileSOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnAssignSOAPFieldFromProfileUseCase {
    @Autowired
    SOAPFieldNewRepository soapFieldNewRepository;
    @Autowired
    ObjectiveProfileSOAPFieldRepository objectiveProfileSOAPFieldRepository;

    @Transactional
    public void unAssign(String fieldId, Long profileId) {

        NewSOAPFieldEntity parent = soapFieldNewRepository.findByIdFieldId(fieldId);
        List<NewSOAPFieldEntity> child = soapFieldNewRepository.findChild(fieldId);
        List<Long> unAssignFieldsIds = child.stream()
                .map(NewSOAPFieldEntity::getId)
                .collect(Collectors.toList());
        unAssignFieldsIds.add(parent.getId());
        objectiveProfileSOAPFieldRepository.deleteFieldsFromProfile(unAssignFieldsIds,profileId);
    }
}
