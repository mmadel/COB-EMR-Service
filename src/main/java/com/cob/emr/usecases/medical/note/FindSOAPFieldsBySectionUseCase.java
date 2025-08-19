package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.*;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.SOAPSectionRepository;
import com.cob.emr.usecases.medical.note.soap.filter.SOAPFieldFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FindSOAPFieldsBySectionUseCase {
    @Autowired
    SOAPSectionRepository soapSectionRepository;
    @Autowired
    SOAPFieldFilterService filterService;

    public Map<String, List<SOAPField>> find(String sectionName) {
        SOAPSectionEntity soapSection = soapSectionRepository.findByName(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP section not found"));
        return soapSection.getCategories().stream()
                .collect(Collectors.toMap(
                        NewSOAPSectionCategoryEntity::getName, // Key: Category Name
                        category -> category.getFields().stream()
                                .sorted(Comparator.comparing(
                                        NewSOAPFieldEntity::getInputOrder,
                                        Comparator.nullsLast(Long::compareTo)
                                ))
                                .map(NewSOAPFieldEntity::getValue) // Extract SOAPField from SOAPFieldEntity
                                .collect(Collectors.toList()) // Collect as List<SOAPField>
                ));
    }
    public Map<String, List<SOAPField>> find(String sectionName, Long medicalNoteType) {
        SOAPSectionEntity soapSection = soapSectionRepository.findByName(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("SOAP section not found"));
        return soapSection.getCategories().stream()
                .collect(Collectors.toMap(
                        NewSOAPSectionCategoryEntity::getName,
                        category -> {
                            List<SOAPField> sortedFields = category.getFields().stream()
                                    .filter(field -> field.getNoteTypes().stream()
                                            .anyMatch(nt -> nt.getNoteType().getId().equals(medicalNoteType)))
                                    .sorted(Comparator.comparing(
                                            field -> resolveInputOrder(field, medicalNoteType),
                                            Comparator.nullsLast(Integer::compareTo) // null → last
                                    ))
                                    .map(NewSOAPFieldEntity::getValue)
                                    .collect(Collectors.toList());
                            return sortedFields;
                        }
                ));
    }

    private Integer resolveInputOrder(NewSOAPFieldEntity field, Long medicalNoteType) {
        if (field.getNoteTypes() == null) {
            return null; // will be pushed to the end by nullsLast
        }
        return field.getNoteTypes().stream()
                .filter(nt -> nt.getNoteType().getId().equals(medicalNoteType))
                .map(SoapFieldNoteType::getFieldOrder)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null); // missing order → treated as null → goes last
    }
}
