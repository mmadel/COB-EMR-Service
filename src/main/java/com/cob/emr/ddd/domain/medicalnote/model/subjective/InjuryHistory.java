package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.OnsetType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InjuryHistory {
    private final LocalDate injuryOnsetDate;
    private final List<OnsetType> onsetType;
    private final String mechanismOfInjury;
    private final List<String> symptomsAtOnset;
    private final boolean isPriorEpisode;
    private final PriorEpisodeHistory priorEpisodeHistory;
    private final SurgeryHistory surgeryHistory;

}
