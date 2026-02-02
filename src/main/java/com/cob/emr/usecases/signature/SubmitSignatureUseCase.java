package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignature;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureCaptureMetadata;
import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.enums.SignatureAuditEvent;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.SignatureMetadataDto;
import com.cob.emr.model.signature.SignatureSubmitRequest;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureCaptureMetadataRepository;
import com.cob.emr.usecases.audit.SignatureAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class SubmitSignatureUseCase {
    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;
    @Autowired
    SignatureCaptureMetadataRepository signatureCaptureMetadataRepository;
    @Autowired
    DoctorSignatureRepository doctorSignatureRepository;
    @Autowired
    ValidateConsentUseCase validateConsentUseCase;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    SignatureAuditService signatureAuditService;

    @Transactional
    public void submit(String token, SignatureSubmitRequest request) throws SignatureTokenException {
        if (token == null || !UUID_PATTERN.matcher(token).matches()) {
            signatureAuditService.logFailure(SignatureAuditEvent.TOKEN_MALFORMED,
                    null, null, SignatureTokenException.TOKEN_MALFORMED,
                    Map.of("reason", "Token format invalid"));
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.TOKEN_MALFORMED, null);
        }

        Optional<DoctorSignatureToken> optionalToken = doctorSignatureTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            signatureAuditService.logFailure(SignatureAuditEvent.TOKEN_NOT_FOUND,
                    null, null, SignatureTokenException.TOKEN_NOT_FOUND,
                    Map.of("reason", "Token not found"));
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        DoctorSignatureToken signatureToken = optionalToken.get();
        if (signatureToken.isUsed()) {
            signatureAuditService.logFailure(SignatureAuditEvent.TOKEN_NOT_FOUND,
                    signatureToken.getDoctor().getId(), signatureToken.getId(),
                    SignatureTokenException.TOKEN_NOT_FOUND,
                    Map.of("reason", "Token already used"));
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        if (signatureToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            signatureAuditService.logFailure(SignatureAuditEvent.TOKEN_EXPIRED,
                    signatureToken.getDoctor().getId(), signatureToken.getId(),
                    SignatureTokenException.TOKEN_EXPIRED,
                    Map.of("expiresAt", signatureToken.getExpiresAt().toString()));
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.TOKEN_EXPIRED, null);
        }

        ClinicalEMRUser doctor = signatureToken.getDoctor();

        Optional<SignatureCaptureMetadata> optionalMetadata =
                signatureCaptureMetadataRepository.findBySignatureToken(signatureToken);
        if (optionalMetadata.isEmpty()) {
            signatureAuditService.logFailure(SignatureAuditEvent.SIGNATURE_SUBMISSION_FAILED,
                    doctor.getId(), signatureToken.getId(),
                    SignatureTokenException.OTP_NOT_VERIFIED,
                    Map.of("reason", "OTP not verified, no capture metadata"));
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_NOT_VERIFIED, null);
        }

        if (request.getSignatureData() == null || request.getSignatureData().isBlank()) {
            signatureAuditService.logFailure(SignatureAuditEvent.SIGNATURE_SUBMISSION_FAILED,
                    doctor.getId(), signatureToken.getId(),
                    SignatureTokenException.SIGNATURE_DATA_MISSING,
                    Map.of("reason", "Signature data missing"));
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.SIGNATURE_DATA_MISSING, null);
        }

        try {
            validateConsentUseCase.validateConsentData(request.getConsent());
        } catch (SignatureTokenException e) {
            signatureAuditService.logFailure(SignatureAuditEvent.CONSENT_VALIDATION_FAILED,
                    doctor.getId(), signatureToken.getId(), e.getCode(),
                    Map.of("reason", "Consent validation failed during submission"));
            throw e;
        }

        SignatureMetadataDto meta = request.getMetadata();
        DoctorSignature signature = new DoctorSignature();
        signature.setSignatureToken(signatureToken);
        signature.setSignatureData(request.getSignatureData());
        signature.setCaptureMethod(meta.getCaptureMethod());
        signature.setUserAgent(meta.getUserAgent());
        signature.setScreenResolution(meta.getScreenResolution());
        signature.setDevicePixelRatio(meta.getDevicePixelRatio());
        signature.setPlatform(meta.getPlatform());
        signature.setSha256Hash(meta.getSha256Hash());
        signature.setFontFamily(meta.getFontFamily());

        try {
            Instant instant = Instant.parse(meta.getCapturedAt());
            signature.setCapturedAt(LocalDateTime.ofInstant(instant, ZoneOffset.UTC).truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        } catch (DateTimeParseException e) {
            signature.setCapturedAt(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        }

        DoctorSignature savedSignature = doctorSignatureRepository.save(signature);

        SignatureCaptureMetadata metadata = optionalMetadata.get();
        metadata.setSignatureId(savedSignature.getId());
        signatureCaptureMetadataRepository.save(metadata);

        signatureToken.setUsed(true);
        doctorSignatureTokenRepository.save(signatureToken);

        signatureAuditService.logSuccess(SignatureAuditEvent.SIGNATURE_SUBMITTED,
                doctor.getId(), signatureToken.getId(),
                Map.of("signatureId", savedSignature.getId().toString()));

        doctor.setDoctorStatus(DoctorStatus.DOCTOR_ACTIVE);
        clinicalEMRUserRepository.save(doctor);

        signatureAuditService.logSuccess(SignatureAuditEvent.DOCTOR_ACTIVATED,
                doctor.getId(), signatureToken.getId(),
                Map.of("doctorEmail", doctor.getEmail()));
    }
}
