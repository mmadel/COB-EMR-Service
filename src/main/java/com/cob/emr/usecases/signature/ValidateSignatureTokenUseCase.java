package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.SignatureTokenValidationResponse;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ValidateSignatureTokenUseCase {
    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;

    public SignatureTokenValidationResponse validate(String token) throws SignatureTokenException {
        if (token == null || !UUID_PATTERN.matcher(token).matches()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.TOKEN_MALFORMED, null);
        }

        Optional<DoctorSignatureToken> optionalToken = doctorSignatureTokenRepository.findByToken(token);
        if (optionalToken.isEmpty() || optionalToken.get().isUsed()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.TOKEN_NOT_FOUND, null);
        }

        DoctorSignatureToken signatureToken = optionalToken.get();
        if (signatureToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.TOKEN_EXPIRED, null);
        }

        ClinicalEMRUser doctor = signatureToken.getDoctor();
        return SignatureTokenValidationResponse.builder()
                .valid(true)
                .doctorName(doctor.getFirstName() + " " + doctor.getLastName())
                .doctorEmail(doctor.getEmail())
                .purpose("Account activation digital signature")
                .expiresAt(signatureToken.getExpiresAt())
                .build();
    }
}
