package com.cob.emr.service.patient.updater;

import com.cob.emr.Utils.ListUtils;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientInsurance;
import com.cob.emr.model.patient.insurance.PatientInsuranceModel;
import com.cob.emr.repositories.patient.insurance.PatientInsuranceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PatientInsuranceUpdaterService {

    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;
    @Autowired
    ModelMapper mapper;

    public void updatePatientInsurances(Patient original, List<PatientInsuranceModel> patientInsuranceModels) {
        delete(original.getId(), patientInsuranceModels);

        update(original, patientInsuranceModels);

        persist(original, patientInsuranceModels);
    }

    private void delete(Long patientId, List<PatientInsuranceModel> submittedPatientInsurance) {
        List<Long> originalIds = patientInsuranceRepository.findByPatient_Id(patientId)
                .stream()
                .map(PatientInsurance::getId)
                .collect(Collectors.toList());
        List<Long> persistedSubmittedIds = submittedPatientInsurance
                .stream()
                .map(PatientInsuranceModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> toBeDeleted = ListUtils.returnDifference(originalIds, persistedSubmittedIds);

        if (toBeDeleted.size() > 0)
            patientInsuranceRepository.deleteAllById(toBeDeleted);
    }

    private void update(Patient original, List<PatientInsuranceModel> submittedPatientInsurance) {
        List<PatientInsurance> persistedSubmittedCases = submittedPatientInsurance
                .stream()
                .filter(patientInsuranceModel -> patientInsuranceModel.getId() != null)
                .map(patientInsuranceModel -> {
                    PatientInsurance toBeUpdated = mapper.map(patientInsuranceModel, PatientInsurance.class);
                    toBeUpdated.setPatient(original);
                    return toBeUpdated;
                })
                .collect(Collectors.toList());
        if (persistedSubmittedCases.size() > 0)
            patientInsuranceRepository.saveAll(persistedSubmittedCases);
    }

    private void persist(Patient original, List<PatientInsuranceModel> submittedPatientInsurance) {
        List<PatientInsurance> newSubmittedCases = submittedPatientInsurance
                .stream()
                .filter(patientInsuranceModel -> patientInsuranceModel.getId() == null)
                .map(patientInsuranceModel -> {
                    PatientInsurance toBeSaved = mapper.map(patientInsuranceModel, PatientInsurance.class);
                    toBeSaved.setPatient(original);
                    return toBeSaved;
                })
                .collect(Collectors.toList());

        if (newSubmittedCases.size() > 0)
            patientInsuranceRepository.saveAll(newSubmittedCases);
    }
}
