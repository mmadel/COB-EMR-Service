package com.cob.emr.usecases.organization;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.OrganizationException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.usecases.clinic.DeleteClinicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class DeleteOrganizationUserCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    DeleteClinicUseCase deleteClinicUseCase;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class, OrganizationException.class})
    public Long delete(Long id) throws OrganizationException {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationException(OrganizationException.ORGANIZATION_NOT_FOUND, new Object[]{id}));
        organization.getClinics().stream()
                .forEach(clinic -> {
                    deleteClinicalUsers(clinic.getClinicalUsers());
                    deleteClinicUseCase.deleteByOrganization(clinic.getId(), id);
                });
        organizationRepository.delete(organization);
        return id;
    }

    private void deleteClinicalUsers(Set<ClinicalEMRUser> clinicalUsers) {
        clinicalUsers
                .forEach(clinicalEMRUser -> {
                    clinicalEMRUserRepository.delete(clinicalEMRUser);
                });
    }
}
