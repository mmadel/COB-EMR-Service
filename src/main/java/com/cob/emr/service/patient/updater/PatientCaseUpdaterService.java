package com.cob.emr.service.patient.updater;

import com.cob.emr.Utils.ListUtils;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PatientCaseUpdaterService {
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    ModelMapper mapper;

    public void updatePatientCases(Patient original, List<PatientCaseModel> patientCaseModels) {
        delete(original.getId(), patientCaseModels);

        assignCaseOrders(patientCaseModels);

        update(original, patientCaseModels);

        persist(original, patientCaseModels);
    }

    private void assignCaseOrders(List<PatientCaseModel> patientCaseModels) {
        List<PatientCaseModel> newCases = patientCaseModels.stream()
                .filter(m -> m.getId() == null)
                .collect(Collectors.toList());
        List<PatientCaseModel> existingCases = patientCaseModels.stream()
                .filter(m -> m.getId() != null)
                .sorted(Comparator.comparingInt(m -> m.getOrder() != null ? m.getOrder() : Integer.MAX_VALUE))
                .collect(Collectors.toList());

        if (!newCases.isEmpty()) {
            for (int i = 0; i < newCases.size(); i++) {
                newCases.get(i).setOrder(i);
            }
            for (int i = 0; i < existingCases.size(); i++) {
                existingCases.get(i).setOrder(newCases.size() + i);
            }
        }
    }

    private void delete(Long patientId, List<PatientCaseModel> submittedPatientCases) {
        List<Long> originalIds = patientCaseRepository.findByPatient_Id(patientId)
                .stream()
                .map(PatientCase::getId)
                .collect(Collectors.toList());
        List<Long> persistedSubmittedIds = submittedPatientCases
                .stream()
                .map(PatientCaseModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> toBeDeleted = ListUtils.returnDifference(originalIds, persistedSubmittedIds);

        if (toBeDeleted.size() > 0)
            patientCaseRepository.deleteAllById(toBeDeleted);
    }

    public void update(Patient original, List<PatientCaseModel> submittedPatientCases) {
        List<PatientCase> persistedSubmittedCases = submittedPatientCases
                .stream()
                .filter(patientCaseModel -> patientCaseModel.getId() != null)
                .map(patientCaseModel -> {
                    PatientCase toBeUpdated = mapper.map(patientCaseModel, PatientCase.class);
                    toBeUpdated.setPatient(original);
                    return toBeUpdated;
                })
                .collect(Collectors.toList());
        if (persistedSubmittedCases.size() > 0)
            patientCaseRepository.saveAll(persistedSubmittedCases);
    }

    public void persist(Patient original, List<PatientCaseModel> submittedPatientCases) {
        List<PatientCase> newSubmittedCases = submittedPatientCases
                .stream()
                .filter(patientCaseModel -> patientCaseModel.getId() == null)
                .map(patientCaseModel -> {
                    PatientCase toBeSaved = mapper.map(patientCaseModel, PatientCase.class);
                    toBeSaved.setPatient(original);
                    return toBeSaved;
                })
                .collect(Collectors.toList());

        if (newSubmittedCases.size() > 0)
            patientCaseRepository.saveAll(newSubmittedCases);
    }
}
