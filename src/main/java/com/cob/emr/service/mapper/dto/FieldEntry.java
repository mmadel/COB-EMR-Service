package com.cob.emr.service.mapper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldEntry {
    private String id;
    private String uuid;
    private String display;
    private List<FieldEntry> children;

    public FieldEntry() {}

    public FieldEntry(String id, String uuid, String display) {
        this.id = id;
        this.uuid = uuid;
        this.display = display;
    }

    public FieldEntry(String id, String uuid, String display, List<FieldEntry> children) {
        this.id = id;
        this.uuid = uuid;
        this.display = display;
        this.children = children;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getDisplay() { return display; }
    public void setDisplay(String display) { this.display = display; }

    public List<FieldEntry> getChildren() { return children; }
    public void setChildren(List<FieldEntry> children) { this.children = children; }
}
