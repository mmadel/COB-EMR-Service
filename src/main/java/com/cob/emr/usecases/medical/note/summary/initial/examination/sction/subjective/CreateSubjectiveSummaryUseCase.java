package com.cob.emr.usecases.medical.note.summary.initial.examination.sction.subjective;

import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Basic;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.summary.reader.SubjectiveReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreateSubjectiveSummaryUseCase {
    private final String SECTION = "subjective";
    private final String BASIC_CATEGORY = "basicInformation";
    @Autowired
    SubjectiveReaderService subjectiveReaderService;
    @Autowired
    FieldMappingService fieldMappingService;

    public SubjectiveSummary create(Subjective subjective) throws IOException {
        SubjectiveSummary subjectiveSummary = new SubjectiveSummary();
        subjectiveSummary.setBasicInformation(constructBasicInformation(subjective.getBasic()));
        return subjectiveSummary;
    }
    private Map<String, Object> constructBasicInformation(Basic basic) throws IOException {
        String file = "basic.information" + ".yaml";
        Map<String, Object> displayBasicInformation=  subjectiveReaderService.loadYaml(file);

        Map.Entry<String, Object> entry = displayBasicInformation.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String,Object> fields=(Map<String,Object>) entry.getValue();
        return processFields(fields,basic);
    }
    @SuppressWarnings("unchecked")
    private Map<String, Object> processFields(Map<String, Object> fieldsMap, Basic basic) throws IOException {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
            String displayName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // Case 1: Nested map -> recurse
                Map<String, Object> nestedResult = processFields((Map<String, Object>) value, basic);
                result.put(displayName, nestedResult);

            } else if (value instanceof Iterable) {
                // Case 2: List of field names (like "Time In/Time Out")
                Map<String, Object> subResult = new HashMap<>();
                for (Object fieldDisplayObj : (Iterable<?>) value) {
                    try{
                        String fieldDisplay = String.valueOf(fieldDisplayObj);
                        String categoryFieldName = fieldMappingService
                                .lookupKeyByParentAndChild(SECTION, BASIC_CATEGORY,displayName ,fieldDisplay)
                                .orElseThrow(() -> new IllegalArgumentException("Field not found: " + fieldDisplay));

                        Object fieldValue = getFieldValue(basic, categoryFieldName);
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
                            .lookupKeyByDisplay(SECTION, BASIC_CATEGORY, displayName)
                            .orElseThrow(() -> new IllegalArgumentException("Field not found: " + displayName));

                    Object fieldValue = getFieldValue(basic, categoryFieldName);
                    result.put(displayName, fieldValue);
                }catch(IllegalArgumentException e){
                    System.out.println("Error " + e);
                }
            }
        }
        return result;
    }
    private Object getFieldValue(Basic basic, String fieldName) {
        try {
            Field field = Basic.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(basic);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error getting field value for: " + fieldName, e);
        }
    }
}
