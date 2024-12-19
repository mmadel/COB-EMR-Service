package com.cob.emr.entity.calendar;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
}
