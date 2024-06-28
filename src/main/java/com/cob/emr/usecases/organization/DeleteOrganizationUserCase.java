package com.cob.emr.usecases.organization;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.OrganizationException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.usecases.clinic.DeleteClinicUseCase;
import com.cob.emr.usecases.insurancecompnay.DeleteInsuranceCompanyUseCase;
import com.cob.emr.usecases.patient.DeletePatientUseCase;
import com.cob.emr.usecases.user.keycloak.RemoveEMRRolesFromUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Autowired
    RemoveEMRRolesFromUserUseCase removeEMRRolesFromUserUseCase;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class, OrganizationException.class})
    public Long delete(Long id) throws OrganizationException {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationException(OrganizationException.ORGANIZATION_NOT_FOUND, new Object[]{id}));
        organization.getClinics().stream()
                .forEach(clinic -> {
                    deletePatientUseCase.deleteByClinic(clinic.getId());
                    deleteAppointmentTypes(clinic.getId());
                    deleteSchedulerConfiguration(clinic.getId());
                    deleteClinicalUsers(clinic);
                    deleteClericalUsers(clinic.getId());
                });
        deleteInsuranceCompanyUseCase.delete(id);
        deleteClinicUseCase.delete(id);
        deleteReferringProvider(id);
        organizationRepository.delete(organization);
        return id;
    }

    private void deleteAppointmentTypes(Long clinicId) {
        appointmentTypeRepository.deleteByClinic_Id(clinicId);
    }

    private void deleteSchedulerConfiguration(Long clinicId) {
        schedulerConfigurationRepository.deleteByClinicId(clinicId);
    }

    private void deleteReferringProvider(Long organizationId) {
        referringProviderRepository.deleteByOrganization_Id(organizationId);
    }

    private void deleteClinicalUsers(Clinic clinic) {
        List<ClinicalEMRUser> clinicalEMRUsers = clinicalEMRUserRepository.findByClinic(clinic.getId()).get();
        List<Long> ids = clinicalEMRUsers
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        List<String> uuids = clinicalEMRUsers
                .stream()
                .map(user -> user.getUuid())
                .collect(Collectors.toList());
        uuids.stream().forEach(uuid -> {
            removeEMRRolesFromUserUseCase.removeAll(uuid);
        });
        clinicalEMRUserRepository.deleteAllById(ids);

    }

    private void deleteClericalUsers(Long clinicId) {
        List<ClericalEMRUser> clericalEMRUsers = clericalEMRUserRepository.findByClinic(clinicId).get();
        List<Long> ids = clericalEMRUsers
                .stream()
                .map(clericalEMRUser -> clericalEMRUser.getId())
                .collect(Collectors.toList());

        List<String> uuids = clericalEMRUsers
                .stream()
                .map(user -> user.getUuid())
                .collect(Collectors.toList());
        uuids.stream().forEach(uuid -> {
            removeEMRRolesFromUserUseCase.removeAll(uuid);
        });
        clericalEMRUserRepository.deleteAllById(ids);
    }
}
