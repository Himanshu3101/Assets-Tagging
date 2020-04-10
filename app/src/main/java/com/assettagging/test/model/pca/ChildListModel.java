package com.assettagging.test.model.pca;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildListModel {
    @SerializedName("CSerial No.")
    @Expose
    private String cserialno;
    @SerializedName("CAsset No.")
    @Expose
    private String cAssetno;

    @SerializedName("CDescrption")
    @Expose
    private String cdescp;

    public ChildListModel(String cserialno, String cAssetno, String cdescp) {
        this.cserialno = cserialno;
        this.cAssetno = cAssetno;
        this.cdescp = cdescp;
    }

    public String getCserialno() {
        return cserialno;
    }

    public void setCserialno(String cserialno) {
        this.cserialno = cserialno;
    }

    public String getcAssetno() {
        return cAssetno;
    }

    public void setcAssetno(String cAssetno) {
        this.cAssetno = cAssetno;
    }

    public String getCdescp() {
        return cdescp;
    }

    public void setCdescp(String cdescp) {
        this.cdescp = cdescp;
    }
}
