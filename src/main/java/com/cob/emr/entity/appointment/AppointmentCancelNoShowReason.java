package com.cob.emr.entity.appointment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "appointment_cancel_no_show_reason")
@Setter
@Getter
public class AppointmentCancelNoShowReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String reason;
    @Column
    private Long reasonDate;
    @Column
    private String comment;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;
}
