package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreateClinicalUserUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    public void create(ClinicalEMRUserModel model) {
        ClinicalEMRUser clinicalEMRUser = mapper.map(model, ClinicalEMRUser.class);
        clinicalEMRUser.setDoctorStatus(DoctorStatus.DOCTOR_PENDING);
        model.getClinicIds()
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clinicalEMRUser.addClinic(clinic));
        clinicalEMRUserRepository.save(clinicalEMRUser);
    }
}
