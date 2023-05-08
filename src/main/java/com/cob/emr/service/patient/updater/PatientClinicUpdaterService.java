package com.cob.emr.service.patient.updater;

import com.cob.emr.Utils.ListUtils;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientClinicUpdaterService {
    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    PatientRepository patientRepository;

    public void update(Patient original, List<Long> submittedClinicsIds) {
        List<Long> originalClinicIds = original.getClinics()
                .stream()
                .map(Clinic::getId).collect(Collectors.toList());


        int toBeAdded = addClinics(original, submittedClinicsIds, originalClinicIds);
        int toBeDelete = removeClinics(original, originalClinicIds, submittedClinicsIds);
        if (toBeAdded > 0 || toBeDelete > 0)
            patientRepository.save(original);

    }

    private int addClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeAdd = ListUtils.returnDifference(source, destination);
        if (clinicsIdsToBeAdd.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeAdd)
                    .forEach(patient::addClinic);
        return clinicsIdsToBeAdd.size();
    }

    private int removeClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeRemoved = ListUtils.returnDifference(source, destination);
        if (clinicsIdsToBeRemoved.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeRemoved)
                    .forEach(patient::removeClinic);
        return clinicsIdsToBeRemoved.size();
    }
}
