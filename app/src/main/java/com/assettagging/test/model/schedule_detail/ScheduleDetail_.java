package com.assettagging.test.model.schedule_detail;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScheduleDetail_ implements Comparable<ScheduleDetail_>, Serializable {

    @SerializedName("ACTIVITYTYPE")
    @Expose
    private String aCTIVITYTYPE;
    @SerializedName("ASSETID")
    @Expose
    private String aSSETID;
    @SerializedName("BarCode")
    @Expose
    private String barCode;
    @SerializedName("EMPNAME")
    @Expose
    private String eMPNAME;
    @SerializedName("EmpID")
    @Expose
    private String empID;
    @SerializedName("LOCATION")
    @Expose
    private String lOCATION;
    @SerializedName("SCHEDULEID")
    @Expose
    private String sCHEDULEID;
    @SerializedName("TRACKING")
    @Expose
    private String tRACKING;
    @SerializedName("ImagePath")
    @Expose
    private String ImagePath;
    @SerializedName("STARTTIME")
    @Expose
    private String STARTTIME;
    @SerializedName("ENDTIME")
    @Expose
    private String ENDTIME;
    @SerializedName("BLOB")
    @Expose
    private String BLOB;
    @SerializedName("BLOB_IMAGE")
    @Expose
    private byte[] BLOB_IMAGE;
    @SerializedName("CurentStatus")
    @Expose
    private String CurentStatus;
    @SerializedName("ITEMS")
    @Expose
    private String ITEMS;
    @SerializedName("MovementFlag")
    @Expose
    private String MovementFlag;

    public String getMovementFlag() {
        return MovementFlag;
    }

    public void setMovementFlag(String MovementFlag) {
        this.MovementFlag = MovementFlag;
    }

    public String getITEMS() {
        return ITEMS;
    }

    public void setITEMS(String ITEMS) {
        this.ITEMS = ITEMS;
    }


    public String getCurentStatus() {
        return CurentStatus;
    }

    public void setCurentStatus(String CurentStatus) {
        this.CurentStatus = CurentStatus;
    }

    public String getBLOB() {
        return BLOB;
    }

    public void setBLOB(String BLOB) {
        this.BLOB = BLOB;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }


    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public String getACTIVITYTYPE() {
        return aCTIVITYTYPE;
    }

    public void setACTIVITYTYPE(String aCTIVITYTYPE) {
        this.aCTIVITYTYPE = aCTIVITYTYPE;
    }

    public String getASSETID() {
        return aSSETID;
    }

    public void setASSETID(String aSSETID) {
        this.aSSETID = aSSETID;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getEMPNAME() {
        return eMPNAME;
    }

    public void setEMPNAME(String eMPNAME) {
        this.eMPNAME = eMPNAME;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getLOCATION() {
        return lOCATION;
    }

    public void setLOCATION(String lOCATION) {
        this.lOCATION = lOCATION;
    }

    public String getSCHEDULEID() {
        return sCHEDULEID;
    }

    public void setSCHEDULEID(String sCHEDULEID) {
        this.sCHEDULEID = sCHEDULEID;
    }

    public String getTRACKING() {
        return tRACKING;
    }

    public void setTRACKING(String tRACKING) {
        this.tRACKING = tRACKING;
    }

    public byte[] getBLOB_IMAGE() {
        return BLOB_IMAGE;
    }

    public void setBLOB_IMAGE(byte[] BLOB_IMAGE) {
        this.BLOB_IMAGE = BLOB_IMAGE;
    }


    private String DeptDimensnValue;

    private String WorkerDimensnValue;

    private String SiteDimensnValue;

    public String getDepartment() {
        return DeptDimensnValue;
    }

    public void setDepartment(String department) {
        this.DeptDimensnValue = department;
    }

    public String getWorker() {
        return WorkerDimensnValue;
    }

    public void setWorker(String worker) {
        WorkerDimensnValue = worker;
    }

    public String getSite() {
        return SiteDimensnValue;
    }

    public void setSite(String site) {
        SiteDimensnValue = site;
    }

    public String getProject() {
        return ProjDimensnValue;
    }

    public void setProject(String proj) {
        ProjDimensnValue = proj;
    }

    public String getCostcenter() {
        return CostCenterDimensnValue;
    }

    public void setCostcenter(String costcenter) {
        CostCenterDimensnValue = costcenter;
    }

    public String getAccount() {
        return FromAccount;
    }

    public void setAccount(String account) {
        FromAccount = account;
    }

    private String ProjDimensnValue;

    private String CostCenterDimensnValue;

    private String FromAccount;

    public String getOffset() {
        return ToAccount;
    }

    public void setOffset(String offset) {
        ToAccount = offset;
    }

    private String ToAccount;


    @Override
    public int compareTo(@NonNull ScheduleDetail_ o) {
        return 0;
    }


}
