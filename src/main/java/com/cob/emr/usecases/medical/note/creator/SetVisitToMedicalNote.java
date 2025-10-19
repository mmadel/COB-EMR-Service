package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.entity.medical.note.types.FullDischargeNote;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;


import java.util.HashMap;
import java.util.Map;

@Component
public class SetVisitToMedicalNote {
    @Autowired
    private ObjectMapper mapper;

    public String setVisitForNonInitialNote(MedicalNote note, Integer visitNumber) {
        try {
            Object subjectiveObj = getSubjectiveObject(note);
            Map<String, Object> subjectiveMap;

            if (subjectiveObj == null) {
                subjectiveMap = new HashMap<>();
            } else if (subjectiveObj instanceof Map) {
                subjectiveMap = (Map<String, Object>) subjectiveObj;
            } else if (subjectiveObj instanceof String) {
                subjectiveMap = mapper.readValue((String) subjectiveObj, new TypeReference<Map<String, Object>>() {});
            } else {
                subjectiveMap = mapper.convertValue(subjectiveObj, new TypeReference<Map<String, Object>>() {});
            }

            // ensure "basic" section exists
            Map<String, Object> basic = (Map<String, Object>) subjectiveMap.get("basic");
            if (basic == null) {
                basic = new HashMap<>();
                subjectiveMap.put("basic", basic);
            }

            // increment visit number
            Integer currentVisit = parseIntSafe(basic.get("visit"));
            Integer newVisit = (visitNumber != null) ? visitNumber : (currentVisit + 1);
            basic.put("visit", newVisit);

            // Set back as Object (not JSON string)
            setSubjectiveObject(note, subjectiveMap);

            return String.valueOf(newVisit);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set visit for " + note.getClass().getSimpleName(), e);
        }
    }

    private int parseIntSafe(Object value) {
        if (value == null) return 0;
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Object getSubjectiveObject(MedicalNote note) {
        if (note instanceof DailyNote) return ((DailyNote) note).getSubjective();
        if (note instanceof ProgressNote) return ((ProgressNote) note).getSubjective();
        if (note instanceof FullDischargeNote) return ((FullDischargeNote) note).getSubjective();
        return null;
    }

    private void setSubjectiveObject(MedicalNote note, Object updatedJson) {
        if (note instanceof DailyNote) ((DailyNote) note).setSubjective(updatedJson);
        if (note instanceof ProgressNote) ((ProgressNote) note).setSubjective(updatedJson);
        if (note instanceof FullDischargeNote) ((FullDischargeNote) note).setSubjective(updatedJson);
    }
}
