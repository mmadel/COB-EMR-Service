# EMR Domain Model Generator Part 2 - Complex Classes
# Generates Subjective, Assessment, Plan, Billing, Objective, and InitialEvaluation
# Usage: .\Generate-DomainModel-Part2.ps1
# Run AFTER Generate-DomainModel.ps1

$ErrorActionPreference = "Stop"

$BASE_DIR = "src\main\java"
$DOMAIN_BASE = "com\cob\emr\ddd\domain\medicalnote"



# Helper function to create Java files
function Create-JavaFile {
    param (
        [string]$Package,
        [string]$FileName,
        [string]$Content
    )
    
    $filePath = Join-Path $BASE_DIR (Join-Path $Package $FileName)
    Set-Content -Path $filePath -Value $Content -Encoding UTF8

}



# SubjectiveFindings.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SubjectiveFindings.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.*;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class SubjectiveFindings {
    
    private final ExaminationContext examinationContext;
    private final ChiefComplaint chiefComplaint;
    private final InjuryHistory injuryHistory;
    private final PainAssessment painAssessment;
    private final MedicalHistory medicalHistory;
    private final FunctionalAssessment functionalAssessment;
    
    public void validate() {
        if (examinationContext == null) {
            throw new IllegalStateException("Examination context is required");
        }
        examinationContext.validate();
        
        if (painAssessment != null) {
            painAssessment.validate();
        }
    }
    
    public SubjectiveFindings excludeHistoryOfPresentCondition() {
        InjuryHistory modifiedHistory = injuryHistory != null
            ? injuryHistory.toBuilder().mechanismOfInjury(null).build()
            : null;
            
        return this.toBuilder()
            .injuryHistory(modifiedHistory)
            .build();
    }
}
'@

# ExaminationContext.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "ExaminationContext.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.VisitType;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.TreatmentSide;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder(toBuilder = true)
public class ExaminationContext {
    private final LocalDate dateOfExamination;
    private final LocalTime timeIn;
    private final LocalTime timeOut;
    private final VisitType visitType;
    private final TreatmentSide treatmentSide;
    private final String physicianOrders;
    
    public void validate() {
        if (dateOfExamination == null) {
            throw new IllegalStateException("Date of examination is required");
        }
    }
    
    public int getTotalMinutes() {
        if (timeIn != null && timeOut != null) {
            return (int) java.time.Duration.between(timeIn, timeOut).toMinutes();
        }
        return 0;
    }
}
'@

# ChiefComplaint.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "ChiefComplaint.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.SymptomPattern;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ChiefComplaint {
    private final String primaryConcern;
    private final List<String> presentSymptoms;
    private final SymptomPattern symptomPattern;
    private final String functionalImpact;
    private final String accumulatedComplaints;
    
    public boolean hasComplaint() {
        return primaryConcern != null && !primaryConcern.trim().isEmpty();
    }
}
'@

# InjuryHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "InjuryHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.OnsetType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class InjuryHistory {
    private final LocalDate injuryOnsetDate;
    private final OnsetType onsetType;
    private final String mechanismOfInjury;
    private final List<String> symptomsAtOnset;
    private final boolean isPriorEpisode;
    private final PriorEpisodeHistory priorEpisodeHistory;
    private final SurgeryHistory surgeryHistory;
    
    public void validate() {
        if (onsetType == OnsetType.ACUTE_TRAUMATIC && injuryOnsetDate == null) {
            throw new IllegalStateException("Acute injury requires onset date");
        }
    }
}
'@

# PriorEpisodeHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "PriorEpisodeHistory.java" @'
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
'@

# SurgeryHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SurgeryHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class SurgeryHistory {
    private final boolean hasSurgery;
    private final LocalDate dateOfSurgery;
    private final String typeOfSurgery;
    private final boolean hadHospitalization;
}
'@

# PainAssessment.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "PainAssessment.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class PainAssessment {
    private final Integer painScale;
    private final List<AggravatingFactor> aggravatingFactors;
    private final List<AlleviatingFactor> alleviatingFactors;
    private final String painPattern;
    private final String restrictions;
    
    public void validate() {
        if (painScale != null && (painScale < 0 || painScale > 10)) {
            throw new IllegalArgumentException("Pain scale must be between 0 and 10");
        }
    }
    
    public boolean isSeverePain() {
        return painScale != null && painScale >= 7;
    }
    
    public boolean isMildPain() {
        return painScale != null && painScale <= 3;
    }
}
'@

# AggravatingFactor.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "AggravatingFactor.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AggravatingFactor {
    private final String activity;
    private final String description;
}
'@

# AlleviatingFactor.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "AlleviatingFactor.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlleviatingFactor {
    private final String intervention;
    private final String description;
}
'@

# MedicalHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "MedicalHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class MedicalHistory {
    private final GeneralHealth generalHealth;
    private final List<String> currentMedications;
    private final List<String> relevantMedicalConditions;
    private final DiagnosticHistory diagnosticHistory;
    private final SocialHistory socialHistory;
    private final OccupationalHistory occupationalHistory;
    private final FallRiskAssessment fallRiskAssessment;
    private final CognitiveStatus cognitiveStatus;
    private final PelvicFloorHistory pelvicFloorHistory;
}
'@

# GeneralHealth.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "GeneralHealth.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneralHealth {
    private final boolean unexplainedWeightLoss;
    private final String generalHealthStatus;
}
'@

# DiagnosticHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "DiagnosticHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DiagnosticHistory {
    private final String icdTenCode;
    private final String treatmentDiagnosis;
    private final List<String> imagingResults;
}
'@

# SocialHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SocialHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.TobaccoUseStatus;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class SocialHistory {
    private final TobaccoUseStatus tobaccoUse;
    private final TobaccoUsageDetails tobaccoDetails;
    private final HomeEnvironment homeEnvironment;
    private final List<String> durableMedicalEquipment;
}
'@

# TobaccoUsageDetails.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "TobaccoUsageDetails.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TobaccoUsageDetails {
    private final boolean currentlyUses;
    private final String usageDescription;
    private final boolean recommendationMadeToStop;
    private final boolean adviceProvided;
    private final boolean continuedSupportPlanned;
}
'@

# HomeEnvironment.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "HomeEnvironment.java" @'
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
'@

# OccupationalHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "OccupationalHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.WorkStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.DutyLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class OccupationalHistory {
    private final String occupation;
    private final WorkStatus workStatus;
    private final LocalDate outOfWorkSince;
    private final LocalDate expectedReturnToWorkDate;
    private final DutyLevel physicalDemandLevel;
    private final boolean isWorkersCompensation;
    private final boolean hasLitigation;
    
    public void validate() {
        if (workStatus == WorkStatus.OFF_WORK && outOfWorkSince == null) {
            throw new IllegalStateException("Off work status requires date");
        }
    }
    
    public boolean isEligibleForWorkersComp() {
        if (workStatus != WorkStatus.OFF_WORK || outOfWorkSince == null) {
            return false;
        }
        long daysOffWork = java.time.temporal.ChronoUnit.DAYS
            .between(outOfWorkSince, LocalDate.now());
        return daysOffWork <= 365 && isWorkersCompensation;
    }
}
'@

# FallRiskAssessment.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "FallRiskAssessment.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallRiskAssessment {
    private final boolean hasFallHistory;
    private final Integer numberOfFallsInLastYear;
    private final String fallDocumentation;
    
    public boolean isHighRisk() {
        return hasFallHistory && numberOfFallsInLastYear != null && numberOfFallsInLastYear >= 2;
    }
}
'@

# CognitiveStatus.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "CognitiveStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CognitiveStatus {
    private final boolean cognitiveImpairmentSuspected;
    private final String observations;
}
'@

# PelvicFloorHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "PelvicFloorHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PelvicFloorHistory {
    private final MenstrualHistory menstrualHistory;
    private final String birthControlMethod;
    private final PregnancyHistory pregnancyHistory;
    private final BladderFunction bladderFunction;
    private final SexualHistory sexualHistory;
    private final boolean hasAbuseHistory;
}
'@

# MenstrualHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "MenstrualHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenstrualHistory {
    private final boolean isRegular;
    private final String details;
}
'@

# PregnancyHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "PregnancyHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PregnancyHistory {
    private final int numberOfPregnancies;
    private final int numberOfLiveBirths;
    private final String complications;
}
'@

# BladderFunction.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "BladderFunction.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BladderFunction {
    private final String concerns;
    private final String pattern;
}
'@

# SexualHistory.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SexualHistory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SexualHistory {
    private final boolean sexuallyActive;
    private final String concerns;
}
'@

# FunctionalAssessment.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "FunctionalAssessment.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class FunctionalAssessment {
    private final FunctionalStatus priorFunctionalLevel;
    private final FunctionalStatus currentFunctionalLevel;
    private final String functionalDisabilityScore;
    
    public int calculateDisabilityScore() {
        if (currentFunctionalLevel == null) return 0;
        return currentFunctionalLevel.calculateDisabilityPoints();
    }
}
'@

# FunctionalStatus.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "FunctionalStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FunctionalStatus {
    private final SelfCareActivities selfCare;
    private final MobilityActivities mobility;
    private final InstrumentalActivities instrumentalActivities;
    private final SleepActivities sleep;
    
    public int calculateDisabilityPoints() {
        int points = 0;
        if (selfCare != null) points += selfCare.getDisabilityPoints();
        if (mobility != null) points += mobility.getDisabilityPoints();
        if (instrumentalActivities != null) points += instrumentalActivities.getDisabilityPoints();
        return points;
    }
}
'@

# SelfCareActivities.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SelfCareActivities.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelfCareActivities {
    private final ActivityLevel hygiene;
    private final ActivityLevel grooming;
    private final ActivityLevel bathing;
    private final ActivityLevel dressing;
    private final ActivityLevel toileting;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (hygiene != null) points += hygiene.getPoints();
        if (grooming != null) points += grooming.getPoints();
        if (bathing != null) points += bathing.getPoints();
        if (dressing != null) points += dressing.getPoints();
        if (toileting != null) points += toileting.getPoints();
        return points;
    }
}
'@

# MobilityActivities.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "MobilityActivities.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MobilityActivities {
    private final ActivityLevel walking;
    private final ActivityLevel transfers;
    private final ActivityLevel positionChanges;
    private final ActivityLevel negotiatingObstacles;
    private final String assistiveDeviceUsed;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (walking != null) points += walking.getPoints() * 2;
        if (transfers != null) points += transfers.getPoints();
        if (positionChanges != null) points += positionChanges.getPoints();
        if (negotiatingObstacles != null) points += negotiatingObstacles.getPoints();
        return points;
    }
}
'@

# InstrumentalActivities.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "InstrumentalActivities.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstrumentalActivities {
    private final ActivityLevel housekeeping;
    private final ActivityLevel shopping;
    private final ActivityLevel foodPreparation;
    private final ActivityLevel transportation;
    private final ActivityLevel financialManagement;
    
    public int getDisabilityPoints() {
        int points = 0;
        if (housekeeping != null) points += housekeeping.getPoints();
        if (shopping != null) points += shopping.getPoints();
        if (foodPreparation != null) points += foodPreparation.getPoints();
        if (transportation != null) points += transportation.getPoints();
        return points;
    }
}
'@

# SleepActivities.java
Create-JavaFile "$DOMAIN_BASE\model\subjective" "SleepActivities.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ActivityLevel;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class SleepActivities {
    private final ActivityLevel sleepQuality;
    private final List<String> sleepingPositions;
    private final String surfaceType;
    private final boolean hasDisturbedSleep;
}
'@



# ClinicalAssessment.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "ClinicalAssessment.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.*;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class ClinicalAssessment {
    
    private final PatientPresentation patientPresentation;
    private final List<Problem> problemList;
    private final List<FunctionalGoal> goals;
    private final Diagnosis diagnosis;
    private final RehabilitationPotential rehabilitationPotential;
    private final PatientCompliance patientCompliance;
    private final Contraindications contraindications;
    private final ConsentToCare consentToCare;
    private final String patientEducation;
    private final String reasonForAssessment;
    
    public void validate() {
        if (goals == null || goals.isEmpty()) {
            throw new IllegalStateException("At least one goal is required");
        }
        
        if (rehabilitationPotential == null) {
            throw new IllegalStateException("Rehabilitation potential is required");
        }
        
        goals.forEach(FunctionalGoal::validate);
    }
    
    public boolean hasGoals() {
        return goals != null && !goals.isEmpty();
    }
    
    public ClinicalAssessment clearDiagnosisForFollowUp() {
        return this.toBuilder()
            .diagnosis(null)
            .build();
    }
    
    public double calculateOverallProgress() {
        if (goals == null || goals.isEmpty()) return 0.0;
        
        return goals.stream()
            .mapToInt(goal -> parseProgressPercentage(goal.getMet()))
            .average()
            .orElse(0.0);
    }
    
    public boolean isPatientProgressing() {
        return calculateOverallProgress() >= 50.0;
    }
    
    public boolean isReadyForDischarge() {
        long metGoals = goals.stream()
            .filter(goal -> parseProgressPercentage(goal.getMet()) >= 80)
            .count();
        return metGoals >= (goals.size() * 0.8);
    }
    
    public List<FunctionalGoal> getUnmetGoals() {
        if (goals == null) return List.of();
        return goals.stream()
            .filter(goal -> parseProgressPercentage(goal.getMet()) < 80)
            .collect(Collectors.toList());
    }
    
    private int parseProgressPercentage(String met) {
        if (met == null || met.trim().isEmpty()) return 0;
        try {
            String cleaned = met.replace("%", "").trim();
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
'@

# PatientPresentation.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "PatientPresentation.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ComplexityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientPresentation {
    private final ComplexityLevel complexityLevel;
    private final String clinicalReason;
    
    public void validate() {
        if (complexityLevel == null) {
            throw new IllegalStateException("Complexity level is required");
        }
    }
}
'@

# Problem.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "Problem.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ProblemStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
    private final String description;
    private final ProblemStatus status;
    
    public boolean isActive() {
        return status == ProblemStatus.ACTIVE;
    }
}
'@

# FunctionalGoal.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "FunctionalGoal.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.GoalType;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.TimePeriod;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class FunctionalGoal {
    private final String term;
    private final String period;
    private final String customPeriod;
    private final String description;
    private final String met;
    private final String customMet;
    
    public void validate() {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalStateException("Goal description is required");
        }
    }
    
    public GoalType getGoalType() {
        if ("Short Term".equalsIgnoreCase(term)) {
            return GoalType.SHORT_TERM;
        } else if ("Long Term".equalsIgnoreCase(term)) {
            return GoalType.LONG_TERM;
        }
        return GoalType.SHORT_TERM;
    }
    
    public TimePeriod getTimePeriod() {
        return TimePeriod.fromString(period);
    }
    
    public int getProgressPercentage() {
        if (met == null || met.trim().isEmpty()) return 0;
        try {
            String cleaned = met.replace("%", "").trim();
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public boolean isMet() {
        return getProgressPercentage() >= 80;
    }
    
    public FunctionalGoal updateProgress(String newProgress) {
        return this.toBuilder()
            .met(newProgress)
            .build();
    }
}
'@

# Diagnosis.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "Diagnosis.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class Diagnosis {
    private final String assessmentDiagnosis;
    private final List<String> icdCodes;
}
'@

# RehabilitationPotential.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "RehabilitationPotential.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.RehabPotentialRating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RehabilitationPotential {
    private final RehabPotentialRating rating;
    private final String justification;
    
    public void validate() {
        if (rating == null) {
            throw new IllegalStateException("Rehabilitation potential rating is required");
        }
    }
}
'@

# PatientCompliance.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "PatientCompliance.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientCompliance {
    private final boolean isCompliant;
    private final ComplianceWithPrecautions complianceWithRestPrecautions;
    private final ComplianceWithPrecautions complianceWithActivityModifications;
}
'@

# ComplianceWithPrecautions.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "ComplianceWithPrecautions.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplianceWithPrecautions {
    private final boolean isCompliant;
    private final String details;
}
'@

# Contraindications.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "Contraindications.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Contraindications {
    private final boolean hasContraindications;
    private final String details;
    
    public boolean hasContraindication(String keyword) {
        return hasContraindications && 
               details != null && 
               details.toLowerCase().contains(keyword.toLowerCase());
    }
}
'@

# ConsentToCare.java
Create-JavaFile "$DOMAIN_BASE\model\assessment" "ConsentToCare.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsentToCare {
    private final boolean agreesToTreatmentPlan;
    private final String consentDetails;
    
    public void validate() {
        if (!agreesToTreatmentPlan) {
            throw new IllegalStateException("Patient must consent to treatment");
        }
    }
}
'@



# PlanOfCare.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "PlanOfCare.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.*;
import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class PlanOfCare {
    
    private final TreatmentPlan treatmentPlan;
    private final List<ModalityPrescription> modalities;
    private final List<TherapeuticProcedure> procedures;
    private final List<SpecialtyService> specialties;
    private final String instructions;
    private final String instructionText;
    private final boolean requiresPhysicianSignature;
    private final boolean createPlanOfCare;
    
    public void validate() {
        if (treatmentPlan == null) {
            throw new IllegalStateException("Treatment plan is required");
        }
        treatmentPlan.validate();
    }
    
    public PlanOfCare simplifyForDailyNote(String dailyInstructions) {
        return PlanOfCare.builder()
            .instructions(dailyInstructions)
            .build();
    }
    
    public LocalDate calculateExpectedDischarge(LocalDate startDate) {
        if (treatmentPlan == null || treatmentPlan.getDuration() == null) {
            return null;
        }
        return startDate.plusWeeks(treatmentPlan.getDuration().getWeeks());
    }
    
    public int calculateTotalExpectedVisits() {
        if (treatmentPlan == null) return 0;
        
        Integer weeks = treatmentPlan.getDuration() != null 
            ? treatmentPlan.getDuration().getWeeks() 
            : 0;
        Integer visitsPerWeek = treatmentPlan.getFrequency() != null 
            ? treatmentPlan.getFrequency().getTimesPerWeek() 
            : 0;
            
        return weeks * visitsPerWeek;
    }
    
    public List<String> checkContraindications(ClinicalAssessment assessment) {
        List<String> warnings = new ArrayList<>();
        
        if (modalities == null || assessment.getContraindications() == null) {
            return warnings;
        }
        
        for (ModalityPrescription modality : modalities) {
            if (modality.getModalityType() == ModalityType.ELECTRICAL_STIMULATION) {
                if (assessment.getContraindications().hasContraindication("pacemaker")) {
                    warnings.add("Electrical stimulation contraindicated with pacemaker");
                }
            }
        }
        
        return warnings;
    }
}
'@

# TreatmentPlan.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "TreatmentPlan.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class TreatmentPlan {
    private final String planCode;
    private final DurationCode duration;
    private final FrequencyCode frequency;
    private final LocalDate dischargeDate;
    private final String reason;
    
    public void validate() {
        if (duration == null) {
            throw new IllegalStateException("Treatment duration is required");
        }
        if (frequency == null) {
            throw new IllegalStateException("Treatment frequency is required");
        }
    }
}
'@

# DurationCode.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "DurationCode.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DurationCode {
    private final String code;
    private final Integer weeks;
    
    public static DurationCode fromCode(String code) {
        if (code == null || !code.startsWith("D")) {
            return null;
        }
        try {
            int weeks = Integer.parseInt(code.substring(1));
            return new DurationCode(code, weeks);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
'@

# FrequencyCode.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "FrequencyCode.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FrequencyCode {
    private final String code;
    private final Integer timesPerWeek;
    
    public static FrequencyCode fromCode(String code) {
        if (code == null || !code.startsWith("F")) {
            return null;
        }
        try {
            int times = Integer.parseInt(code.substring(1));
            return new FrequencyCode(code, times);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
'@

# ModalityPrescription.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "ModalityPrescription.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ModalityType;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ModalityPurpose;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ModalityPrescription {
    private final ModalityType modalityType;
    private final String modalitySubtype;
    private final List<ModalityPurpose> purposes;
    private final String duration;
    private final String additionalNotes;
}
'@

# TherapeuticProcedure.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "TherapeuticProcedure.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ProcedureCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TherapeuticProcedure {
    private final ProcedureCategory procedureCategory;
    private final String procedureType;
    private final String details;
}
'@

# SpecialtyService.java
Create-JavaFile "$DOMAIN_BASE\model\planofcare" "SpecialtyService.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.SpecialtyServiceType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecialtyService {
    private final SpecialtyServiceType serviceType;
    private final String details;
}
'@



# BillingInformation.java
Create-JavaFile "$DOMAIN_BASE\model\billing" "BillingInformation.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.billing;

import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.objective.PerformedIntervention;
import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class BillingInformation {
    
    private final List<TimedCode> timedCodes;
    private final List<UntimedCode> untimedCodes;
    private final List<String> supplies;
    private final List<String> casts;
    private final List<String> braces;
    private final List<String> splintsOrthotics;
    private final String respiratory;
    private final String nerveConduction;
    private final boolean dailyNoteIncluded;
    private final String calendarMonth;
    private final String preTreatment;
    private final String postTreatment;
    private final String objectiveFindings;
    private final String instructionsTxt;
    private final String otherTreatmentProcedures;
    
    public void validate() {
        if ((timedCodes == null || timedCodes.isEmpty()) && 
            (untimedCodes == null || untimedCodes.isEmpty())) {
            throw new IllegalStateException("At least one billing code is required");
        }
        
        if (!validate8MinuteRule()) {
            throw new IllegalStateException("Billing does not comply with 8-minute rule");
        }
    }
    
    public boolean hasEvaluationCode() {
        if (untimedCodes == null) return false;
        return untimedCodes.stream()
            .anyMatch(UntimedCode::isEvaluationCode);
    }
    
    public BillingInformation excludeEvaluationCodes() {
        if (untimedCodes == null) return this;
        
        List<UntimedCode> filtered = untimedCodes.stream()
            .filter(code -> !code.isEvaluationCode())
            .collect(Collectors.toList());
            
        return this.toBuilder()
            .untimedCodes(filtered)
            .build();
    }
    
    public int calculateTotalUnits() {
        if (timedCodes == null) return 0;
        return timedCodes.stream()
            .mapToInt(TimedCode::getUnits)
            .sum();
    }
    
    public boolean validate8MinuteRule() {
        if (timedCodes == null || timedCodes.isEmpty()) return true;
        
        int totalMinutes = timedCodes.stream()
            .mapToInt(TimedCode::getDurationMinutes)
            .sum();
            
        int totalUnits = calculateTotalUnits();
        
        if (totalMinutes < 8) return totalUnits == 0;
        
        int expectedMinUnits = (totalMinutes - 8) / 15 + 1;
        int expectedMaxUnits = (totalMinutes + 7) / 15;
        
        return totalUnits >= expectedMinUnits && totalUnits <= expectedMaxUnits;
    }
    
    public ObjectiveFindings toObjectiveFindings() {
        List<PerformedIntervention> interventions = new ArrayList<>();
        
        if (timedCodes != null) {
            timedCodes.stream()
                .map(code -> PerformedIntervention.builder()
                    .cptCode(code.getCptCode())
                    .description(code.getDescription())
                    .durationMinutes(code.getDurationMinutes())
                    .units(code.getUnits())
                    .build())
                .forEach(interventions::add);
        }
        
        if (untimedCodes != null) {
            untimedCodes.stream()
                .filter(code -> !code.isEvaluationCode())
                .map(code -> PerformedIntervention.builder()
                    .cptCode(code.getCptCode())
                    .description(code.getDescription())
                    .build())
                .forEach(interventions::add);
        }
        
        return ObjectiveFindings.builder()
            .performedInterventions(interventions)
            .preTreatmentFindings(preTreatment)
            .postTreatmentFindings(postTreatment)
            .clinicalObservations(objectiveFindings)
            .instructions(instructionsTxt)
            .build();
    }
}
'@

# TimedCode.java
Create-JavaFile "$DOMAIN_BASE\model\billing" "TimedCode.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.billing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimedCode {
    private final String cptCode;
    private final String description;
    private final int durationMinutes;
    private final int units;
    
    public void validate() {
        if (cptCode == null || cptCode.trim().isEmpty()) {
            throw new IllegalStateException("CPT code is required");
        }
        if (durationMinutes <= 0) {
            throw new IllegalStateException("Duration must be positive");
        }
        if (units <= 0) {
            throw new IllegalStateException("Units must be positive");
        }
    }
    
    public static int calculateUnits(int durationMinutes) {
        if (durationMinutes < 8) return 0;
        return (durationMinutes / 15) + ((durationMinutes % 15 >= 8) ? 1 : 0);
    }
}
'@

# UntimedCode.java
Create-JavaFile "$DOMAIN_BASE\model\billing" "UntimedCode.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.billing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UntimedCode {
    private final String cptCode;
    private final String description;
    
    public void validate() {
        if (cptCode == null || cptCode.trim().isEmpty()) {
            throw new IllegalStateException("CPT code is required");
        }
    }
    
    public boolean isEvaluationCode() {
        return cptCode != null && cptCode.matches("9716[1-4]");
    }
}
'@

# ObjectiveFindings.java
Create-JavaFile "$DOMAIN_BASE\model\objective" "ObjectiveFindings.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.objective;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class ObjectiveFindings {
    private final List<String> precautions;
    private final String preTreatmentFindings;
    private final String postTreatmentFindings;
    private final String clinicalObservations;
    private final List<PerformedIntervention> performedInterventions;
    private final String instructions;
    
    public ObjectiveFindings excludeEvaluationCodes() {
        if (performedInterventions == null) return this;
        
        List<PerformedIntervention> filtered = performedInterventions.stream()
            .filter(intervention -> !intervention.isEvaluationCode())
            .collect(Collectors.toList());
            
        return this.toBuilder()
            .performedInterventions(filtered)
            .build();
    }
}
'@

# PerformedIntervention.java
Create-JavaFile "$DOMAIN_BASE\model\objective" "PerformedIntervention.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.objective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerformedIntervention {
    private final String cptCode;
    private final String description;
    private final Integer durationMinutes;
    private final Integer units;
    
    public boolean isEvaluationCode() {
        return cptCode != null && cptCode.matches("9716[1-4]");
    }
}
'@



# InitialEvaluation.java
Create-JavaFile "$DOMAIN_BASE\model" "InitialEvaluation.java" @'
package com.cob.emr.ddd.domain.medicalnote.model;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteType;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.SubjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
public class InitialEvaluation extends MedicalNote {
    
    private final SubjectiveFindings subjective;
    private final ObjectiveFindings objective;
    private final ClinicalAssessment assessment;
    private final PlanOfCare planOfCare;
    private final BillingInformation billing;
    
    public InitialEvaluation(Long patientCaseId,
                           int visitNumber,
                           LocalDate dateOfService,
                           NoteStatus status,
                           String createdBy,
                           LocalDateTime createdAt,
                           SubjectiveFindings subjective,
                           ObjectiveFindings objective,
                           ClinicalAssessment assessment,
                           PlanOfCare planOfCare,
                           BillingInformation billing) {
        super(patientCaseId, NoteType.INITIAL_EVALUATION, visitNumber, 
              dateOfService, status, createdBy, createdAt);
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.planOfCare = planOfCare;
        this.billing = billing;
    }
    
    @Override
    public void validate() {
        super.validate();
        
        if (this.getVisitNumber() != 1) {
            throw new IllegalStateException("Initial Evaluation must be visit number 1");
        }
        
        if (subjective == null) {
            throw new IllegalStateException("Initial Evaluation must have Subjective findings");
        }
        
        if (assessment == null) {
            throw new IllegalStateException("Initial Evaluation must have Assessment");
        }
        
        if (planOfCare == null) {
            throw new IllegalStateException("Initial Evaluation must have Plan of Care");
        }
        
        if (billing == null) {
            throw new IllegalStateException("Initial Evaluation must have Billing information");
        }
        
        subjective.validate();
        assessment.validate();
        planOfCare.validate();
        billing.validate();
        
        if (!billing.hasEvaluationCode()) {
            throw new IllegalStateException("Initial Evaluation must have evaluation billing code (97161-97163)");
        }
    }
    
    @Override
    public boolean canFinalize() {
        return super.canFinalize() 
            && subjective != null 
            && assessment != null 
            && assessment.hasGoals()
            && planOfCare != null
            && billing != null
            && billing.hasEvaluationCode();
    }
    
    public ObjectiveFindings deriveObjectiveForDailyNote() {
        if (billing == null) {
            return null;
        }
        return billing.toObjectiveFindings();
    }
    
    public String getChiefComplaint() {
        return subjective != null && subjective.getChiefComplaint() != null
            ? subjective.getChiefComplaint().getPrimaryConcern()
            : null;
    }
    
    public Integer getPainScale() {
        return subjective != null && subjective.getPainAssessment() != null
            ? subjective.getPainAssessment().getPainScale()
            : null;
    }
    
    public String getRehabPotential() {
        return assessment != null && assessment.getRehabilitationPotential() != null
            ? assessment.getRehabilitationPotential().getRating().name()
            : null;
    }
    
    public String getWorkStatus() {
        return subjective != null 
            && subjective.getMedicalHistory() != null
            && subjective.getMedicalHistory().getOccupationalHistory() != null
            ? subjective.getMedicalHistory().getOccupationalHistory().getWorkStatus().name()
            : null;
    }
    
    public String getComplexityLevel() {
        return assessment != null && assessment.getPatientPresentation() != null
            ? assessment.getPatientPresentation().getComplexityLevel().name()
            : null;
    }
    
    public Boolean isPatientCompliant() {
        return assessment != null && assessment.getPatientCompliance() != null
            ? assessment.getPatientCompliance().isCompliant()
            : null;
    }
    
    public LocalDate getInjuryOnsetDate() {
        return subjective != null
            && subjective.getInjuryHistory() != null
            ? subjective.getInjuryHistory().getInjuryOnsetDate()
            : null;
    }
    
    public Integer getTotalBillingUnits() {
        return billing != null ? billing.calculateTotalUnits() : null;
    }
}
'@