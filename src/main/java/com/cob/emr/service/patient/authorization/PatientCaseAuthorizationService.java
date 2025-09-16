package com.cob.emr.service.patient.authorization;


import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.model.patient.authorization.PatientCaseAuthorizationDTO;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PatientCaseAuthorizationService {

    private final PatientCaseAuthorizationRepository repository;

    public PatientCaseAuthorizationService(PatientCaseAuthorizationRepository repository) {
        this.repository = repository;
    }

    public PatientCaseAuthorizationDTO create(PatientCaseAuthorizationDTO dto) {
        PatientCaseAuthorization entity = toEntity(dto);
        PatientCaseAuthorization saved = repository.save(entity);
        return toDTO(saved);
    }

    public PatientCaseAuthorizationDTO update(Long id, PatientCaseAuthorizationDTO dto) {
        PatientCaseAuthorization existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Authorization not found"));
        // update fields
        existing.setAuthName(dto.getAuthName());
        existing.setAuthType(dto.getAuthType());
        existing.setAuthNumber(dto.getAuthNumber());
        existing.setEffectiveStart(dto.getEffectiveStart());
        existing.setEffectiveEnd(dto.getEffectiveEnd());
        existing.setInsuranceName(dto.getInsuranceName());
        existing.setInsuranceId(dto.getInsuranceId());
        PatientCaseAuthorization saved = repository.save(existing);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<PatientCaseAuthorizationDTO> listAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Mapping helpers
    private PatientCaseAuthorizationDTO toDTO(PatientCaseAuthorization e) {
        PatientCaseAuthorizationDTO d = new PatientCaseAuthorizationDTO();
        d.setId(e.getId());
        d.setAuthName(e.getAuthName());
        d.setAuthType(e.getAuthType());
        d.setAuthNumber(e.getAuthNumber());
        d.setEffectiveStart(e.getEffectiveStart());
        d.setEffectiveEnd(e.getEffectiveEnd());
        d.setInsuranceName(e.getInsuranceName());
        d.setInsuranceId(e.getInsuranceId());
        return d;
    }

    private PatientCaseAuthorization toEntity(PatientCaseAuthorizationDTO d) {
        PatientCaseAuthorization e = new PatientCaseAuthorization();
        e.setAuthName(d.getAuthName());
        e.setAuthType(d.getAuthType());
        e.setAuthNumber(d.getAuthNumber());
        e.setEffectiveStart(d.getEffectiveStart());
        e.setEffectiveEnd(d.getEffectiveEnd());
        e.setInsuranceName(d.getInsuranceName());
        e.setInsuranceId(d.getInsuranceId());
        return e;
    }
}
