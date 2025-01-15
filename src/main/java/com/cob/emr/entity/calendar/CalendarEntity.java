package com.cob.emr.entity.calendar;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.EMRUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "calendar")
public class CalendarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "public")
    private Boolean isPublic;
    @Column(name = "attached")
    private boolean attached;
    @Column(name = "user_uuid")
    private String createdBy;
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
    private List<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            if (appointments == null)
                appointments = new ArrayList<>();
            appointments.add(appointment);
        }
    }

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
