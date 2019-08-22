package com.assettagging.test.model.asset_disposal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_LocationToAssetGroupWiseList {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("Name")
@Expose
private String name;

    public String getId() {
        return id;
    }

    public void setId(String location) {
        this.id = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String locationName) {
        this.name = locationName;
    }

}

