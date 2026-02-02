package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureOtp;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import com.cob.emr.repositories.signature.SignatureOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SendSignatureOtpUseCase {
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;
    @Autowired
    SignatureOtpRepository signatureOtpRepository;
    @Autowired
    OtpSmsService otpSmsService;

    public void send(String uuid) throws SignatureTokenException {
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
        if (signatureToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SignatureTokenException(HttpStatus.GONE,
                    SignatureTokenException.TOKEN_EXPIRED, null);
        }

        String phone = doctor.getPhone();
        String code = generateOtpCode();

        SignatureOtp otp = new SignatureOtp();
        otp.setCode(code);
        otp.setPhone(phone);
        otp.setSignatureToken(signatureToken);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        signatureOtpRepository.save(otp);

        String doctorName = doctor.getFirstName() + " " + doctor.getLastName();
        otpSmsService.sendOtp(phone, code, doctorName);
    }

    private String generateOtpCode() {
        int bound = (int) Math.pow(10, OTP_LENGTH);
        int number = random.nextInt(bound);
        return String.format("%0" + OTP_LENGTH + "d", number);
    }
}
