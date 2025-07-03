package com.cob.emr.usecases.medical.note.util;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateFieldsStyle {

    public static List<Map<String, String>> createStyles(Map<Integer, List<NewSOAPFieldEntity>> levelMap) {
        return levelMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(field -> {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("name", field.getFieldName()); // Level + Field Name
                    obj.put("label_style", "white-space: nowrap; min-width: 500px;");
                    obj.put("style", "margin-left: " + (10 * (entry.getKey() + 1)) + "px;"); // Adjust margin
                    return obj;
                }))
                .collect(Collectors.toList());
    }
}
