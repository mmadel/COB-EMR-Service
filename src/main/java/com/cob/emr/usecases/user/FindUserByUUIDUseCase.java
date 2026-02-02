package com.cob.emr.usecases.user;

import com.cob.emr.entity.security.user.AdministratorUser;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.UserType;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.EMRUser;
import com.cob.emr.model.user.ProviderInfo;
import com.cob.emr.repositories.security.AdministratorUserRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUserByUUIDUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Autowired
    AdministratorUserRepository administratorUserRepository;

    @Autowired
    ModelMapper mapper;

    public EMRUser find(String uuid) throws UserException {
        EMRUser user = null;
        Optional<ClinicalEMRUser> clinicalEMRUser = clinicalEMRUserRepository.findById(uuid);
        Optional<ClericalEMRUser> clericalEMRUser;
        if (clinicalEMRUser.isEmpty()) {
            clericalEMRUser = clericalEMRUserRepository.findByUUID(uuid);
            if (!clericalEMRUser.isEmpty())
                user = map(clericalEMRUser.get());
        } else {
            user = map(clinicalEMRUser.get());
        }
        if (user == null) {
            Optional<AdministratorUser> administratorUser = administratorUserRepository.findById(uuid);
            if (!administratorUser.isEmpty())
                user = map(administratorUser.get());
        }
        if (user == null)
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_FOUND, new Object[]{});
        return user;

    }

    private EMRUser map(ClinicalEMRUser user) {
        EMRUser emrUser = new EMRUser();
        emrUser.setAccountName(user.getAccountName());
        emrUser.setFirstName(user.getFirstName());
        emrUser.setMiddleName(user.getMiddleName());
        emrUser.setLastName(user.getLastName());
        emrUser.setEmail(user.getEmail());
        emrUser.setUuid(user.getUuid());
        emrUser.setRoleScope(user.getRoleScope());
        emrUser.setUserType(UserType.Clinical);
        emrUser.setStatus(user.getStatus());
        emrUser.setDoctorStatus(user.getDoctorStatus());
        emrUser.setProviderInfo(ProviderInfo.builder()
                .npi(user.getNpi())
                .credential(user.getCredential())
                .license(user.getLicence())
                .speciality(user.getSpeciality())
                .build());
        return emrUser;
    }

    private EMRUser map(ClericalEMRUser user) {
        EMRUser emrUser = new EMRUser();
        emrUser.setAccountName(user.getAccountName());
        emrUser.setFirstName(user.getFirstName());
        emrUser.setMiddleName(user.getMiddleName());
        emrUser.setLastName(user.getLastName());
        emrUser.setEmail(user.getEmail());
        emrUser.setUuid(user.getUuid());
        emrUser.setRoleScope(user.getRoleScope());
        emrUser.setUserType(UserType.Clerical);
        emrUser.setStatus(user.getStatus());
        return emrUser;
    }

    private EMRUser map(AdministratorUser user) {
        EMRUser emrUser = new EMRUser();
        emrUser.setAccountName(user.getAccountName());
        emrUser.setFirstName(user.getFirstName());
        emrUser.setMiddleName(user.getMiddleName());
        emrUser.setLastName(user.getLastName());
        emrUser.setEmail(user.getEmail());
        emrUser.setUuid(user.getUuid());
        emrUser.setRoleScope(user.getRoleScope());
        return emrUser;
    }
}
