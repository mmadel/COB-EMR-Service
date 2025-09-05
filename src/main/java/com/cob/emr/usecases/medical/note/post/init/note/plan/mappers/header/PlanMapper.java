package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header;

import java.util.Map;
import java.util.Optional;

public class PlanMapper {

    private static final Map<String, String> PLAN_MAP = Map.ofEntries(
            Map.entry("PL00", "Begin Plan as Outlined"),
            Map.entry("PL01", "Begin Plan per Flowsheet"),
            Map.entry("PL02", "Begin Plan per Protocol"),
            Map.entry("Custom", "Custom")
    );

    public static Optional<String> getPlanLabel(String code) {
        return Optional.ofNullable(PLAN_MAP.get(code));
    }

    public static String getPlanLabelOrDefault(String code, String defaultValue) {
        return PLAN_MAP.getOrDefault(code, defaultValue);
    }
}
