package com.cob.emr.entity.medical.note.soap.field;

import com.cob.emr.entity.medical.note.soap.profile.ObjectiveProfileEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "objective_profile_soap_field",
        indexes = {
                @Index(name = "idx_objprofile_soapfield_profile", columnList = "objective_profile_id"),
                @Index(name = "idx_objprofile_soapfield_field", columnList = "soap_field_id")
        })
@Setter
@Getter
public class ObjectiveProfileSOAPField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objective_profile_id")
    private ObjectiveProfileEntity objectiveProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soap_field_id")
    private NewSOAPFieldEntity soapField;

    // Additional fields if needed
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
