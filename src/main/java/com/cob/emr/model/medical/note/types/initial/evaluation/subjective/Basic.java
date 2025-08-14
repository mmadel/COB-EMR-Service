package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Basic {
    public String dateOfInitialExamination;
    public String timeInTimeOut;
    public String timeIn;
    public String timeOut;
    public String visit;
    public Object treatmentSide;
    public String specificPhysicianOrders;
    public String specificPhysicianOrdersText;
    public String injuryOnsetDateChangeOfStatusDate;
    public Boolean chronic;
    public Boolean insidious;
    public Boolean newInjury;
    public String newInjuryText;
    public String noNewAggravationInjury;
    public String surgeryPerformed;
    public String dateOfSurgery;
    public String typeOfSurgery;
    public String priorHospitalization;
    public String from;
    public String to;
    public String pelvicProfileSpeechProfile;
    public String movementBasedSpinalAssessmentQuestionnaire;
    public String workMechanicalStresses;
    public String workMechanicalStressesText;
    public String leisureMechanicalStresses;
    public String leisureMechanicalStressesText;
    public String functionalDisabilityFromPresentEpisode;
    public String functionalDisabilityFromPresentEpisodeText;
    public String functionalDisabilityScore;
    public String functionalDisabilityScoreText;
    public String presentSymptoms;
    public String presentSymptomsSelect;
    public String commencedAsResultOf;
    public String commencedAsResultofText;
    public String noApparentReason;
    public String symptomsAtOnset;
    public List<String> symptomsAtOnsetSelect;
    public String symptomsAtOnsetText;
    public String constantSymptoms;
    public List<String> constantSymptomsSelect;
    public String constantSymptomsText;
    public String intermittentSymptoms;
    public List<String> intermittentSymptomsSelect;
    public String intermittentSymptomsText;
    public String bladder;
    public String bladderSelect;
    public String bladderText;
    public String historyOfPresentConditionMechanismOfInjury;
    public String primaryConcernChiefComplaint;
    private Object icdtenCodeDiagnosis;
    private Object icdtenCodeTreatmentDiagnosis;
}
