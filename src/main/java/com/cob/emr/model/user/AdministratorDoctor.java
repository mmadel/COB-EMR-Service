package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdministratorDoctor {
        private Long id;
    	private String userName;
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String phone;
        private String npi;
        private String password;
}
