package com.cob.emr.service.patient.authorization;


import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.model.patient.authorization.PatientCaseAuthorizationDTO;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PatientCaseAuthorizationService {

    private final PatientCaseAuthorizationRepository repository;

    public PatientCaseAuthorizationService(PatientCaseAuthorizationRepository repository) {
        this.repository = repository;
    }

    public List<PatientCaseAuthorizationDTO> saveOrUpdateList(List<PatientCaseAuthorizationDTO> dtos) {
        // Convert incoming DTOs to entities
        List<PatientCaseAuthorization> incoming = dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        // Fetch all existing auths from DB

        List<PatientCaseAuthorization> existing = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        ;

        // Determine IDs present in the incoming list
        Set<Long> incomingIds = incoming.stream()
                .map(PatientCaseAuthorization::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Delete entities not present in incoming list
        List<PatientCaseAuthorization> toDelete = existing.stream()
                .filter(e -> e.getId() != null && !incomingIds.contains(e.getId()))
                .collect(Collectors.toList());

        if (!toDelete.isEmpty()) {
            repository.deleteAll(toDelete);
        }

        // Save new/updated entities
        List<PatientCaseAuthorization> saved = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // Return as DTOs
        return saved.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PatientCaseAuthorizationDTO> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    private PatientCaseAuthorizationDTO mapToDTO(PatientCaseAuthorization entity) {
        PatientCaseAuthorizationDTO dto = new PatientCaseAuthorizationDTO();
        dto.setId(entity.getId());
        dto.setAuthName(entity.getAuthName());
        dto.setEffectiveStart(entity.getEffectiveStart());
        dto.setEffectiveEnd(entity.getEffectiveEnd());
        dto.setAuthType(entity.getAuthType());
        dto.setAuthNumber(entity.getAuthNumber());
        dto.setInsuranceName(entity.getInsuranceName());
        dto.setInsuranceId(entity.getInsuranceId());
        return dto;
    }

    private PatientCaseAuthorization mapToEntity(PatientCaseAuthorizationDTO dto) {
        PatientCaseAuthorization entity = new PatientCaseAuthorization();

        entity.setId(dto.getId());  // important for update vs create
        entity.setAuthName(dto.getAuthName());
        entity.setEffectiveStart(dto.getEffectiveStart());
        entity.setEffectiveEnd(dto.getEffectiveEnd());
        entity.setAuthType(dto.getAuthType());
        entity.setAuthNumber(dto.getAuthNumber());
        entity.setInsuranceName(dto.getInsuranceName());
        entity.setInsuranceId(dto.getInsuranceId());

        return entity;
    }
}
