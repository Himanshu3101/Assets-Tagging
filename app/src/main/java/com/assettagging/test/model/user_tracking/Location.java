package com.assettagging.test.model.user_tracking;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Name")
    @Expose
    private String Name;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }


    @SerializedName("LocationName")
    @Expose
    private String LocationNameOff;

    public String getLocationNameOff() {
        return LocationNameOff;
    }

    public void setLocationNameOff(String LocationNameOff) {
        this.LocationNameOff = LocationNameOff;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @SerializedName("Location")
    @Expose
    private String location;
    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }
    @SerializedName("ProjectId")
    @Expose
    private String ProjectId;
    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String ProjectId) {
        this.ProjectId = ProjectId;
    }


}

