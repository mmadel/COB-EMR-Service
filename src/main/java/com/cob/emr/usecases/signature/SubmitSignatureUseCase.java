package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignature;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureCaptureMetadata;
import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.SignatureMetadataDto;
import com.cob.emr.model.signature.SignatureSubmitRequest;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureCaptureMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
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

    @Transactional
    public void submit(String token, SignatureSubmitRequest request) throws SignatureTokenException {
        if (token == null || !UUID_PATTERN.matcher(token).matches()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.TOKEN_MALFORMED, null);
        }

        Optional<DoctorSignatureToken> optionalToken = doctorSignatureTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        DoctorSignatureToken signatureToken = optionalToken.get();
        if (signatureToken.isUsed()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        if (signatureToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.TOKEN_EXPIRED, null);
        }

        Optional<SignatureCaptureMetadata> optionalMetadata =
                signatureCaptureMetadataRepository.findBySignatureToken(signatureToken);
        if (optionalMetadata.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_NOT_VERIFIED, null);
        }

        if (request.getSignatureData() == null || request.getSignatureData().isBlank()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.SIGNATURE_DATA_MISSING, null);
        }

        validateConsentUseCase.validateConsentData(request.getConsent());

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

        ClinicalEMRUser doctor = signatureToken.getDoctor();
        doctor.setDoctorStatus(DoctorStatus.DOCTOR_ACTIVE);
        clinicalEMRUserRepository.save(doctor);
    }
}
