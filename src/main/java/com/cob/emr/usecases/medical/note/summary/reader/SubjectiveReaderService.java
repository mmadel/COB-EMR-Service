package com.cob.emr.usecases.medical.note.summary.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class SubjectiveReaderService {
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    /**
     * Loads a YAML file from resources/soap/subjective/ and returns it as a Map.
     *
     * @param fileName the YAML filename (e.g., "basic.information.yaml")
     * @return parsed YAML as Map
     * @throws IOException if file not found or parsing fails
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> loadYaml(String fileName) throws IOException {
        return mapper.readValue(
                new ClassPathResource("soap/subjective/" + fileName).getInputStream(),
                Map.class
        );
    }
}
