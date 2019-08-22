package com.assettagging.test.model.asset_disposal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisposalWiseDataList {

    @SerializedName("AssetId")
    @Expose
    private String aSSETID;
    @SerializedName("Barcode")
    @Expose
    private String Barcode;
    @SerializedName("Location")
    @Expose
    private String lOCATION;
    @SerializedName("Name")
    @Expose
    private String nAME;
    @SerializedName("SCHEDULEID")
    @Expose
    private String SCHEDULEID;
    @SerializedName("Status")
    @Expose
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSCHEDULEID() {
        return SCHEDULEID;
    }

    public void setSCHEDULEID(String SCHEDULEID) {
        this.SCHEDULEID = SCHEDULEID;
    }

    public String getASSETID() {
        return aSSETID;
    }

    public void setASSETID(String aSSETID) {
        this.aSSETID = aSSETID;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }

    public String getLOCATION() {
        return lOCATION;
    }

    public void setLOCATION(String lOCATION) {
        this.lOCATION = lOCATION;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

}
