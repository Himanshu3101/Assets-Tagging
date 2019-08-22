package com.assettagging.test.model.tasklocationwise;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleLocationTask {

    @SerializedName("ACTIVITYTYPE")
    @Expose
    private String aCTIVITYTYPE;
    @SerializedName("EMPID")
    @Expose
    private String eMPID;
    @SerializedName("ENDTIME")
    @Expose
    private String eNDTIME;
    @SerializedName("SCHEDULEID")
    @Expose
    private String sCHEDULEID;
    @SerializedName("STARTTIME")
    @Expose
    private String sTARTTIME;
    @SerializedName("LOCATION")
    @Expose
    private String LOCATION;

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String aCTIVITYTYPE) {
        this.LOCATION = LOCATION;
    }

    public String getACTIVITYTYPE() {
        return aCTIVITYTYPE;
    }

    public void setACTIVITYTYPE(String aCTIVITYTYPE) {
        this.aCTIVITYTYPE = aCTIVITYTYPE;
    }

    public String getEMPID() {
        return eMPID;
    }

    public void setEMPID(String eMPID) {
        this.eMPID = eMPID;
    }

    public String getENDTIME() {
        return eNDTIME;
    }

    public void setENDTIME(String eNDTIME) {
        this.eNDTIME = eNDTIME;
    }

    public String getSCHEDULEID() {
        return sCHEDULEID;
    }

    public void setSCHEDULEID(String sCHEDULEID) {
        this.sCHEDULEID = sCHEDULEID;
    }

    public String getSTARTTIME() {
        return sTARTTIME;
    }

    public void setSTARTTIME(String sTARTTIME) {
        this.sTARTTIME = sTARTTIME;
    }

}

