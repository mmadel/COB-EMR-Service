package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PriorEpisodeHistory {
    private final int numberOfEpisodes;
    private final Integer yearOfFirstEpisode;
    private final String ageRangeAtFirstEpisode;
    private final List<String> previousTreatments;
}
