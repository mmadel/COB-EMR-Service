package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateSOAPCategoryFieldUseCase {
    @Autowired
    SOAPSectionCategoryRepository soapSectionCategoryRepository;

    public void update(SOAPField field , String categoryName){
        SOAPSectionCategoryEntity sectionCategory= soapSectionCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP Category not found"));

        SOAPFieldEntity soapFieldEntity = new SOAPFieldEntity();
        soapFieldEntity.setValue(field);
        sectionCategory.getFields().add(soapFieldEntity);
        soapSectionCategoryRepository.save(sectionCategory);
    }
}
