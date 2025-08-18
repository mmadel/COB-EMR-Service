package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.field.*;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.repositories.medical.note.SOAPSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FindSOAPFieldsBySectionUseCase {
    @Autowired
    SOAPSectionRepository soapSectionRepository;

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
}
