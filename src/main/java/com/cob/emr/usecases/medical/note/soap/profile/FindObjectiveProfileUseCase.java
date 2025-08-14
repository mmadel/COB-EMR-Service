package com.cob.emr.usecases.medical.note.soap.profile;

import com.cob.emr.entity.medical.note.soap.profile.ObjectiveProfileEntity;
import com.cob.emr.model.medical.note.soap.profile.ObjectiveProfile;
import com.cob.emr.repositories.medical.note.ObjectiveProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindObjectiveProfileUseCase {
    @Autowired
    ObjectiveProfileRepository objectiveProfileRepository;
    @Autowired
    ModelMapper mapper;

    public List<ObjectiveProfile> findAll() {
        List<ObjectiveProfileEntity> objectiveProfileEntities = StreamSupport.stream(objectiveProfileRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return objectiveProfileEntities.stream()
                .filter(ObjectiveProfileEntity::isActive)
                .map(objectiveProfileEntity -> mapper.map(objectiveProfileEntity, ObjectiveProfile.class))
                .collect(Collectors.toList());
    }
}
