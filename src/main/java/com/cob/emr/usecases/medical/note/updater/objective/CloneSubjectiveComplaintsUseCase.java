package com.cob.emr.usecases.medical.note.updater.objective;

import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CloneSubjectiveComplaintsUseCase {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String sourceFieldName = "primaryConcernChiefComplaint";
    private static final String destinationFileName = "currentcomplaintsgains";
    public Subjective cloneComplaints(Subjective obj){
        if (obj == null) {
            return null;
        }

        try {
            // Convert whole object into a Map
            Map<String, Object> root = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});

            // Navigate into "basic"
            Object basicObj = root.get("basic");
            if (basicObj instanceof Map) {
                Map<String, Object> basic = (Map<String, Object>) basicObj;

                // If source exists, copy to destination
                if (basic.containsKey(sourceFieldName)) {
                    basic.put(destinationFileName, basic.get(sourceFieldName));
                }
            }

            // Convert back to original object type
            return mapper.convertValue(root, obj.getClass());

        } catch (Exception e) {
            throw new RuntimeException("Failed to clone complaints field", e);
        }
    }
}
