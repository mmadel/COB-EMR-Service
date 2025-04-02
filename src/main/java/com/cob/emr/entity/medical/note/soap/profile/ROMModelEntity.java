package com.cob.emr.entity.medical.note.soap.profile;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "objective_rom")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class ROMModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rom_test_name")
    private String romTestName;

    @Column(name = "name")
    private String name;

    @Column(name = "objective_rom_values", columnDefinition = "json")
    @Type(type = "json")
    private Map<String, String[]> values;
}
