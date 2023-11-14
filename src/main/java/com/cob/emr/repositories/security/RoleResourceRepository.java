package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.resource.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    @Query("select rs.role.name from role_resource  rs where rs.resource.name LIKE %:resource% and rs.add =true ")
    String[] findCreationAuthorizationRoles(@Param("resource")  String resource);

    @Query("select rs.role.name from role_resource  rs where rs.resource.name LIKE %:resource% and rs.view =true ")
    String[] findInquiryAuthorizationRoles(@Param("resource")  String resource);

    @Query("select rs.role.name from role_resource  rs where rs.resource.name LIKE %:resource% and rs.modify =true ")
    String[] findModificationAuthorizationRoles(@Param("resource")  String resource);

    @Query("select rs.role.name from role_resource  rs where rs.resource.name LIKE %:resource% and rs.delete =true ")
    String[] findRemovingAuthorizationRoles(@Param("resource")  String resource);
}
