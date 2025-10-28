package com.cob.emr.entity.medical.note.test;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "omt_test_values")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class OMTTestValuesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="test_name")
    private String testName;

    @Column(name = "test_value", columnDefinition = "json")
    @Type(type = "json")
    private Object testValues;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_note_id", referencedColumnName = "id")
    private MedicalNote medicalNote;
}
