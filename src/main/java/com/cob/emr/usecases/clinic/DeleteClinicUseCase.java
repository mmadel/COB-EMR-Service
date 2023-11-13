package com.cob.emr.usecases.clinic;

import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.EMRException;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;

    public void delete(Long clinicId) throws ClinicException {
        try {
            clinicRepository.deleteById(clinicId);
        } catch (Exception emrException) {
            throw new ClinicException(HttpStatus.CONFLICT, ClinicException.CLINIC_ASSIGN_TO_USER, new Object[]{clinicId});
        }
    }

    public void deleteByOrganization(Long clinicId, Long organizationId) {
        clinicRepository.deleteByOrganization(clinicId, organizationId);
    }
}
