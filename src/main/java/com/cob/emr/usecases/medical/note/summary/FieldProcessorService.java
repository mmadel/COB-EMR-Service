package com.cob.emr.usecases.medical.note.summary;

import com.cob.emr.service.mapper.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class FieldProcessorService {
    @Autowired
    FieldMappingService fieldMappingService;

    public Map<String, Object> processFields(Map<String, Object> fieldsMap, Object dto, String section, String category) throws IOException {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
            String displayName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // Case 1: Nested map -> recurse
                Map<String, Object> nestedResult = processFields((Map<String, Object>) value, dto,section,category);
                result.put(displayName, nestedResult);

            } else if (value instanceof Iterable) {
                // Case 2: List of field names (like "Time In/Time Out")
                Map<String, Object> subResult = new HashMap<>();
                for (Object fieldDisplayObj : (Iterable<?>) value) {
                    try{
                        String fieldDisplay = String.valueOf(fieldDisplayObj);
                        String categoryFieldName = fieldMappingService
                                .lookupKeyByParentAndChild(section, category,displayName ,fieldDisplay)
                                .orElseThrow(() -> new IllegalArgumentException("Field not found: " + fieldDisplay));

                        Object fieldValue = getFieldValue(dto, categoryFieldName);
                        subResult.put(fieldDisplay, fieldValue);
                    }catch(IllegalArgumentException e){
                        System.out.println("Error " + e);
                    }

                }
                result.put(displayName, subResult);

            } else {
                // Case 3: Leaf field
                try {
                    String categoryFieldName = fieldMappingService
                            .lookupKeyByDisplay(section, category, displayName)
                            .orElseThrow(() -> new IllegalArgumentException("Field not found: " + displayName));

                    Object fieldValue = getFieldValue(dto, categoryFieldName);
                    result.put(displayName, fieldValue);
                }catch(IllegalArgumentException e){
                    System.out.println("Error " + e);
                }
            }
        }
        return result;
    }
    private Object getFieldValue(Object dto, String fieldName) {
        try {
            Field field = dto.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(dto);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error getting field value for: " + fieldName + " from class " + dto.getClass().getSimpleName());
            return null;
        }
    }
}
