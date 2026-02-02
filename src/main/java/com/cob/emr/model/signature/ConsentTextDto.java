package com.cob.emr.model.signature;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsentTextDto {
    private String versionCode;
    private String title;
    private String text;
    private String textHash;
}
