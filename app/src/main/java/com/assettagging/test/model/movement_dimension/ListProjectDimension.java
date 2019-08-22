package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListProjectDimension {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ProjId")
    @Expose
    private String projId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public ListProjectDimension() {
    }

    @Override
    public String toString() {
        return projId+" - "+name;
    }
}
