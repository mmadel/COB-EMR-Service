package com.cob.emr.usecases.organization;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.OrganizationException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.usecases.appointment.type.DeleteAppointmentTypeUseCase;
import com.cob.emr.usecases.calendar.DeleteCalendarUseCase;
import com.cob.emr.usecases.clinic.DeleteClinicUseCase;
import com.cob.emr.usecases.insurancecompnay.DeleteInsuranceCompanyUseCase;
import com.cob.emr.usecases.patient.DeletePatientUseCase;
import com.cob.emr.usecases.referring.provider.DeleteReferringProviderUseCase;
import com.cob.emr.usecases.scheduler.DeleteSchedulerConfigurationUseCase;
import com.cob.emr.usecases.user.clerical.DeleteClericalUserUseCase;
import com.cob.emr.usecases.user.clinical.DeleteClinicalUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteOrganizationUserCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    DeleteClinicUseCase deleteClinicUseCase;
    @Autowired
    DeletePatientUseCase deletePatientUseCase;
    @Autowired
    DeleteInsuranceCompanyUseCase deleteInsuranceCompanyUseCase;
    @Autowired
    DeleteAppointmentTypeUseCase deleteAppointmentTypeUseCase;
    @Autowired
    DeleteCalendarUseCase deleteCalendarUseCase;
    @Autowired
    DeleteSchedulerConfigurationUseCase deleteSchedulerConfigurationUseCase;
    @Autowired
    DeleteReferringProviderUseCase deleteReferringProviderUseCase;
    @Autowired
    DeleteClinicalUserUseCase deleteClinicalUserUseCase;
    @Autowired
    DeleteClericalUserUseCase deleteClericalUserUseCase;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class, OrganizationException.class})
    public Long delete(Long id) throws OrganizationException {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationException(OrganizationException.ORGANIZATION_NOT_FOUND, new Object[]{id}));
        organization.getClinics().stream()
                .forEach(clinic -> {
                    deletePatientUseCase.deleteByClinic(clinic.getId());
                    deleteAppointmentTypeUseCase.deleteByClinic(clinic.getId());
                    deleteSchedulerConfigurationUseCase.deleteByClinic(clinic.getId());
                    deleteCalendarUseCase.deleteByClinic(clinic.getId());
                    deleteClinicalUserUseCase.deleteByClinic(clinic.getId());
                    deleteClericalUserUseCase.deleteByClinic(clinic.getId());
                });
        deleteInsuranceCompanyUseCase.delete(id);
        deleteClinicUseCase.delete(id);
        deleteReferringProviderUseCase.deleteByOrganization(id);
        organizationRepository.delete(organization);
        return id;
    }
}
