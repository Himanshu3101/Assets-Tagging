package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListSiteDimension {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("FINANCIALTAGCATEGORY")
    @Expose
    private String fINANCIALTAGCATEGORY;
    @SerializedName("Value")
    @Expose
    private String value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFINANCIALTAGCATEGORY() {
        return fINANCIALTAGCATEGORY;
    }

    public void setFINANCIALTAGCATEGORY(String fINANCIALTAGCATEGORY) {
        this.fINANCIALTAGCATEGORY = fINANCIALTAGCATEGORY;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ListSiteDimension() {
    }

    @Override
    public String toString() {
        return value+" - "+description;
    }
}
