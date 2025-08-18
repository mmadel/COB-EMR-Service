package com.cob.emr.usecases.medical.note.soap.filter;

import com.cob.emr.model.medical.note.soap.field.SOAPField;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("InitialExamination")
public class InitialExaminationNoteSOAPFieldFilter implements SOAPFieldFilterStrategy {
    @Override
    public List<SOAPField> filter(List<SOAPField> fields) {
        return fields.stream()
                //.filter(f -> f.getValue() != null)
                .collect(Collectors.toList());
    }
}
