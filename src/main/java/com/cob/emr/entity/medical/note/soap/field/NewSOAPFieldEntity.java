package com.cob.emr.entity.medical.note.soap.field;

import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SoapFieldNoteType> noteTypes = new HashSet<>();

    // convenience methods
    public void addNoteType(MedicalNoteTypeEntity noteType, int order) {
        SoapFieldNoteType link = new SoapFieldNoteType();
        link.setField(this);
        link.setNoteType(noteType);
        link.setFieldOrder(order);
        noteTypes.add(link);
        noteType.getFields().add(link);
    }

    public void removeNoteType(MedicalNoteTypeEntity noteType) {
        noteTypes.removeIf(link -> link.getNoteType().equals(noteType));
        noteType.getFields().removeIf(link -> link.getField().equals(this));
    }
}
