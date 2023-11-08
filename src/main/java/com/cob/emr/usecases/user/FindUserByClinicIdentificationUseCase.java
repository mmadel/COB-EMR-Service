package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.model.response.UserResponse;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
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
public class FindUserByClinicIdentificationUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalUserRepository clinicalUserRepository;
    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Autowired
    ModelMapper mapper;

    public UserResponse find(Pageable pageable, Long clinicId) throws ClinicException {
        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new ClinicException(HttpStatus.NOT_FOUND, ClinicException.CLINIC_NOT_FOUND, new Object[]{clinicId})));
        Page<ClinicalUser> pages = clinicalUserRepository.findByClinicsIn(pageable, clinics);
        UserResponse userResponse = new UserResponse();
        getUsers(pageable, clinics, userResponse);
        getDoctors(pageable, clinics, userResponse);
        return userResponse;
    }

    private void getUsers(Pageable pageable, List<Clinic> clinics, UserResponse userResponse) {
        Page<ClinicalUser> pages = clinicalUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClinicalUserModel> records = pages.stream().map(clinicalUser -> mapper.map(clinicalUser, ClinicalUserModel.class))
                .collect(Collectors.toList());
        userResponse.setNumber_of_matching_records(total);
        userResponse.setNumber_of_records(records.size());
        userResponse.getRecords().addAll(records);
    }

    private void getDoctors(Pageable pageable, List<Clinic> clinics, UserResponse userResponse) {
        Page<DoctorUser> pages = doctorUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClinicalUserModel> records = pages.stream().map(doctorUser -> mapper.map(doctorUser, ClinicalUserModel.class))
                .collect(Collectors.toList());
        userResponse.setNumber_of_matching_records(userResponse.getNumber_of_matching_records()
                + total);
        userResponse.setNumber_of_records(userResponse.getNumber_of_records() + records.size());
        userResponse.getRecords().addAll(records);
    }
}
