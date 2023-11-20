package com.cob.emr.entity.scheduler;

import com.cob.emr.entity.clinic.Clinic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity()
@Table(name = "scheduler_configuration")
@Getter
@Setter
public class SchedulerConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private Long startHour;
    @Column
    private Long endHour;
    @Column
    private Long clinicId;
}
