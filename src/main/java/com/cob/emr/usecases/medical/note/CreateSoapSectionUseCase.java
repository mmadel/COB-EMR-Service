package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.SOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.SOAPSectionEntity;
import com.cob.emr.model.medical.note.SOAPSection;
import com.cob.emr.model.medical.note.SOAPSectionCategory;
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
        List<SOAPSectionCategoryEntity> categoryEntities = createCategoryEntities(soapSection.getCategoryies());
        entity.setCategories(categoryEntities);
        soapSectionRepository.save(entity);
    }

    private List<SOAPSectionCategoryEntity> createCategoryEntities(List<SOAPSectionCategory> categories) {
        return categories.stream()
                .map(soapSectionCategory -> mapper.map(soapSectionCategory, SOAPSectionCategoryEntity.class))
                .collect(Collectors.toList());
    }

}
