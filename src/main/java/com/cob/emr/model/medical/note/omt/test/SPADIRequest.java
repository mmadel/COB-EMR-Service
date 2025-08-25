package com.cob.emr.model.medical.note.omt.test;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class SPADIRequest {
    Map<String, Integer> pain;
    Map<String, Integer> disability;
}
