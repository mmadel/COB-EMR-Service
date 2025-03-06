package com.cob.emr.entity.medical.note;

import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.medical.note.SOAPField;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "soap_field")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class SOAPFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_value", columnDefinition = "json")
    @Type(type = "json")
    private SOAPField value;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_display_name")
    private String fieldDisplayName;

}
