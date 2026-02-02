package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.AdministratorUser;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.repositories.audit.SignatureAuditLogRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.security.AdministratorUserRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureCaptureMetadataRepository;
import com.cob.emr.repositories.signature.SignatureOtpRepository;
import com.cob.emr.usecases.user.keycloak.KeyCloakUsersRemoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class DeleteUserUseCase {

    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Autowired
    AdministratorUserRepository administratorUserRepository;
    @Autowired
    KeyCloakUsersRemoverService keyCloakUsersRemoverService;
    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;
    @Autowired
    DoctorSignatureRepository doctorSignatureRepository;
    @Autowired
    SignatureOtpRepository signatureOtpRepository;
    @Autowired
    SignatureCaptureMetadataRepository signatureCaptureMetadataRepository;
    @Autowired
    SignatureAuditLogRepository signatureAuditLogRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    public void delete(String uuid) throws UserException {
        Optional<ClinicalEMRUser> clinicalUser = clinicalEMRUserRepository.findByUUID(uuid);
        if (clinicalUser.isPresent()) {
            deleteClinicalUser(clinicalUser.get());
            tryRemoveKeycloakUser(uuid);
            return;
        }

        Optional<ClericalEMRUser> clericalUser = clericalEMRUserRepository.findByUUID(uuid);
        if (clericalUser.isPresent()) {
            deleteClericalUser(clericalUser.get(), uuid);
            tryRemoveKeycloakUser(uuid);
            return;
        }

        Optional<AdministratorUser> adminUser = administratorUserRepository.findById(uuid);
        if (adminUser.isPresent()) {
            administratorUserRepository.delete(adminUser.get());
            tryRemoveKeycloakUser(uuid);
            return;
        }

        throw new UserException(HttpStatus.NOT_FOUND, UserException.USER_NOT_FOUND, new Object[]{uuid});
    }

    private void tryRemoveKeycloakUser(String uuid) {
        try {
            keyCloakUsersRemoverService.removeKCUser(uuid);
        } catch (UserException ignored) {
        }
    }

    private void deleteClinicalUser(ClinicalEMRUser clinicalUser) throws UserException {
        if (patientCaseRepository.existsByClinicalEMRUser_Id(clinicalUser.getId()))
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_ASSIGNED_TO_PATIENT_CASE, new Object[]{clinicalUser.getId()});

        List<DoctorSignatureToken> tokens = doctorSignatureTokenRepository.findByDoctor(clinicalUser);
        for (DoctorSignatureToken token : tokens) {
            doctorSignatureRepository.deleteBySignatureToken(token);
            signatureOtpRepository.deleteBySignatureToken(token);
            signatureCaptureMetadataRepository.deleteBySignatureToken(token);
        }
        doctorSignatureTokenRepository.deleteAll(tokens);
        signatureAuditLogRepository.deleteByDoctorId(clinicalUser.getId());

        Set<Clinic> copy = new HashSet<>(clinicalUser.getClinics());
        copy.forEach(clinic -> clinic.removeClinicalUser(clinicalUser));
        clinicalEMRUserRepository.delete(clinicalUser);
    }

    private void deleteClericalUser(ClericalEMRUser clericalUser, String uuid) {
        Set<Clinic> copy = new HashSet<>(clericalUser.getClinics());
        copy.forEach(clinic -> clinic.removeClericalUser(clericalUser));
        clericalEMRUserRepository.deleteByUuid(uuid);
    }
}
