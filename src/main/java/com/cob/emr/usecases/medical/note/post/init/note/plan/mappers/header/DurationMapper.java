package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header;

import java.util.Map;
import java.util.Optional;

public class DurationMapper {

    private static final Map<String, String> DURATION_MAP = Map.ofEntries(
            Map.entry("D00", "N/A"),
            Map.entry("D05", "1 week"),
            Map.entry("D06", "2 weeks"),
            Map.entry("D07", "3 weeks"),
            Map.entry("D08", "4 weeks"),
            Map.entry("D09", "5 weeks"),
            Map.entry("D10", "6 weeks"),
            Map.entry("D11", "8 weeks"),
            Map.entry("D12", "10 weeks"),
            Map.entry("D13", "12 weeks"),
            Map.entry("Custom", "Custom")
    );

    public static Optional<String> getDurationLabel(String code) {
        return Optional.ofNullable(DURATION_MAP.get(code));
    }

    public static String getDurationLabelOrDefault(String code, String defaultValue) {
        return DURATION_MAP.getOrDefault(code, defaultValue);
    }
}
