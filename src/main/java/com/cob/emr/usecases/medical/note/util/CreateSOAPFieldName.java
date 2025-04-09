package com.cob.emr.usecases.medical.note.util;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;

public class CreateSOAPFieldName {
    public static void create(NewSOAPFieldEntity soapFieldEntity,
                              String categoryName) {
        StringBuilder name = new StringBuilder();
        name.append(categoryName);
        if (soapFieldEntity.getValue().getParentField() != null)
            name.append("_" + soapFieldEntity.getValue().getParentField());
        if (soapFieldEntity.getValue().getDisplay() != null)
            name.append("_" + toCamelCase(soapFieldEntity.getValue().getDisplay()));
        soapFieldEntity.getValue().setName(name.toString());
        soapFieldEntity.setFieldName(name.toString());
    }

    public static String toCamelCase(String input) {
        // Remove special characters and digits using regex
        String cleanedInput = input.replaceAll("[^a-zA-Z ]", "");

        // Split the cleaned string into words
        String[] words = cleanedInput.split("\\s+");

        // Convert the first word to lowercase and the rest to capitalized form
        StringBuilder camelCaseString = new StringBuilder(words[0].toLowerCase());

        for (int i = 1; i < words.length; i++) {
            camelCaseString.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1).toLowerCase());
        }

        return camelCaseString.toString();
    }
}
