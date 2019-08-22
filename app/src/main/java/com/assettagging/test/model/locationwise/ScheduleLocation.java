package com.assettagging.test.model.locationwise;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleLocation implements Comparable<ScheduleLocation>{

    @SerializedName("EMPID")
    @Expose
    private String eMPID;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("SCHEDULEID")
    @Expose
    private String sCHEDULEID;
    @SerializedName("LocationName")
    @Expose
    private String LocationName;

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String LocationName) {
        this.LocationName = LocationName;
    }

    public String getEMPID() {
        return eMPID;
    }

    public void setEMPID(String eMPID) {
        this.eMPID = eMPID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSCHEDULEID() {
        return sCHEDULEID;
    }

    public void setSCHEDULEID(String sCHEDULEID) {
        this.sCHEDULEID = sCHEDULEID;
    }

    @Override
    public int compareTo(@NonNull ScheduleLocation o) {
        return getEMPID().compareTo(o.getEMPID());

    }
}
