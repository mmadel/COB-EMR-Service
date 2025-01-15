package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class CalendarModel {
    private Long id;
    private String name;
    private String createdBy;
    private Boolean isPublic;
    private Boolean attached;
    private String user;
    private Long clinicId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarModel that = (CalendarModel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
