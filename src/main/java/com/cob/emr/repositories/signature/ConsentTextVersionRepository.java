package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.ConsentTextVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsentTextVersionRepository extends JpaRepository<ConsentTextVersion, Long> {
    Optional<ConsentTextVersion> findTopByActiveTrueOrderByEffectiveFromDesc();

    Optional<ConsentTextVersion> findByVersionCode(String versionCode);
}
