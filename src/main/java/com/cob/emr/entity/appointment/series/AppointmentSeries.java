package com.cob.emr.entity.appointment.series;

import com.cob.emr.model.appointment.AppointmentRepetitionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class AppointmentSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_type")
    private AppointmentRepetitionType repetitionType;

    @Column
    private Integer seriesId;
    @Column(name = "step")
    private Integer step;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "extra_info")
    private String extra;
}
