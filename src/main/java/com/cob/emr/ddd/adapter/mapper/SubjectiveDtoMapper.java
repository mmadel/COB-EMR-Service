package com.cob.emr.ddd.adapter.mapper;


import com.cob.emr.ddd.domain.medicalnote.model.subjective.MedicalHistory;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.*;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.*;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.*;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Maps Subjective DTOs (flat structure from Angular) to SubjectiveFindings domain model
 * Extracts fields from Basic.java and groups them into proper Value Objects
 */
@Component
public class SubjectiveDtoMapper {

    /**
     * Main entry point - maps Subjective DTO to SubjectiveFindings domain
     */
    public SubjectiveFindings toDomain(Subjective subjectiveDto) {
        if (subjectiveDto == null) {
            return null;
        }

        // Extract from flat Basic DTO and create structured domain VOs
        ExaminationContext examinationContext = mapExaminationContext(subjectiveDto.getBasic());
        ChiefComplaint chiefComplaint = mapChiefComplaint(subjectiveDto.getBasic());
        InjuryHistory injuryHistory = mapInjuryHistory(subjectiveDto.getBasic(), subjectiveDto.getMedicalHistory());
        PainAssessment painAssessment = mapPainAssessment(subjectiveDto.getPain());
        MedicalHistory medicalHistory = mapMedicalHistory(subjectiveDto.getMedicalHistory(), subjectiveDto.getBasic());
        FunctionalAssessment functionalAssessment = mapFunctionalAssessment(
                subjectiveDto.getPriorFunction(),
                subjectiveDto.getCurrentFunction(),
                subjectiveDto.getBasic()
        );

        // Create domain object
        return SubjectiveFindings.builder()
                .examinationContext(examinationContext)
                .chiefComplaint(chiefComplaint)
                .injuryHistory(injuryHistory)
                .painAssessment(painAssessment)
                .medicalHistory(medicalHistory)
                .functionalAssessment(functionalAssessment)
                .build();
    }

    // ==================== ExaminationContext Mapping ====================

    private ExaminationContext mapExaminationContext(Basic basic) {
        if (basic == null) {
            return null;
        }

        LocalDate dateOfExamination = parseDate(basic.dateOfInitialExamination);
        LocalDate timeIn = parseDate(basic.timeIn);
        LocalDate timeOut = parseDate(basic.timeOut);
        VisitType visitType = parseVisitType(basic.visit);
        List<TreatmentSide> treatmentSide = parseTreatmentSide(basic.treatmentSide);
        SpecialtyProfile specialtyProfile = parseSpecialtyProfile(basic.pelvicProfileSpeechProfile);
        String physicianOrders = getFirstNonEmpty(
                basic.specificPhysicianOrdersText,
                basic.specificPhysicianOrders
        );

        return ExaminationContext.builder()
                .dateOfExamination(dateOfExamination)
                .timeIn(timeIn)
                .timeOut(timeOut)
                .visitType(visitType)
                .treatmentSide(treatmentSide)
                .physicianOrders(physicianOrders)
                .specialtyProfile(specialtyProfile)
                .build();
    }

    // ==================== ChiefComplaint Mapping ====================

    private ChiefComplaint mapChiefComplaint(Basic basic) {
        if (basic == null) {
            return null;
        }

        String primaryConcern = basic.primaryConcernChiefComplaint;

        // Extract present symptoms from various fields
        List<String> presentSymptoms = extractSymptoms(
                basic.presentSymptoms,
                basic.presentSymptomsSelect
        );

        // Determine symptom pattern from multiple boolean/string fields
        SymptomPattern symptomPattern = deriveSymptomPattern(
                basic.constantSymptoms,
                basic.intermittentSymptoms
        );

        String functionalImpact = getFirstNonEmpty(
                basic.functionalDisabilityFromPresentEpisodeText,
                basic.functionalDisabilityFromPresentEpisode
        );

        // Accumulated complaints from history
        String accumulatedComplaints = basic.historyOfPresentConditionMechanismOfInjury;

        return ChiefComplaint.builder()
                .primaryConcern(primaryConcern)
                .presentSymptoms(presentSymptoms)
                .symptomPattern(symptomPattern)
                .functionalImpact(functionalImpact)
                .accumulatedComplaints(accumulatedComplaints)
                .build();
    }

    // ==================== InjuryHistory Mapping ====================

    private InjuryHistory mapInjuryHistory(Basic basic, com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory medicalHistoryDto) {
        if (basic == null) {
            return null;
        }

        LocalDate injuryOnsetDate = parseDate(basic.injuryOnsetDateChangeOfStatusDate);

        // Derive onset type from boolean flags
        List<OnsetType> onsetType = deriveOnsetType(basic.chronic, basic.insidious, basic.newInjury);

        String mechanismOfInjury = getFirstNonEmpty(
                basic.newInjuryText,
                basic.commencedAsResultofText,
                basic.historyOfPresentConditionMechanismOfInjury
        );

        List<String> symptomsAtOnset = extractSymptoms(
                null,
                basic.symptomsAtOnsetSelect
        );

        // Check if there's prior episode history
        boolean isPriorEpisode = medicalHistoryDto != null
                && medicalHistoryDto.previousHistoryOfSimilarSymptomsMedicalHistory != null;

        PriorEpisodeHistory priorEpisodeHistory = mapPriorEpisodeHistory(medicalHistoryDto);
        SurgeryHistory surgeryHistory = mapSurgeryHistory(basic);

        return InjuryHistory.builder()
                .injuryOnsetDate(injuryOnsetDate)
                .onsetType(onsetType)
                .mechanismOfInjury(mechanismOfInjury)
                .symptomsAtOnset(symptomsAtOnset)
                .isPriorEpisode(isPriorEpisode)
                .priorEpisodeHistory(priorEpisodeHistory)
                .surgeryHistory(surgeryHistory)
                .build();
    }

    private PriorEpisodeHistory mapPriorEpisodeHistory(com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory medicalHistoryDto) {
        if (medicalHistoryDto == null) {
            return null;
        }

        // Extract from medical history DTO
        Integer numberOfEpisodes = parseInteger(
                medicalHistoryDto.previousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory
        );

        Integer yearOfFirstEpisode = parseInteger(
                medicalHistoryDto.yearOfFirstEpisodePreviousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory
        );

        String ageRange = String.valueOf(
                medicalHistoryDto.ageRangePreviousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory
        );

        List<String> previousTreatments = extractList(
                medicalHistoryDto.previousTreatmentsForSimilarSymptomsPreviousHistoryOfSimilarSymptomsMedicalHistory
        );

        return PriorEpisodeHistory.builder()
                .numberOfEpisodes(numberOfEpisodes != null ? numberOfEpisodes : 0)
                .yearOfFirstEpisode(yearOfFirstEpisode != null ? yearOfFirstEpisode : 0)
                .ageRangeAtFirstEpisode(ageRange)
                .previousTreatments(previousTreatments)
                .build();
    }

    private SurgeryHistory mapSurgeryHistory(Basic basic) {
        if (basic == null) {
            return null;
        }

        boolean hasSurgery = parseBoolean(basic.surgeryPerformed);
        LocalDate dateOfSurgery = parseDate(basic.dateOfSurgery);
        String typeOfSurgery = basic.typeOfSurgery;
        boolean hadHospitalization = parseBoolean(basic.priorHospitalization);

        return SurgeryHistory.builder()
                .hasSurgery(hasSurgery)
                .dateOfSurgery(dateOfSurgery)
                .typeOfSurgery(typeOfSurgery)
                .hadHospitalization(hadHospitalization)
                .build();
    }

    // ==================== PainAssessment Mapping ====================

    private PainAssessment mapPainAssessment(Pain painDto) {
        if (painDto == null) {
            return null;
        }

        Integer painScale = parseInteger(painDto.pain_scale);

        List<AggravatingFactor> aggravatingFactors = extractAggravatingFactors(
                painDto.worseWithSelect,
                painDto.worseWithText,
                painDto.aggravatingFactors
        );

        List<AlleviatingFactor> alleviatingFactors = extractAlleviatingFactors(
                painDto.betterwithSelect,
                painDto.betterWithText
        );

        String painPattern = getFirstNonEmpty(
                painDto.restrictionsAndPainAlleviatorsTextArea,
                painDto.restrictionsAndPainAlleviators
        );

        String restrictions = painDto.restrictionsAndPainAlleviators;

        return PainAssessment.builder()
                .painScale(painScale != null ? painScale : 0)
                .aggravatingFactors(aggravatingFactors)
                .alleviatingFactors(alleviatingFactors)
                .painPattern(painPattern)
                .restrictions(restrictions)
                .build();
    }

    private List<AggravatingFactor> extractAggravatingFactors(
            List<String> activities,
            String description,
            List<String> aggravatingFactors) {

        List<AggravatingFactor> factors = new ArrayList<>();

        if (activities != null) {
            for (String activity : activities) {
                if (activity != null && !activity.trim().isEmpty()) {

                    factors.add(AggravatingFactor.builder()
                            .description(description)
                            .activity(activity)
                            .build());
                }
            }
        }

        if (factors.isEmpty() && aggravatingFactors != null) {
            for (String factor : aggravatingFactors) {
                if (factor != null && !factor.trim().isEmpty()) {
                    factors.add(AggravatingFactor.builder()
                            .description("")
                            .activity(factor)
                            .build());
                }
            }
        }

        return factors;
    }

    private List<AlleviatingFactor> extractAlleviatingFactors(
            List<String> interventions,
            String description) {

        List<AlleviatingFactor> factors = new ArrayList<>();

        if (interventions != null) {
            for (String intervention : interventions) {
                if (intervention != null && !intervention.trim().isEmpty()) {

                    factors.add(AlleviatingFactor.builder()
                            .intervention(intervention)
                            .description(description)
                            .build());
                }
            }
        }

        return factors;
    }

    // ==================== MedicalHistory Mapping ====================

    private MedicalHistory mapMedicalHistory(com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory medicalHistoryDto, Basic basic) {
        if (medicalHistoryDto == null) {
            return null;
        }

        // This is a complex mapping - will implement in phases
        // For now, return a minimal implementation
        GeneralHealth generalHealth = mapGeneralHealth(medicalHistoryDto);
        List<String> currentMedications = mapCurrentMedications(medicalHistoryDto.currentMedicationsMedicalHistory);
        List<String> relevantConditions = mapRelevantConditions(medicalHistoryDto.medicalHistoryMedicalHistory);
        DiagnosticHistory diagnosticHistory = mapDiagnosticHistory(medicalHistoryDto, basic);
        SocialHistory socialHistory = mapSocialHistory(medicalHistoryDto);
        OccupationalHistory occupationalHistory = mapOccupationalHistory(medicalHistoryDto);
        FallRiskAssessment fallRisk = mapFallRisk(medicalHistoryDto);
        CognitiveStatus cognitiveStatus = mapCognitiveStatus(medicalHistoryDto);
        PelvicFloorHistory pelvicFloorHistory = mapPelvicFloorHistory(medicalHistoryDto);

        return MedicalHistory.builder()
                .generalHealth(generalHealth)
                .currentMedications(currentMedications)
                .relevantMedicalConditions(relevantConditions)
                .diagnosticHistory(diagnosticHistory)
                .socialHistory(socialHistory)
                .occupationalHistory(occupationalHistory)
                .fallRiskAssessment(fallRisk)
                .cognitiveStatus(cognitiveStatus)
                .pelvicFloorHistory(pelvicFloorHistory)
                .build();
    }

    // Placeholder methods for MedicalHistory sub-components
    // TODO: Implement these based on domain model requirements

    private GeneralHealth mapGeneralHealth(com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        boolean unexplainedWeightLoss = parseBoolean(dto.unexplainedWeightLossMedicalHistory);
        String generalHealthStatus = String.valueOf(dto.generalHealthMedicalHistory);
        return GeneralHealth.builder()
                .unexplainedWeightLoss(unexplainedWeightLoss)
                .generalHealthStatus(generalHealthStatus)
                .build();

    }

    private List<String> mapCurrentMedications(CurrentMedications dto) {
        if (dto == null) {
            return Collections.emptyList();
        }

        List<String> medications = new ArrayList<>();
        addIfPresent(medications, dto.current_medications_Prescription_txt);
        addIfPresent(medications, dto.current_medications_OverTheCounter_txt);
        addIfPresent(medications, dto.current_medications_Herbals_txt);
        addIfPresent(medications, dto.current_medications_VitaminMineralDietarySupplements_txt);
        addIfPresent(medications, dto.current_medications_Other_txt);

        return medications;
    }

    private List<String> mapRelevantConditions(MedicalHistorySub dto) {
        if (dto == null) {
            return Collections.emptyList();
        }

        List<String> conditions = new ArrayList<>();

        if (dto.medical_history_Alzheimers)
            addIfPresent(conditions, "Alzheimer's: " + dto.medical_history_Alzheimers_txt);
        if (dto.medical_history_CardiovascularDisease)
            addIfPresent(conditions, "Cardiovascular Disease: " + dto.medical_history_CardiovascularDisease_txt);
        if (dto.medical_history_DiabetesMellitusType1)
            addIfPresent(conditions, "Diabetes Type 1: " + dto.medical_history_DiabetesMellitusType1_txt);
        if (dto.medical_history_DiabetesMellitusType2)
            addIfPresent(conditions, "Diabetes Type 2: " + dto.medical_history_DiabetesMellitusType2_txt);
        // Add more conditions as needed

        return conditions;
    }

    private DiagnosticHistory mapDiagnosticHistory(com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto, Basic basic) {
        // Extract ICD codes and diagnosis from Basic (passed through medical history)
        List<IcdCode> icdCodes = mapIcdCodes(basic.getIcdtenCodeDiagnosis());
        List<IcdCode> treatmentDiagnosisCodes = mapIcdCodes(basic.getIcdtenCodeTreatmentDiagnosis());
        List<String> imagingResults = Collections.emptyList(); // TODO: Extract from dto
        return DiagnosticHistory.builder()
                .imagingResults(imagingResults)
                .treatmentDiagnosisCodes(treatmentDiagnosisCodes)
                .icdCodes(icdCodes)
                .build();
    }

    private List<IcdCode> mapIcdCodes(Object icdCodesObj) {
        if (icdCodesObj == null) {
            return Collections.emptyList();
        }

        // Handle if it's already a List
        if (icdCodesObj instanceof List) {
            List<?> list = (List<?>) icdCodesObj;
            return list.stream()
                    .map(this::mapSingleIcdCode)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(IcdCode::getOrder))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private IcdCode mapSingleIcdCode(Object obj) {
        if (obj == null) {
            return null;
        }

        // If it's a Map (from JSON deserialization)
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            String code = String.valueOf(map.get("code"));
            String description = String.valueOf(map.get("description"));
            Integer order = parseInteger(map.get("order"));

            return new IcdCode(code, description, order != null ? order : 0);
        }

        return null;
    }

    private SocialHistory mapSocialHistory
            (com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        // TODO: Implement comprehensive social history mapping
        return SocialHistory.builder().build();
    }

    private OccupationalHistory mapOccupationalHistory
            (com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        // TODO: Implement occupational history mapping
        return OccupationalHistory.builder().build();
    }

    private FallRiskAssessment mapFallRisk
            (com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        boolean hasFallHistory = parseBoolean(dto.historyOfFallsMedicalHistory);
        Integer numberOfFalls = 0; // TODO: Extract from dto
        String documentation = String.valueOf(dto.fallsDocumentedDoYouHaveDocumentationThatSupportsThePatientFallHistoryHistoryOfFallsMedicalHistory);
        return FallRiskAssessment.builder()
                .hasFallHistory(hasFallHistory)
                .fallDocumentation(documentation)
                .numberOfFallsInLastYear(numberOfFalls)
                .build();

    }

    private CognitiveStatus mapCognitiveStatus
            (com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        boolean suspected = parseBoolean(dto.mentalStatusCognitiveFunctionAppearsImpairedMedicalHistory);
        String observations = String.valueOf(dto.mentalStatusCognitiveFunctionAppearsImpairedTxtMentalStatusCognitiveFunctionAppearsImpairedMedicalHistory);
        return CognitiveStatus.builder()
                .observations(observations)
                .cognitiveImpairmentSuspected(suspected)
                .build();

    }

    private PelvicFloorHistory mapPelvicFloorHistory
            (com.cob.emr.model.medical.note.types.initial.evaluation.subjective.MedicalHistory dto) {
        // TODO: Implement pelvic floor history mapping
        return PelvicFloorHistory.builder().build();
    }

    // ==================== FunctionalAssessment Mapping ====================

    private FunctionalAssessment mapFunctionalAssessment(
            PriorFunction priorDto,
            CurrentFunction currentDto,
            Basic basic) {

        FunctionalStatus priorLevel = mapFunctionalLevel(priorDto);
        FunctionalStatus currentLevel = mapFunctionalLevel(currentDto);

        String disabilityScore = basic != null ? basic.functionalDisabilityScore : "";

        return FunctionalAssessment.builder()
                .priorFunctionalLevel(priorLevel)
                .currentFunctionalLevel(currentLevel)
                .functionalDisabilityScore(disabilityScore)
                .build();
    }

    private FunctionalStatus mapFunctionalLevel(Object functionalDto) {
        // TODO: Implement based on PriorFunction/CurrentFunction structure
        return FunctionalStatus.builder().build();
    }

    // ==================== Utility Methods ====================

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalTime.parse(timeStr, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    private VisitType parseVisitType(String visit) {
        if (visit == null || visit.trim().isEmpty()) {
            return VisitType.INITIAL;
        }
        try {
            return VisitType.valueOf(visit.toUpperCase());
        } catch (IllegalArgumentException e) {
            return VisitType.INITIAL;
        }
    }


    private SpecialtyProfile parseSpecialtyProfile(String profile) {
        if (profile.equals("POS01"))
            return SpecialtyProfile.SPEECH_THERAPY;
        if (profile.equals("POS02"))
            return SpecialtyProfile.PELVIC_FLOOR;
        return SpecialtyProfile.NONE;
    }

    private List<TreatmentSide> parseTreatmentSide(Object treatmentSide) {
        List<TreatmentSide> result = new ArrayList<>();

        if (treatmentSide == null) {
            return result; // return empty list instead of null
        }

        if (treatmentSide instanceof Map) {
            Map<String, Object> sideMap = (Map<String, Object>) treatmentSide;

            if (Boolean.TRUE.equals(sideMap.get("left"))) {
                result.add(TreatmentSide.LEFT);
            }
            if (Boolean.TRUE.equals(sideMap.get("right"))) {
                result.add(TreatmentSide.RIGHT);
            }
            if (Boolean.TRUE.equals(sideMap.get("na"))) {
                result.add(TreatmentSide.NOT_APPLICABLE);
            }
        }

        return result;
    }


    private List<OnsetType> deriveOnsetType(Boolean chronic, Boolean insidious, Boolean newInjury) {
        List<OnsetType> result = new ArrayList<>();

        if (Boolean.TRUE.equals(newInjury)) {
            result.add(OnsetType.NEW_INJURY);
        }
        if (Boolean.TRUE.equals(chronic)) {
            result.add(OnsetType.CHRONIC);
        }
        if (Boolean.TRUE.equals(insidious)) {
            result.add(OnsetType.INSIDIOUS);
        }

        return result;
    }

    private SymptomPattern deriveSymptomPattern(String constant, String intermittent) {
        if (constant != null && !constant.trim().isEmpty()) {
            return SymptomPattern.CONSTANT;
        }
        if (intermittent != null && !intermittent.trim().isEmpty()) {
            return SymptomPattern.INTERMITTENT;
        }
        return SymptomPattern.CONSTANT; // Default
    }

    private List<String> extractSymptoms(String symptomsStr, Object symptomsList) {
        List<String> symptoms = new ArrayList<>();

        // Try to extract from list first
        if (symptomsList instanceof List) {
            List<?> list = (List<?>) symptomsList;
            for (Object item : list) {
                if (item != null) {
                    String symptom = item.toString().trim();
                    if (!symptom.isEmpty()) {
                        symptoms.add(symptom);
                    }
                }
            }
        }

        // If no list, try string
        if (symptoms.isEmpty() && symptomsStr != null && !symptomsStr.trim().isEmpty()) {
            symptoms.add(symptomsStr);
        }

        return symptoms;
    }

    private List<String> extractList(Object obj) {
        if (obj instanceof List) {
            return ((List<?>) obj).stream()
                    .filter(item -> item != null && !item.toString().trim().isEmpty())
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        if (obj != null && !obj.toString().trim().isEmpty()) {
            return List.of(obj.toString());
        }
        return Collections.emptyList();
    }

    private Integer parseInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        String str = obj.toString().toLowerCase();
        return "true".equals(str) || "yes".equals(str) || "1".equals(str);
    }

    private String getFirstNonEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }
        return "";
    }

    private void addIfPresent(List<String> list, String value) {
        if (value != null && !value.trim().isEmpty()) {
            list.add(value);
        }
    }
}
