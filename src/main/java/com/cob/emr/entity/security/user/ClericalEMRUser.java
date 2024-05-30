package com.cob.emr.entity.security.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.user.EMRUseRoleScope;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clerical_emr_user")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class ClericalEMRUser extends EMRUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "role_scope", columnDefinition = "json")
    @Type(type = "json")
    private List<EMRUseRoleScope> roleScope;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "clericalUsers")
    private Set<Clinic> clinics = new HashSet<>();

    public void addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.getClericalUsers().add(this);
    }

    public void removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.getClericalUsers().remove(this);
    }
}
