package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ReadPlanCategorisesUseCase {
    private final Map<String, Object> yamlData;

    public ReadPlanCategorisesUseCase() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream inputStream = new ClassPathResource("plan/plan_of_care_procedures.yaml").getInputStream()) {
            this.yamlData = mapper.readValue(inputStream, Map.class);
        }
    }

    public Map<String, List<String>> get(String category) {
        switch (category) {
            case "Procedures":
                return getProcedures();
            case "Modalities":
                return getModalities();
            case "Specialties":
                return getSpecialties();
            default:
                throw new IllegalArgumentException("Category not found");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<String>> getCategory(String category) {
        Object raw = yamlData.get(category);
        if (raw instanceof Map) {
            return ((Map<String, Object>) raw).entrySet().stream()
                    .collect(
                            java.util.stream.Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> {
                                        if (e.getValue() instanceof List) {
                                            return (List<String>) e.getValue();
                                        } else {
                                            return Collections.emptyList();
                                        }
                                    }
                            )
                    );
        }
        return Collections.emptyMap();
    }

    private Map<String, List<String>> getProcedures() {
        return getCategory("Procedures");
    }

    private Map<String, List<String>> getModalities() {
        return getCategory("Modalities");
    }

    private Map<String, List<String>> getSpecialties() {
        return getCategory("Specialties");
    }
}
