package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.profile.ROMModelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ROMModelEntityRepository extends CrudRepository<ROMModelEntity, Long> {

    Optional<List<ROMModelEntity>> findByRomTestName(String romTestName);
}
