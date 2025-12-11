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
