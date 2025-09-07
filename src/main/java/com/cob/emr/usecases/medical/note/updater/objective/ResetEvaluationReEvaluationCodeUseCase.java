package com.cob.emr.usecases.medical.note.updater.objective;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;

@Component
public class ResetEvaluationReEvaluationCodeUseCase {
    private static final ObjectMapper mapper = new ObjectMapper();
    public Object resetUntimedCodes(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            // Convert the whole object into a Map
            Map<String, Object> root = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});

            // Extract untimedCodes
            Object rawValue = root.get("untimedCodes");
            if (rawValue == null) {
                return obj; // nothing to update
            }

            Map<String, Object> codes;

            if (rawValue instanceof String) {
                // Parse JSON string to map
                codes = mapper.readValue((String) rawValue, new TypeReference<Map<String, Object>>() {});
            } else {
                // Convert nested object to Map
                codes = mapper.convertValue(rawValue, new TypeReference<Map<String, Object>>() {});
            }

            // Keys to nullify
            String[] keysToTransform = {"97161", "97162", "97163","97164"};
            for (String key : keysToTransform) {
                codes.put(key, null);
            }

            // Put back into root
            root.put("untimedCodes", codes);

            // Convert back to the same type as input
            return mapper.convertValue(root, obj.getClass());

        } catch (Exception e) {
            throw new RuntimeException("Failed to update untimedCodes", e);
        }

    }
}
