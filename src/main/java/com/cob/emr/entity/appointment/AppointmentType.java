package com.cob.emr.entity.appointment;

import com.cob.emr.entity.clinic.Clinic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;
    @Column
    private String color;

    @Column
    private Long duration;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
