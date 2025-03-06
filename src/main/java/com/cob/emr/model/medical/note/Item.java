package com.cob.emr.model.medical.note;

import java.util.ArrayList;
import java.util.List;


public class Item {
    public String fieldName;
    public String fieldType = "checkbox";
    public String changeValue = "true";
    public List<Item> dependencies = new ArrayList<>();

    public Item(String fieldName) {
        this.fieldName = fieldName;
    }
}
