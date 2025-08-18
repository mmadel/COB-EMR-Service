package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.MedicalNoteTypeEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.model.medical.note.soap.field.SoapFieldsByMedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteTypeRepository;
import com.cob.emr.repositories.medical.note.SOAPFieldNewRepository;
import com.cob.emr.repositories.medical.note.SOAPSectionCategoryNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AssignSOAPFieldsToMedicalNoteUseCase {
    @Autowired
    SOAPSectionCategoryNewRepository soapSectionCategoryNewRepository;
    @Autowired
    MedicalNoteTypeRepository medicalNoteTypeRepository;
    @Autowired
    SOAPFieldNewRepository soapFieldRepository;

    @Transactional
    public void assign(SoapFieldsByMedicalNoteRequest soapFieldsByMedicalNoteRequest) {
        MedicalNoteTypeEntity medicalNoteType = medicalNoteTypeRepository.findById(soapFieldsByMedicalNoteRequest.getMedicalNotType())
                .orElseThrow(() -> new IllegalArgumentException("Medical Note Type is not found"));
        NewSOAPSectionCategoryEntity category = soapSectionCategoryNewRepository.findById(soapFieldsByMedicalNoteRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category is not found"));
        List<NewSOAPFieldEntity> categoryFields = category.getFields().stream()
                .collect(Collectors.toList());
        List<NewSOAPFieldEntity> parentList = category.getFields().stream()
                .filter(field -> soapFieldsByMedicalNoteRequest.getFields().contains(field.getValue().getDisplay()))
                .collect(Collectors.toList());
        List<NewSOAPFieldEntity> fieldEntities = buildHierarchy(parentList, categoryFields);
        fieldEntities.forEach(newSOAPFieldEntity -> {
            newSOAPFieldEntity.getNoteTypes().add(medicalNoteType);
        });
        soapFieldRepository.saveAll(fieldEntities);
    }

    public List<NewSOAPFieldEntity> buildHierarchy(
            List<NewSOAPFieldEntity> parentList,
            List<NewSOAPFieldEntity> categoryFields) {

        // Group children by parentId
        Map<String, List<NewSOAPFieldEntity>> childrenByParentId =
                categoryFields.stream()
                        .filter(f -> f.getValue().getParentField() != null)
                        .collect(Collectors.groupingBy(f -> f.getValue().getParentField()));

        List<NewSOAPFieldEntity> result = new ArrayList<>();

        for (NewSOAPFieldEntity parent : parentList) {
            result.add(parent); // add parent itself
            result.addAll(getChildrenRecursive(
                    parent.getValue().getIdField().getId(), childrenByParentId));
        }

        return result;
    }

    private List<NewSOAPFieldEntity> getChildrenRecursive(
            String parentId,
            Map<String, List<NewSOAPFieldEntity>> childrenByParentId) {

        List<NewSOAPFieldEntity> children =
                childrenByParentId.getOrDefault(parentId, Collections.emptyList());

        List<NewSOAPFieldEntity> result = new ArrayList<>(children);
        for (NewSOAPFieldEntity child : children) {
            String childId = child.getValue().getIdField().getId();
            result.addAll(getChildrenRecursive(childId, childrenByParentId));
        }
        return result;
    }
}
