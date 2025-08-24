package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPSection;
import com.cob.emr.model.medical.note.soap.field.SOAPSectionCategory;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateSoapSectionUseCase {
    @Autowired
    SOAPSectionRepository soapSectionRepository;
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public void create(SOAPSection soapSection) {
        SOAPSectionEntity entity = new SOAPSectionEntity();
        entity.setName(soapSection.getName());
        List<NewSOAPSectionCategoryEntity> categoryEntities = createCategoryEntities(soapSection.getCategories());
        entity.setCategories(categoryEntities);
        soapSectionRepository.save(entity);
    }

    private List<NewSOAPSectionCategoryEntity> createCategoryEntities(List<SOAPSectionCategory> categories) {
        return categories.stream()
                .map(soapSectionCategory -> mapper.map(soapSectionCategory, NewSOAPSectionCategoryEntity.class))
                .collect(Collectors.toList());
    }

}
