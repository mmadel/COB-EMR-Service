package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.ConsentTextVersion;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureCaptureMetadata;
import com.cob.emr.entity.signature.SignatureOtp;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.ConsentAcknowledgmentDto;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureCaptureMetadataRepository;
import com.cob.emr.repositories.signature.SignatureOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public void verify(String uuid, String code, ConsentAcknowledgmentDto consentDto) throws SignatureTokenException {
        Optional<ClinicalEMRUser> optionalUser = clinicalEMRUserRepository.findByUUID(uuid);
        if (optionalUser.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        ClinicalEMRUser doctor = optionalUser.get();
        Optional<DoctorSignatureToken> optionalToken =
                doctorSignatureTokenRepository.findTopByDoctorAndUsedFalseOrderByCreatedAtDesc(doctor);
        if (optionalToken.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        DoctorSignatureToken signatureToken = optionalToken.get();

        Optional<SignatureOtp> optionalOtp =
                signatureOtpRepository.findTopBySignatureTokenAndUsedFalseOrderByCreatedAtDesc(signatureToken);
        if (optionalOtp.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_NOT_FOUND, null);
        }

        SignatureOtp otp = optionalOtp.get();
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.OTP_EXPIRED, null);
        }

        if (!otp.getCode().equals(code)) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.OTP_INVALID, null);
        }

        otp.setUsed(true);
        signatureOtpRepository.save(otp);

        ConsentTextVersion consentVersion = validateConsentUseCase.validateConsentData(consentDto);

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
    }
}
