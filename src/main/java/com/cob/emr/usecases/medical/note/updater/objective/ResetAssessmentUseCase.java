package com.cob.emr.usecases.medical.note.updater.objective;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResetAssessmentUseCase {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String fieldName = "assessmentDiagnosisAssessment";

    public Object resetDiagnosis(Object obj) {
        if (obj == null || fieldName == null) {
            return obj;
        }

        try {
            // Convert object into a map
            Map<String, Object> root = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });

            // Set the field to null if it exists
            if (root.containsKey(fieldName)) {
                root.put(fieldName, null);
            }

            // Convert back into the same class type
            return mapper.convertValue(root, obj.getClass());

        } catch (Exception e) {
            throw new RuntimeException("Failed to update field: " + fieldName, e);
        }

    }

}
