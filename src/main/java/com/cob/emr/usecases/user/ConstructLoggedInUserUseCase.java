package com.cob.emr.usecases.user;

import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.user.EMRUser;
import com.cob.emr.model.user.LoggedInUser;
import com.cob.emr.usecases.clinic.FindClinicsForUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConstructLoggedInUserUseCase {
    @Autowired
    FindUserByUUIDUseCase findUserByUUIDUseCase;
    @Autowired
    FindClinicsForUserUseCase findClinicsForUserUseCase;

    public LoggedInUser construct(String uuid) throws UserException {
        EMRUser emrUser = findUserByUUIDUseCase.find(uuid);
        if (DoctorStatus.DOCTOR_PENDING.equals(emrUser.getDoctorStatus())) {
            throw new UserException(HttpStatus.FORBIDDEN, UserException.DOCTOR_ACCOUNT_PENDING, new Object[]{});
        }
        List<ClinicModel> clinics = findClinicsForUserUseCase.find(uuid);
        Long organizationId = null;
        if (clinics != null && !clinics.isEmpty()) {
            organizationId = clinics.stream().findFirst().get().getOrganizationId();
        }
        LoggedInUser loggedInUser = LoggedInUser.builder()
                .uuid(uuid)
                .userName(emrUser.getAccountName())
                .email(emrUser.getEmail())
                .firstName(emrUser.getFirstName())
                .lastName(emrUser.getLastName())
                .userRoleScope(emrUser.getRoleScope())
                .clinics(clinics)
                .providerInfo(emrUser.getProviderInfo())
                .doctorStatus(emrUser.getDoctorStatus())
                .organizationId(organizationId)
                .build();
        return loggedInUser;
    }
}
