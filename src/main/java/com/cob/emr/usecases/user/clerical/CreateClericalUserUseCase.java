package com.cob.emr.usecases.user.clerical;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreateClericalUserUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;

    public void create(ClericalEMRUserModel model) {
        ClericalEMRUser clericalEMRUser = mapper.map(model, ClericalEMRUser.class);
        model.getClinics()
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clericalEMRUser.addClinic(clinic));
        clericalEMRUserRepository.save(clericalEMRUser);
    }
}
