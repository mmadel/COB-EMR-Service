package com.cob.emr.usecases.medical.note.post.init.note;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class FindValueFromRawDataUseCase {
    public  Map<String, Object> extractValues(Object objective, List<String> keys) {
        Map<String, Object> result = new HashMap<>();
        if (!(objective instanceof Map)) {
            return result;
        }

        Map<String, Object> objMap = (Map<String, Object>) objective;

        // Search recursively in case the key could be nested
        for (String key : keys) {
            Object value = findValue(objMap, key);
            result.put(key, value);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Object findValue(Map<String, Object> map, String targetKey) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(targetKey)) {
                return entry.getValue();
            }
            if (entry.getValue() instanceof Map) {
                Object nested = findValue((Map<String, Object>) entry.getValue(), targetKey);
                if (nested != null) {
                    return nested;
                }
            }
        }
        return null;
    }
}
