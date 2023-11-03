package com.cob.emr.service.security;

import com.cob.emr.model.security.SecurityAuthority;
import com.cob.emr.repositories.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityFinderService {
    @Autowired
    RoleRepository roleRepository;

    public List<SecurityAuthority> findRoleAuthorities() {
        List<SecurityAuthority> securityAuthorities = new ArrayList<>();
        roleRepository.findAll().forEach(role -> {
            role.getRoleResources().forEach(resources -> {
                SecurityAuthority securityAuthority = new SecurityAuthority();
                securityAuthority.setRoleName(role.getName());
                securityAuthority.setResource(resources.getResource().getName());
                if (resources.getAdd())
                    securityAuthority.getAvailableMethods().add(HttpMethod.POST);
                if (resources.getView())
                    securityAuthority.getAvailableMethods().add(HttpMethod.GET);
                if (resources.getModify())
                    securityAuthority.getAvailableMethods().add(HttpMethod.PUT);
                if (resources.getDelete())
                    securityAuthority.getAvailableMethods().add(HttpMethod.DELETE);
                securityAuthorities.add(securityAuthority);
            });
        });
        return securityAuthorities;
    }

}
