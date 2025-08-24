package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.exception.business.SOAPException;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import com.cob.emr.usecases.medical.note.soap.AssignSOAPFieldToProfileUseCase;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;
import com.cob.emr.usecases.medical.note.util.CreateFieldsStyle;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;
import com.cob.emr.usecases.medical.note.util.TraverseFieldRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreateFieldDependenciesUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryRepository;
    @Autowired
    AssignSOAPFieldToProfileUseCase assignSOAPFieldToProfileUseCase;
    public List<Map<String, String>> create(List<FieldRequest> requests, String parentId, String categoryName , String profile) throws SOAPException {
        NewSOAPSectionCategoryEntity category = soapSectionCategoryNewRepository.findByName(categoryName)
                .orElseThrow(() -> new SOAPException(HttpStatus.CONFLICT, SOAPException.CATEGORY_NOT_FOUND, new Object[]{}));
        Map<Integer, List<NewSOAPFieldEntity>> levelMap = new HashMap<>();
        List<NewSOAPFieldEntity> fields = new ArrayList<>();
        for (FieldRequest request : requests) {
            NewSOAPFieldEntity field = CreateSoapField.create(request, null);
            if (request.getDependencies() != null) {
                TraverseFieldRequests.traverse(request.getDependencies(), levelMap, field.getValue().getIdField().getId(), 0);
            }
            field.getValue().setParentField(parentId);
            CreateSOAPFieldName.create(field, category.getName());
            fields.add(field);
        }
        category.getFields().addAll(fields);
        levelMap.put(0, fields);
        soapSectionCategoryRepository.save(category);
        if(profile !=null)
            assignToProfile(profile,fields);
        return prepareStyles(levelMap);
    }

    private List<Map<String, String>> prepareStyles(Map<Integer, List<NewSOAPFieldEntity>> levelMap){
        return CreateFieldsStyle.createStyles(levelMap);
    }
    private void assignToProfile(String profileName , List<NewSOAPFieldEntity> fields){
        fields.stream()
                .forEach(field->{
                    assignSOAPFieldToProfileUseCase.assign(profileName, field.getValue().getIdField().getId());
                });

    }
}
