package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SOAPSection {
    private Long id;
    private String name;
    List<SOAPSectionCategory> categories;
}
