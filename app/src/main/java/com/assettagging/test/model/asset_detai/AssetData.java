package com.assettagging.test.model.asset_detai;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetData {

    @SerializedName("BarcodeWiseDataList")
    @Expose
    private List<BarcodeWiseDataList> barcodeWiseDataList = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("IsExist")
    @Expose
    private String IsExist;
    @SerializedName("ProjId")
    @Expose
    private String ProjId;
    public String getProjId() {
        return ProjId;
    }

    public void setProjId(String ProjId) {
        this.ProjId = ProjId;
    }


    @SerializedName("ProjectName")
    @Expose
    private String ProjectName;
    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }



    public String getIsExist() {
        return IsExist;
    }

    public void setIsExist(String IsExist) {
        this.IsExist = IsExist;
    }

    public List<BarcodeWiseDataList> getBarcodeWiseDataList() {
        return barcodeWiseDataList;
    }

    public void setBarcodeWiseDataList(List<BarcodeWiseDataList> barcodeWiseDataList) {
        this.barcodeWiseDataList = barcodeWiseDataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

