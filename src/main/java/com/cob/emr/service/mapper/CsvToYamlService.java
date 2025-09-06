package com.cob.emr.service.mapper;

import com.cob.emr.service.mapper.dto.FieldEntry;
import com.cob.emr.service.mapper.dto.FieldWrapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvToYamlService {

    public File convertCsvToYaml(MultipartFile csvFile) throws IOException {
        Map<String, FieldNode> nodes = new LinkedHashMap<>();

        // Parse CSV
        try (Reader reader = new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String fieldId = record.get("field_id").trim();
                String display = record.get("field_display").trim();
                String name = record.get("field_name").trim();
                String parentId = record.get("parent_field_id").trim();
                if (parentId.isEmpty() || parentId.equalsIgnoreCase("null")) {
                    parentId = null;
                }

                FieldNode node = new FieldNode(fieldId, display, name, parentId);
                nodes.put(fieldId, node);
            }
        }

        // Build hierarchy
        List<FieldNode> roots = new ArrayList<>();
        for (FieldNode node : nodes.values()) {
            if (node.getParentId() == null) {
                roots.add(node);
            } else {
                FieldNode parent = nodes.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    // no parent found → treat as root
                    roots.add(node);
                }
            }
        }

        // Prepare YAML structure
        Map<String, Object> yamlRoot = new LinkedHashMap<>();
        yamlRoot.put("fields", roots);

        File yamlFile = File.createTempFile("output-", ".yaml");
        YAMLMapper mapper = new YAMLMapper();
        mapper.configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true);
        mapper.writeValue(yamlFile, yamlRoot);

        return yamlFile;
    }

    // Inner class to represent a node
    public static class FieldNode {
        private String id;
        private String display;
        private String name;
        private String parentId;
        private List<FieldNode> children = new ArrayList<>();

        public FieldNode(String id, String display, String name, String parentId) {
            this.id = id;
            this.display = display;
            this.name = name;
            this.parentId = parentId;
        }

        public String getId() { return id; }
        public String getDisplay() { return display; }
        public String getName() { return name; }
        public String getParentId() { return parentId; }
        public List<FieldNode> getChildren() { return children; }
    }
}
