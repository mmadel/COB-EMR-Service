package com.cob.emr.usecases.patient.chart;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.enums.PatientChartAccessibility;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.chart.PatientChartAccessibilityModelRequest;
import com.cob.emr.model.patient.chart.PatientChartAccessibilityModelResponse;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PatientChartAccessibilityUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public PatientChartAccessibilityModelResponse check(PatientChartAccessibilityModelRequest model) {
        Patient patient = patientRepository.findById(model.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Set<Clinic> patientClinics = patient.getClinics();
        Clinic matchedClinic = patientClinics.stream()
                .filter(clinic -> clinic.getId().equals(model.getClinicId()))
                .findFirst()
                .orElse(null);

        if (matchedClinic != null) {
            return PatientChartAccessibilityModelResponse.builder()
                    .patientChartAccessibility(PatientChartAccessibility.AccessWithoutPrompt)
                    .promptedClinics(null)
                    .build();
        }

        // Get the prompted clinics
        List<Clinic> promptedClinics = getPromptedClinics(new ArrayList<>(patientClinics), model.getAllowedClinics());

        return promptedClinics == null || promptedClinics.isEmpty()
                ? PatientChartAccessibilityModelResponse.builder()
                .patientChartAccessibility(PatientChartAccessibility.NoAccess)
                .promptedClinics(null)
                .build()
                : PatientChartAccessibilityModelResponse.builder()
                .patientChartAccessibility(PatientChartAccessibility.AccessWithPrompt)
                .promptedClinics(
                        promptedClinics.stream()
                                .map(clinic -> mapper.map(clinic, ClinicModel.class))
                                .collect(Collectors.toList()))
                .build();

    }

    private List<Clinic> getPromptedClinics(List<Clinic> patientClinics, List<Long> allowedClinicIds) {
        boolean allAllowed = patientClinics.stream()
                .allMatch(clinic -> allowedClinicIds.contains(clinic.getId()));
        return allAllowed ? patientClinics :
                patientClinics.stream()
                        .filter(clinic -> allowedClinicIds.contains(clinic.getId()))
                        .collect(Collectors.toList());
    }
}
