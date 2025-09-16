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

    @Transactional
    public List<PatientCaseAuthorizationDTO> saveOrUpdateList(List<PatientCaseAuthorizationDTO> dtos) {
        // Collect incoming IDs
        List<Long> incomingIds = dtos.stream()
                .map(PatientCaseAuthorizationDTO::getId)
                .filter(id -> id != null)
                .collect(Collectors.toList());

        // Find all existing records
        List<PatientCaseAuthorization> existing = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());


        // Delete ones not in incoming list
        existing.stream()
                .filter(e -> !incomingIds.contains(e.getId()))
                .forEach(repository::delete);

        // Save new or updated records
        List<PatientCaseAuthorization> saved = dtos.stream()
                .map(this::mapToEntity) // ensures id is set if exists
                .map(repository::save)
                .collect(Collectors.toList());

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
