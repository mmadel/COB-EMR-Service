package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers;

import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Modalities;


import java.lang.reflect.Field;

public class ModalitiesMapper {
    public static Object getFieldValue(Modalities modalitiesObj, String fieldName) {
        try {
            Field field = Modalities.class.getDeclaredField(fieldName);
            field.setAccessible(true); // allow access to private/public fields
            return field.get(modalitiesObj); // return value
        } catch (NoSuchFieldException e) {
            System.err.println("⚠ Field not found in Modalities: " + fieldName);
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
