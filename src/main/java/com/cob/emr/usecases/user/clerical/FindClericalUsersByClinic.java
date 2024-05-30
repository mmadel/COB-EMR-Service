package com.cob.emr.usecases.user.clerical;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.model.response.ClericalUserResponse;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClericalUsersByClinic {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Autowired
    ModelMapper mapper;
    public ClericalUserResponse find(Pageable pageable, Long clinicId) throws ClinicException {
        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new ClinicException(HttpStatus.NOT_FOUND, ClinicException.CLINIC_NOT_FOUND, new Object[]{clinicId})));
        Page<ClericalEMRUser> pages = clericalEMRUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClericalEMRUserModel> records = pages.stream()
                .map(clinicalUser -> {
                    ClericalEMRUserModel user = mapper.map(clinicalUser, ClericalEMRUserModel.class);
                    user.setClinicIds(clinicalUser.getClinics().stream().map(clinic -> clinic.getId()).collect(Collectors.toList()));
                    return user;
                })
                .collect(Collectors.toList());
        return ClericalUserResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(total)
                .records(records)
                .build();
    }
}
