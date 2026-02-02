package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.model.user.OrganizationUserModel;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAllOrganizationUsersUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;

    public List<OrganizationUserModel> find(Long organizationId) {
        List<OrganizationUserModel> users = new ArrayList<>();
        clinicalEMRUserRepository.findByOrganization(organizationId)
                .forEach(user -> users.add(mapClinicalUser(user)));
        clericalEMRUserRepository.findByOrganization(organizationId)
                .forEach(user -> users.add(mapClericalUser(user)));
        return users;
    }

    private OrganizationUserModel mapClinicalUser(ClinicalEMRUser user) {
        OrganizationUserModel model = new OrganizationUserModel();
        model.setUserName(user.getAccountName());
        model.setEmail(user.getEmail());
        model.setUserType("clinical");
        model.setId(user.getId());
        model.setUuid(user.getUuid());
        model.setClinicIds(user.getClinics().stream()
                .map(Clinic::getId)
                .collect(Collectors.toList()));
        return model;
    }

    private OrganizationUserModel mapClericalUser(ClericalEMRUser user) {
        OrganizationUserModel model = new OrganizationUserModel();
        model.setUserName(user.getAccountName());
        model.setEmail(user.getEmail());
        model.setUserType("clerical");
        model.setId(user.getId());
        model.setUuid(user.getUuid());
        model.setClinicIds(user.getClinics().stream()
                .map(Clinic::getId)
                .collect(Collectors.toList()));
        return model;
    }
}
