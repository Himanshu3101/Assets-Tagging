package com.assettagging.test.model.assetList;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 29-Nov-18.
 */

public class disposalassetlist implements Comparable<disposalassetlist>{
    @SerializedName("AssetGroup")
    @Expose
    private String assetGroup;
    @SerializedName("AssetId")
    @Expose
    private String assetId;
    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("ProjectId")
    @Expose
    private String ProjectId;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String ProjectId) {
        this.ProjectId = ProjectId;
    }




    @SerializedName("Status")
    @Expose
    private String status;

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(@NonNull disposalassetlist o) {
        return 0;
    }
}
