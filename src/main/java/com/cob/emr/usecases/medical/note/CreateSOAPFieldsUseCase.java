package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.SOAPField;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateSOAPFieldsUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
    @Autowired
    ModelMapper mapper;
    public void create(List<SOAPField> fields , String categoryName){
        SOAPSectionCategoryEntity sectionCategory= soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));

        List<SOAPFieldEntity> soapFieldEntities = fields.stream()
                .map(soapField -> {
                    SOAPFieldEntity soapFieldEntity = new SOAPFieldEntity();
                    soapFieldEntity.setValue(soapField);
                    return soapFieldEntity;
                })
                .collect(Collectors.toList());
        sectionCategory.setFields(soapFieldEntities);
        soapSectionCategoryRepository.save(sectionCategory);
    }
}
