package com.cob.emr.repositories.signature;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorSignatureTokenRepository extends JpaRepository<DoctorSignatureToken, Long> {
    Optional<DoctorSignatureToken> findByToken(String token);

    Optional<DoctorSignatureToken> findTopByDoctorAndUsedFalseOrderByCreatedAtDesc(ClinicalEMRUser doctor);

    List<DoctorSignatureToken> findByDoctor(ClinicalEMRUser doctor);
}
