package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface SignatureOtpRepository extends JpaRepository<SignatureOtp, Long> {
    Optional<SignatureOtp> findTopBySignatureTokenAndUsedFalseOrderByCreatedAtDesc(DoctorSignatureToken signatureToken);

    @Modifying
    void deleteBySignatureToken(DoctorSignatureToken signatureToken);
}
