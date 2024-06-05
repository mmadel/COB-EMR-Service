package com.cob.emr.usecases.patient.update.clinic;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdatePatientClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    public void update(PatientModel model, Patient patient) {
        List<Long> source = model.getClinicsId();
        List<Long> target = patient.getClinics().stream()
                .map(clinic -> clinic.getId())
                .collect(Collectors.toList());
        List<Long> addedItems = findAdded(source, target);
        addedItems
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> patient.addClinic(clinic));
    }

    private List<Long> findAdded(List<Long> source, List<Long> target) {
        return source.stream()
                .filter(item -> !target.contains(item))
                .collect(Collectors.toList());
    }
}
