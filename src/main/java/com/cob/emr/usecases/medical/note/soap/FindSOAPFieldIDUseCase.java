package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.exception.business.SOAPException;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindSOAPFieldIDUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;

    public String find(String displayName, String categoryName) throws SOAPException {
        NewSOAPSectionCategoryEntity category = soapSectionCategoryNewRepository.findByName(categoryName)
                .orElseThrow(() -> new SOAPException(HttpStatus.CONFLICT, SOAPException.CATEGORY_NOT_FOUND, new Object[]{}));

        NewSOAPFieldEntity soapField = category.getFields().stream()
                .filter(field -> field.getFieldDisplayName() !=null && field.getFieldDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new SOAPException(HttpStatus.CONFLICT, SOAPException.FIELD_NOT_FOUND, new Object[]{}));
        return soapField.getValue().getIdField().getId();
    }
}

