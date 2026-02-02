package com.cob.emr.usecases.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.repositories.signature.DoctorSignatureTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SignatureTokenService {
    @Autowired
    DoctorSignatureTokenRepository doctorSignatureTokenRepository;

    public DoctorSignatureToken generateToken(ClinicalEMRUser doctor) {
        String token = UUID.randomUUID().toString();
        DoctorSignatureToken signatureToken = new DoctorSignatureToken();
        signatureToken.setToken(token);
        signatureToken.setDoctor(doctor);
        signatureToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        return doctorSignatureTokenRepository.save(signatureToken);
    }
}
