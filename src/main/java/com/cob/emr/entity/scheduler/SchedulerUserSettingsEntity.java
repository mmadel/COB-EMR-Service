package com.cob.emr.entity.scheduler;

import com.cob.emr.entity.clinic.Clinic;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "scheduler_user_settings")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class SchedulerUserSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    @Column(name = "user_uuid")
    private String user;

    @Column(name = "selected_calendars", columnDefinition = "json")
    @Type(type = "json")
    private List<Long> selectedCalendars;
}
