package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "initial_evaluation")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class InitialEvaluation extends MedicalNote {
}
