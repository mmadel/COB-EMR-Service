package com.cob.emr.usecases.clinic;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.EMRException;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    public void deleteById(Long clinicId) throws ClinicException {
        try {
            clinicRepository.deleteById(clinicId);
        } catch (Exception emrException) {
            throw new ClinicException(HttpStatus.CONFLICT, ClinicException.CLINIC_ASSIGN_TO_USER, new Object[]{clinicId});
        }
    }

    public void delete(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).get();
        clinicRepository.deleteByOrganization(organization);
    }
}
