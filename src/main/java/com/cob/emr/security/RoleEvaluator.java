package com.cob.emr.security;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.repositories.security.RoleResourceRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component(value = "roleEvaluator")
public class RoleEvaluator {

    public String[] findAuthorizedRoleForCreation(String resource) {
        RoleResourceRepository roleResourceRepository = BeanFactory.getBean(RoleResourceRepository.class);
        return roleResourceRepository.findCreationAuthorizationRoles(resource);
    }

    public String[] findAuthorizedRoleForInquiring(String resource) {
        RoleResourceRepository roleResourceRepository = BeanFactory.getBean(RoleResourceRepository.class);
        return roleResourceRepository.findInquiryAuthorizationRoles(resource);
    }

    public String[] findAuthorizedRoleForModification(String resource) {
        RoleResourceRepository roleResourceRepository = BeanFactory.getBean(RoleResourceRepository.class);
        return roleResourceRepository.findModificationAuthorizationRoles(resource);
    }

    public String[] findAuthorizedRoleForRemoving(String resource) {
        RoleResourceRepository roleResourceRepository = BeanFactory.getBean(RoleResourceRepository.class);
        return roleResourceRepository.findRemovingAuthorizationRoles(resource);
    }
}
