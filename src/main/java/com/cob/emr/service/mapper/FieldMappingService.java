package com.cob.emr.service.mapper;

import com.cob.emr.model.medical.note.mapper.FieldDefinition;
import com.cob.emr.model.medical.note.mapper.FieldYamlWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FieldMappingService {

    private final ObjectMapper mapper;

    public FieldMappingService() {
        this.mapper = new ObjectMapper(new YAMLFactory());
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private FieldYamlWrapper loadYaml(String section, String category) throws IOException {
        String path = String.format("mappings/%s/%s.yaml",
                section.toLowerCase(),
                category.replace(" ", "-"));
        ClassPathResource resource = new ClassPathResource(path);
        return mapper.readValue(resource.getInputStream(), FieldYamlWrapper.class);
    }

    /**
     * Unified lookup method:
     * - If displayName is a parent → returns all its children names
     * - If displayName is a leaf → returns its own name as a single-element list
     */
    public Optional<List<String>> lookupByDisplay(String section, String category, String displayName) throws IOException {
        FieldYamlWrapper wrapper = loadYaml(section, category);
        return findRecursive(wrapper.getFields(), displayName);
    }
    public Optional<String> lookupKeyByDisplay(String section, String category, String displayName) throws IOException {
        FieldYamlWrapper wrapper = loadYaml(section, category);
        return findKeyNonRecursive(wrapper.getFields(), displayName);
    }
    public Optional<String> lookupKeyByParentAndChild(String section, String category,
                                                      String parentDisplay, String childDisplay) throws IOException {
        FieldYamlWrapper wrapper = loadYaml(section, category);
        return findKeyByParentAndChild(wrapper.getFields(), parentDisplay, childDisplay);
    }

    private Optional<String> findKeyByParentAndChild(List<FieldDefinition> fields,
                                                     String parentDisplay, String childDisplay) {
        if (fields == null) return Optional.empty();

        for (FieldDefinition field : fields) {
            // Match parent at current level
            if (parentDisplay.equalsIgnoreCase(field.getDisplay())) {
                if (field.getChildren() != null) {
                    // Search children of the matched parent
                    for (FieldDefinition child : field.getChildren()) {
                        if (childDisplay.equalsIgnoreCase(child.getDisplay())) {
                            return Optional.of(child.getName());
                        }
                    }
                }
            }

            // Continue searching deeper for the parent
            Optional<String> nested = findKeyByParentAndChild(field.getChildren(), parentDisplay, childDisplay);
            if (nested.isPresent()) {
                return nested;
            }
        }
        return Optional.empty();
    }
    private Optional<String> findKeyNonRecursive(List<FieldDefinition> fields, String displayName) {
        if (fields == null) return Optional.empty();

        for (FieldDefinition field : fields) {
            if (displayName.equalsIgnoreCase(field.getDisplay())) {
                return Optional.of(field.getName()); // return the key of the matched display
            }
        }
        return Optional.empty();
    }

    private Optional<List<String>> findRecursive(List<FieldDefinition> fields, String displayName) {
        if (fields == null) return Optional.empty();

        for (FieldDefinition field : fields) {
            if (displayName.equalsIgnoreCase(field.getDisplay())) {
                if (field.getChildren() != null && !field.getChildren().isEmpty()) {
                    return Optional.of(collectNames(field.getChildren()));
                } else {
                    return Optional.of(List.of(field.getName()));
                }
            }
            Optional<List<String>> childResult = findRecursive(field.getChildren(), displayName);
            if (childResult.isPresent()) {
                return childResult;
            }
        }
        return Optional.empty();
    }

    private List<String> collectNames(List<FieldDefinition> fields) {
        List<String> names = new ArrayList<>();
        for (FieldDefinition field : fields) {
            names.add(field.getName());
            if (field.getChildren() != null && !field.getChildren().isEmpty()) {
                names.addAll(collectNames(field.getChildren()));
            }
        }
        return names;
    }

    /**
     * Build hierarchy from flat list of fields.
     */
    private List<FieldDefinition> buildHierarchy(List<FieldDefinition> flatFields) {
        Map<String, FieldDefinition> lookup = flatFields.stream()
                .collect(Collectors.toMap(FieldDefinition::getId, f -> f));

        List<FieldDefinition> roots = new ArrayList<>();

        for (FieldDefinition field : flatFields) {
            if (field.getParentId() == null) {
                roots.add(field);
            } else {
                FieldDefinition parent = lookup.get(field.getParentId());
                if (parent != null) {
                    parent.getChildren().add(field);
                }
            }
        }
        return roots;
    }
}
