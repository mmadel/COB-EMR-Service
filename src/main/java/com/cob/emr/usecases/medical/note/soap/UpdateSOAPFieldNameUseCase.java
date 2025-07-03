package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import com.cob.emr.repositories.medical.note.SOAPFieldRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UpdateSOAPFieldNameUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;
    @Autowired
    SOAPFieldNewRepository soapFieldNewRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public void update(Long categoryId) {
        List<NewSOAPFieldEntity> fields = new ArrayList<>();
        Optional<NewSOAPSectionCategoryEntity> optional = soapSectionCategoryNewRepository.findById(categoryId);
        NewSOAPSectionCategoryEntity category = optional.orElseThrow(() -> new IllegalArgumentException("not found "));
        category.getFields().stream()
                .forEach(soapFieldEntity -> {
                    NewSOAPFieldEntity entity = mapper.map(soapFieldEntity, NewSOAPFieldEntity.class);
                    CreateSOAPFieldName.create(entity, category.getName());

                    fields.add(entity);
                });
        soapFieldNewRepository.saveAll(fields);
    }
}
