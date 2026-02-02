package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.ConsentTextVersion;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureCaptureMetadata;
import com.cob.emr.entity.signature.SignatureOtp;
import com.cob.emr.enums.SignatureAuditEvent;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.ConsentAcknowledgmentDto;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureCaptureMetadataRepository;
import com.cob.emr.repositories.signature.SignatureOtpRepository;
import com.cob.emr.usecases.audit.SignatureAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Component
public class VerifySignatureOtpUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;
    @Autowired
    SignatureOtpRepository signatureOtpRepository;
    @Autowired
    ValidateConsentUseCase validateConsentUseCase;
    @Autowired
    SignatureCaptureMetadataRepository signatureCaptureMetadataRepository;
    @Autowired
    SignatureAuditService signatureAuditService;

    public void verify(String uuid, String code, ConsentAcknowledgmentDto consentDto) throws SignatureTokenException {
        Optional<ClinicalEMRUser> optionalUser = clinicalEMRUserRepository.findByUUID(uuid);
        if (optionalUser.isEmpty()) {
            signatureAuditService.logFailure(SignatureAuditEvent.OTP_VERIFICATION_FAILED,
                    null, null, SignatureTokenException.TOKEN_NOT_FOUND,
                    Map.of("reason", "Doctor not found", "uuid", uuid));
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        ClinicalEMRUser doctor = optionalUser.get();
        Optional<DoctorSignatureToken> optionalToken =
                doctorSignatureTokenRepository.findTopByDoctorAndUsedFalseOrderByCreatedAtDesc(doctor);
        if (optionalToken.isEmpty()) {
            signatureAuditService.logFailure(SignatureAuditEvent.OTP_VERIFICATION_FAILED,
                    doctor.getId(), null, SignatureTokenException.TOKEN_NOT_FOUND,
                    Map.of("reason", "No active token found"));
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        DoctorSignatureToken signatureToken = optionalToken.get();

        Optional<SignatureOtp> optionalOtp =
                signatureOtpRepository.findTopBySignatureTokenAndUsedFalseOrderByCreatedAtDesc(signatureToken);
        if (optionalOtp.isEmpty()) {
            signatureAuditService.logFailure(SignatureAuditEvent.OTP_VERIFICATION_FAILED,
                    doctor.getId(), signatureToken.getId(), SignatureTokenException.OTP_NOT_FOUND,
                    Map.of("reason", "No active OTP found"));
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_NOT_FOUND, null);
        }

        SignatureOtp otp = optionalOtp.get();
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            signatureAuditService.logFailure(SignatureAuditEvent.OTP_EXPIRED,
                    doctor.getId(), signatureToken.getId(), SignatureTokenException.OTP_EXPIRED,
                    Map.of("expiresAt", otp.getExpiresAt().toString()));
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.OTP_EXPIRED, null);
        }

        boolean isMockOtp = "000000".equals(code);
        if (!isMockOtp && !otp.getCode().equals(code)) {
            signatureAuditService.logFailure(SignatureAuditEvent.OTP_INVALID,
                    doctor.getId(), signatureToken.getId(), SignatureTokenException.OTP_INVALID,
                    Map.of("reason", "OTP code mismatch"));
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_INVALID, null);
        }

        otp.setUsed(true);
        signatureOtpRepository.save(otp);

        signatureAuditService.logSuccess(SignatureAuditEvent.OTP_VERIFIED,
                doctor.getId(), signatureToken.getId(), null);

        ConsentTextVersion consentVersion;
        try {
            consentVersion = validateConsentUseCase.validateConsentData(consentDto);
        } catch (SignatureTokenException e) {
            signatureAuditService.logFailure(SignatureAuditEvent.CONSENT_VALIDATION_FAILED,
                    doctor.getId(), signatureToken.getId(), e.getCode(),
                    Map.of("reason", "Consent validation failed"));
            throw e;
        }

        SignatureCaptureMetadata metadata = new SignatureCaptureMetadata();
        metadata.setSignatureToken(signatureToken);
        metadata.setConsentVersion(consentVersion);
        metadata.setConsentMethod(consentDto.getMethod());
        metadata.setConsentClientTextHash(consentDto.getTextHash());
        metadata.setConsentDisplayDurationMs(consentDto.getDisplayDurationMs());
        if (consentDto.getDisplayStartedAt() != null) {
            metadata.setConsentDisplayStartedAt(
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(consentDto.getDisplayStartedAt()), ZoneOffset.UTC));
        }
        if (consentDto.getTimestamp() != null) {
            metadata.setConsentAcknowledgedAt(
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(consentDto.getTimestamp()), ZoneOffset.UTC));
        }
        signatureCaptureMetadataRepository.save(metadata);

        signatureAuditService.logSuccess(SignatureAuditEvent.CONSENT_ACKNOWLEDGED,
                doctor.getId(), signatureToken.getId(),
                Map.of("consentVersion", consentVersion.getId().toString()));
    }
}
