package com.cob.emr.usecases.patient;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.SearchType;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.PatientSearchModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SearchPatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public PatientResponse search(PatientSearchModel searchModel, Long clinicId, Long organizationId) {
        Long effectiveClinicId = null;
        Long effectiveOrganizationId = null;
        if (searchModel.getSearchType() == SearchType.ALL_CLINIC_PATIENTS) {
            effectiveOrganizationId = organizationId;
        } else {
            effectiveClinicId = clinicId;
        }
        String patientStatus = searchModel.getPatientStatus() != null
                ? searchModel.getPatientStatus().name()
                : null;
        List<Patient> patients = patientRepository.searchPatients(
                effectiveClinicId,
                effectiveOrganizationId,
                searchModel.getFirstName(),
                searchModel.getLastName(),
                searchModel.getDateOfBirth(),
                searchModel.getInsuranceCompanyId(),
                searchModel.getTherapistId(),
                searchModel.getPhoneNumber(),
                patientStatus,
                searchModel.getEmrId()
        );
        List<PatientModel> records = patients.stream().map(patient -> {
                    PatientModel model = mapper.map(patient, PatientModel.class);
                    model.setClinicsId(patient.getClinics()
                            .stream()
                            .map(clinic -> clinic.getId()).collect(Collectors.toList()));
                    enrichTherapistData(patient, model);
                    return model;
                })
                .collect(Collectors.toList());
        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(records.size())
                .records(records)
                .build();
    }

    private void enrichTherapistData(Patient patient, PatientModel model) {
        if (patient.getCases() == null || model.getCases() == null) return;

        Map<Long, PatientCase> caseEntityMap = patient.getCases().stream()
                .collect(Collectors.toMap(PatientCase::getId, pc -> pc));

        for (PatientCaseModel caseModel : model.getCases()) {
            if (caseModel.getId() == null) continue;

            PatientCase caseEntity = caseEntityMap.get(caseModel.getId());
            if (caseEntity != null && caseEntity.getClinicalEMRUser() != null) {
                caseModel.setTherapist(caseEntity.getClinicalEMRUser().getId());
                caseModel.setTherapistUUID(caseEntity.getClinicalEMRUser().getUuid());
            }
        }
    }
}
