package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.Doctor;
import com.cob.emr.repositories.security.DoctorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindAllDoctorsUseCase {
    @Autowired
    DoctorUserRepository doctorUserRepository;

    public List<ClinicalUserModel> find() {
        List<ClinicalUserModel> models = new ArrayList<>();
        doctorUserRepository.findAll()
                .stream().filter(doctorUser -> doctorUser.getClinics().isEmpty())
                .forEach(doctorUser -> {
                    ClinicalUserModel clinicalUserModel = new ClinicalUserModel();
                    clinicalUserModel.setUserName(doctorUser.getUserName());
                    clinicalUserModel.setFirstName(doctorUser.getFirstName());
                    clinicalUserModel.setMiddleName(doctorUser.getMiddleName());
                    clinicalUserModel.setLastName(doctorUser.getLastName());
                    clinicalUserModel.setEmail(doctorUser.getEmail());
                    clinicalUserModel.setUuid(doctorUser.getUuid());
                    clinicalUserModel.setId(doctorUser.getId());
                    Doctor doctor = new Doctor();
                    doctor.setNpi(doctorUser.getNpi());
                    doctor.setLicence(doctorUser.getLicence());
                    doctor.setSpeciality(doctorUser.getSpeciality());
                    doctor.setCredential(doctorUser.getCredential());
                    clinicalUserModel.setDoctor(doctor);
                    models.add(clinicalUserModel);
                });
        return models;
    }
}
