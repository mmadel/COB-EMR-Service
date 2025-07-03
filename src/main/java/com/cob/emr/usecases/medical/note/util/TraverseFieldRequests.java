package com.cob.emr.usecases.medical.note.util;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.usecases.medical.note.soap.field.CreateSoapField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraverseFieldRequests {
    public  static void traverse(
            List<FieldRequest> dependencies,
            Map<Integer, List<NewSOAPFieldEntity>> levelMap,
            String uuid,
            int level) {
        if (dependencies == null || dependencies.isEmpty()) {
            return;
        }

        for (FieldRequest fieldRequest : dependencies) {
            NewSOAPFieldEntity toBeCreated = createField(fieldRequest, uuid);

            // Add to the appropriate level in the map
            levelMap.computeIfAbsent(level, k -> new ArrayList<>()).add(toBeCreated);

            // Recursive call with the next level
            traverse(fieldRequest.getDependencies(), levelMap,
                    toBeCreated.getValue().getIdField().getId(), level + 1);
        }
    }
    private static NewSOAPFieldEntity createField(FieldRequest fieldRequest, String uuid) {
        NewSOAPFieldEntity dependent = CreateSoapField.create(fieldRequest, uuid);
        dependent.setFieldName(dependent.getFieldName());
        dependent.setFieldDisplayName(fieldRequest.getFieldName());
        return dependent;
    }
}
