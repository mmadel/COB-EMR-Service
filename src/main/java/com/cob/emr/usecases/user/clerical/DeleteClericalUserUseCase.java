package com.cob.emr.usecases.user.clerical;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteClericalUserUseCase {
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Transactional

    public void delete(String uuid){
        ClericalEMRUser clericalEMRUser = clericalEMRUserRepository.findByUUID(uuid).get();
        clericalEMRUser.getClinics()
                        .forEach(clinic -> {
                            clinic.removeClericalUser(clericalEMRUser);
                        });
        clericalEMRUserRepository.deleteByUuid(uuid);
    }
}
