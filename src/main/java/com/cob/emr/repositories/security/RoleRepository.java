package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}
