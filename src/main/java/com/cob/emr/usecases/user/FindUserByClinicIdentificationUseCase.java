package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.model.response.UserResponse;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.Doctor;
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

    public UserResponse getUsers(Pageable pageable, Long clinicId) throws ClinicException {
        List<Clinic> clinics = new ArrayList<>();
        getClinic(clinics, clinicId);
        Page<ClinicalUser> pages = clinicalUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClinicalUserModel> records = pages.stream()
                .filter(clinicalUser -> {
                    return !(clinicalUser instanceof DoctorUser);
                })
                .map(clinicalUser -> {
                    ClinicalUserModel user = mapper.map(clinicalUser, ClinicalUserModel.class);
                    user.setClinics(clinicalUser.getClinics().stream().map(clinic -> clinic.getId().toString()).collect(Collectors.toList()));
                    return user;
                })
                .collect(Collectors.toList());
        return UserResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(total)
                .records(records)
                .build();
    }

    public UserResponse getDoctors(Pageable pageable, Long clinicId) throws ClinicException {
        List<Clinic> clinics = new ArrayList<>();
        getClinic(clinics, clinicId);
        Page<DoctorUser> pages = doctorUserRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<ClinicalUserModel> records = pages.stream().map(doctorUser -> {
                    ClinicalUserModel user = mapper.map(doctorUser, ClinicalUserModel.class);
                    Doctor doctor = new Doctor();
                    doctor.setNpi(doctorUser.getNpi());
                    doctor.setLicence(doctorUser.getLicence());
                    doctor.setSpeciality(doctorUser.getSpeciality());
                    doctor.setCredential(doctorUser.getCredential());
                    user.setDoctor(doctor);
                    user.setClinics(doctorUser.getClinics().stream().map(clinic -> clinic.getId().toString()).collect(Collectors.toList()));
                    return user;
                })
                .collect(Collectors.toList());
        return UserResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(total)
                .records(records)
                .build();
    }

    private void getClinic(List<Clinic> clinics, Long clinicId) throws ClinicException {
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new ClinicException(HttpStatus.NOT_FOUND, ClinicException.CLINIC_NOT_FOUND, new Object[]{clinicId})));
    }
}
