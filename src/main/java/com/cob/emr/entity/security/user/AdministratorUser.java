package com.cob.emr.entity.security.user;

import com.cob.emr.model.user.EMRUseRoleScope;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "administrator_user")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class AdministratorUser extends EMRUser{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_scope", columnDefinition = "json")
    @Type(type = "json")
    private List<EMRUseRoleScope> roleScope;
}
