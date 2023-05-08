package com.cob.emr.entity.appointment;

import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.StatusHistoryValueModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class AppointmentStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

    @Column
    private Long createdDate;

    @Column(name = "status_history_value", columnDefinition = "json")
    @Type(type = "json")
    private StatusHistoryValueModel statusHistoryValueModel;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
