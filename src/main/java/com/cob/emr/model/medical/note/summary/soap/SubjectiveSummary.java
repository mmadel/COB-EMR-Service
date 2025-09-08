package com.cob.emr.model.medical.note.summary.soap;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class SubjectiveSummary {
    private Map<String, Object> basicInformation;
}
