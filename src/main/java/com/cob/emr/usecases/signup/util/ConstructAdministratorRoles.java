package com.cob.emr.usecases.signup.util;

import com.cob.emr.model.role.Role;
import com.cob.emr.model.user.EMRUseRoleScope;
import com.cob.emr.model.user.EMRUser;

import java.util.ArrayList;
import java.util.List;

public class ConstructAdministratorRoles {
    public static void construct(EMRUser administrator) {
        List<EMRUseRoleScope> emrUseRoleScopes = new ArrayList<>();
        for (int i = 0; i < Role.values.length; i++) {
            EMRUseRoleScope emrUseRoleScope = new EMRUseRoleScope();
            emrUseRoleScope.setScope("modify");
            emrUseRoleScope.setRole(Role.values[i]);
            emrUseRoleScopes.add(emrUseRoleScope);
        }
        administrator.setRoleScope(emrUseRoleScopes);
    }
}
