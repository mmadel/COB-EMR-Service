package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers;



import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Specialties;

import java.lang.reflect.Field;

public class SpecialtiesMapper {
    public static Object getFieldValue(Specialties specialtiesObj, String fieldName) {
        try {
            if (fieldName != null && !fieldName.isEmpty() && Character.isDigit(fieldName.charAt(0))) {
                fieldName = "_"+ fieldName ;
            }
            Field field = Specialties.class.getDeclaredField(fieldName);
            field.setAccessible(true); // allow access to private/public fields
            return field.get(specialtiesObj); // return value
        } catch (NoSuchFieldException e) {
            System.err.println("⚠ Field not found in Specialties: " + fieldName);
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
