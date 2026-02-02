package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignatureOtpRepository extends JpaRepository<SignatureOtp, Long> {
    Optional<SignatureOtp> findTopBySignatureTokenAndUsedFalseOrderByCreatedAtDesc(DoctorSignatureToken signatureToken);
}
