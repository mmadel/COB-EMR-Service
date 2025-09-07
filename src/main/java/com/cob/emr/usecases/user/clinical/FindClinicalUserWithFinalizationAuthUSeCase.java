package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClinicalUserWithFinalizationAuthUSeCase {
    private static final String FINALIZE_MEDICAL_NOTE_ROLE = "finalize-medical-note-role";
    private static final String FINALIZE_MEDICAL_NOTE_ROLE_SCOPE = "true";
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ModelMapper mapper;

    public List<ClinicalEMRUserModel> find(Long clinicId , String uuid) {
        List<ClinicalEMRUser> clinicalEMRUsers = clinicalEMRUserRepository.findByClinic(clinicId)
                .get();
        return clinicalEMRUsers.stream()
                .filter(user -> !user.getUuid().equals(uuid))
//                .filter(user -> user.getRoleScope().stream()
//                        .anyMatch(scope ->
//                                FINALIZE_MEDICAL_NOTE_ROLE.equals(scope.getRole()) &&
//                                        FINALIZE_MEDICAL_NOTE_ROLE_SCOPE.equals(scope.getScope()))
//                )
                .collect(Collectors.toList())
                .stream()
                .map(clinicalEMRUser -> mapper.map(clinicalEMRUser, ClinicalEMRUserModel.class))
                .collect(Collectors.toList());
    }
}
