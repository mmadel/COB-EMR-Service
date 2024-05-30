package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.response.UserResponse;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClinicalUsersByClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ModelMapper mapper;

    public UserResponse find(Pageable pageable, Long clinicId) throws ClinicException {
        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new ClinicException(HttpStatus.NOT_FOUND, ClinicException.CLINIC_NOT_FOUND, new Object[]{clinicId})));
        Page<ClinicalEMRUser> pages = clinicalEMRUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClinicalEMRUserModel> records = pages.stream()
                .map(clinicalUser -> {
                    ClinicalEMRUserModel user = mapper.map(clinicalUser, ClinicalEMRUserModel.class);
                    user.setClinicIds(clinicalUser.getClinics().stream().map(clinic -> clinic.getId()).collect(Collectors.toList()));
                    return user;
                })
                .collect(Collectors.toList());
        return UserResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(total)
                .records(records)
                .build();
    }
    public ClericalEMRUserModel find(Long clinicId, String uuid) throws UserException {
        ClericalEMRUser clericalEMRUser = clinicalEMRUserRepository
                .findById(clinicId, uuid).orElseThrow(() -> new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_FOUND, new Object[]{}));
        return mapper.map(clericalEMRUser, ClericalEMRUserModel.class);
    }
}
