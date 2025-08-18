package com.cob.emr.usecases.medical.note.soap.filter;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SOAPFieldFilterService {

    private final Map<String, SOAPFieldFilterStrategy> strategies;
    public SOAPFieldFilterService(Map<String, SOAPFieldFilterStrategy> strategies) {
        this.strategies = strategies;
    }
    public List<NewSOAPFieldEntity> apply(String context, List<NewSOAPFieldEntity> fields) {
        SOAPFieldFilterStrategy strategy = strategies.get(context);
        if (strategy == null) {
            // Default: return unchanged list
            return fields;
        }
        return strategy.filter(fields);
    }
}
