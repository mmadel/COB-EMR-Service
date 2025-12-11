package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class HomeEnvironment {
    private final String homeLayout;
    private final List<String> environmentalBarriers;
    private final boolean hasHomeHealthCare;
}
