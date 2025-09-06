package com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header;

import java.util.Map;
import java.util.Optional;

public class FrequencyMapper {

    private static final Map<String, String> FREQUENCY_MAP = Map.ofEntries(
            Map.entry("F00", "N/A"),
            Map.entry("F01", "1 time visit only"),
            Map.entry("F02", "1 time a week"),
            Map.entry("F03", "1-2 times a week"),
            Map.entry("F04", "2 times a week"),
            Map.entry("F05", "2-3 times a week"),
            Map.entry("F06", "3 times a week"),
            Map.entry("F07", "3-4 times a week"),
            Map.entry("F08", "4 times a week"),
            Map.entry("F09", "BID"),
            Map.entry("F10", "QD"),
            Map.entry("F11", "PRN"),
            Map.entry("Custom", "Custom")
    );

    public static Optional<String> getFrequencyLabel(String code) {
        return Optional.ofNullable(FREQUENCY_MAP.get(code));
    }

    public static String getFrequencyLabelOrDefault(String code, String defaultValue) {
        return FREQUENCY_MAP.getOrDefault(code, defaultValue);
    }
}
