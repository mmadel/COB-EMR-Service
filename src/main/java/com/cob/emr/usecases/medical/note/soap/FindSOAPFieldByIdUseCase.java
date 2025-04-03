package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import com.cob.emr.repositories.medical.note.SOAPFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FindSOAPFieldByIdUseCase {
    @Autowired
    SOAPFieldRepository soapFieldRepository;
    public Map<String,String> find(String fieldId){
        SOAPFieldEntity soapFieldEntity = soapFieldRepository.findByFieldId(fieldId)
                .orElseThrow( () -> new IllegalArgumentException("Soap Field Not Found"));
        Map<String,String> result = new HashMap<>();
        result.put(soapFieldEntity.getValue().getIdField().getId() ,soapFieldEntity.getFieldName());
        return result;
    }
}
