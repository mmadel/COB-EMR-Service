package com.cob.emr.usecases.medical.note.converter;

import com.cob.emr.model.patient.cases.CaseDiagnosis;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<CaseDiagnosis> convertToCaseDiagnosisList(Object icdObject) {
        try {
            // Convert whatever Object we get into JSON string
            String json = mapper.writeValueAsString(icdObject);

            // Parse JSON into a list of generic ObjectNodes
            List<ObjectNode> nodes = mapper.readValue(json, new TypeReference<List<ObjectNode>>() {
            });

            List<CaseDiagnosis> result = new ArrayList<>();

            for (ObjectNode node : nodes) {
                CaseDiagnosis diagnosis = new CaseDiagnosis();

                // Map JSON fields -> your CaseDiagnosis fields
                if (node.has("code")) {
                    diagnosis.setDiagnosisCode(node.get("code").asText());
                }
                if (node.has("description")) {
                    diagnosis.setDiagnosisDescription(node.get("description").asText());
                }

                result.add(diagnosis);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ICD diagnosis object to CaseDiagnosis list", e);
        }
    }
}
