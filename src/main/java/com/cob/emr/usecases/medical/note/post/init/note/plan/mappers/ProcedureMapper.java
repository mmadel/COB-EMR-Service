package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers;

import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Procedures;

import java.lang.reflect.Field;

public class ProcedureMapper {

    public static Object getFieldValue(Procedures proceduresObj, String fieldName) {
        try {
            Field field = Procedures.class.getDeclaredField(fieldName);
            field.setAccessible(true); // allow access to private/public fields
            return field.get(proceduresObj); // return value
        } catch (NoSuchFieldException e) {
            System.err.println("⚠ Field not found in Procedures: " + fieldName);
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
