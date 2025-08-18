package com.cob.emr.entity.medical.note.soap.field;

import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "new_soap_field")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class NewSOAPFieldEntity {
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

    @Column(name = "field_order")
    private Long inputOrder;

}
