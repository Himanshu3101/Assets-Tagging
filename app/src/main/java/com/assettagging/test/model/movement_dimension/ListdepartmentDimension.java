package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListdepartmentDimension {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Value")
    @Expose
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ListdepartmentDimension() {
    }

    @Override
    public String toString() {
        return name+" - "+value;
    }
}
