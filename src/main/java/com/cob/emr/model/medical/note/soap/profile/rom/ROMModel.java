package com.cob.emr.model.medical.note.soap.profile.rom;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter

public class ROMModel {
    public ROMModel(String name, Map<String, String[]> values) {
        this.name = name;
        this.values = values;
    }
    private String name;
    private Map<String, String[]> values;
}
