package com.cob.emr.entity.clinic;

import com.cob.emr.model.patient.AddressModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @Column(name = "clinic_address", columnDefinition = "json")
    @Type(type = "json")
    private AddressModel address;


}
