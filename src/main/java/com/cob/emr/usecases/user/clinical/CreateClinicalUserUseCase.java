package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.enums.SignatureAuditEvent;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.usecases.audit.SignatureAuditService;
import com.cob.emr.usecases.signature.SignatureEmailService;
import com.cob.emr.usecases.signature.SignatureTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
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
    @Autowired
    SignatureTokenService signatureTokenService;
    @Autowired
    SignatureEmailService signatureEmailService;
    @Autowired
    SignatureAuditService signatureAuditService;

    public Long create(ClinicalEMRUserModel model) {
        ClinicalEMRUser clinicalEMRUser = mapper.map(model, ClinicalEMRUser.class);
        clinicalEMRUser.setDoctorStatus(DoctorStatus.DOCTOR_PENDING);
        model.getClinicIds()
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clinicalEMRUser.addClinic(clinic));
        ClinicalEMRUser savedEntity = clinicalEMRUserRepository.save(clinicalEMRUser);

        signatureAuditService.logSuccess(SignatureAuditEvent.CLINICAL_USER_CREATED,
                savedEntity.getId(), null,
                Map.of("email", savedEntity.getEmail(),
                        "name", savedEntity.getFirstName() + " " + savedEntity.getLastName()));

        DoctorSignatureToken signatureToken = signatureTokenService.generateToken(savedEntity);

        signatureAuditService.logSuccess(SignatureAuditEvent.SIGNATURE_TOKEN_GENERATED,
                savedEntity.getId(), signatureToken.getId(),
                Map.of("expiresAt", signatureToken.getExpiresAt().toString()));

        signatureEmailService.sendSignatureLink(savedEntity.getEmail(),
                savedEntity.getFirstName() + " " + savedEntity.getLastName(),
                signatureToken.getToken());

        signatureAuditService.logSuccess(SignatureAuditEvent.SIGNATURE_EMAIL_SENT,
                savedEntity.getId(), signatureToken.getId(),
                Map.of("email", savedEntity.getEmail()));

        return savedEntity.getId();
    }
}
