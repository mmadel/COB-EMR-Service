package com.cob.emr.usecases.medical.note.post.init.note;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class ReadActiveOMTTestUseCase {
    private final Map<String, Object> yamlData;

    public ReadActiveOMTTestUseCase() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("omt_test/omt_test_score.yaml")) {
            if (input == null) {
                throw new IllegalStateException("YAML file not found in resources/omt_test/omt_test_score.yaml");
            }
            this.yamlData = mapper.readValue(input, new TypeReference<Map<String, Object>>() {});
        }
    }

    public Map<String, List<String>> read() {
        List<Map<String, Object>> categories = (List<Map<String, Object>>) yamlData.get("category");

        return categories.stream()
                .flatMap(cat -> {
                    List<Map<String, Object>> omtTests = (List<Map<String, Object>>) cat.get("OMTTest");
                    return omtTests.stream();
                })
                .filter(test -> Boolean.TRUE.equals(test.get("active")))
                .collect(Collectors.toMap(
                        test -> (String) test.get("name"),
                        test -> (List<String>) test.get("score")
                ));
    }
}
