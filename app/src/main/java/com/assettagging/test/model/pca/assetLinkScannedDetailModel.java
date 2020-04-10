package com.assettagging.test.model.pca;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class assetLinkScannedDetailModel {
    @SerializedName("isChild")
    @Expose
    private String ischild;
    @SerializedName("Serial No.")
    @Expose
    private String serialno;
    @SerializedName("Asset No.")
    @Expose
    private String Assetno;
    @SerializedName("Tag")
    @Expose
    private String tag;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Project")
    @Expose
    private String project;
    @SerializedName("TechInfo")
    @Expose
    private String TechInfo;
    @SerializedName("Descrption")
    @Expose
    private String descp;
    @SerializedName("ChildList")
    @Expose
    private List<ChildListModel> childListModelList;
    @SerializedName("PSerial No.")
    @Expose
    private String pserialno;
    @SerializedName("PAsset No.")
    @Expose
    private String pAssetno;

    @SerializedName("PDescrption")
    @Expose
    private String pdescp;

    public String getIschild() {
        return ischild;
    }

    public void setIschild(String ischild) {
        this.ischild = ischild;
    }

    //if ischild is 0
    public assetLinkScannedDetailModel(String serialno, String assetno, String tag,
                                       String location, String project, String techInfo, String descp,
                                       List<ChildListModel> childListModelList,
                                       String ischild) {
        this.serialno = serialno;
        Assetno = assetno;
        this.tag = tag;
        this.location = location;
        this.project = project;
        TechInfo = techInfo;
        this.descp = descp;
        this.childListModelList = childListModelList;
        this.ischild=ischild;
    }
//if ischild is 1
    public assetLinkScannedDetailModel(String serialno, String assetno, String tag, String location, String project,
                                       String techInfo, String descp, String pserialno, String pAssetno, String pdescp, String ischild) {
        this.serialno = serialno;
        Assetno = assetno;
        this.tag = tag;
        this.location = location;
        this.project = project;
        TechInfo = techInfo;
        this.ischild=ischild;
        this.descp = descp;
        this.pserialno = pserialno;
        this.pAssetno = pAssetno;
        this.pdescp = pdescp;
    }

    public List<ChildListModel> getChildListModelList() {
        return childListModelList;
    }

    public void setChildListModelList(List<ChildListModel> childListModelList) {
        this.childListModelList = childListModelList;
    }

    public String getPserialno() {
        return pserialno;
    }

    public void setPserialno(String pserialno) {
        this.pserialno = pserialno;
    }

    public String getpAssetno() {
        return pAssetno;
    }

    public void setpAssetno(String pAssetno) {
        this.pAssetno = pAssetno;
    }

    public String getPdescp() {
        return pdescp;
    }

    public void setPdescp(String pdescp) {
        this.pdescp = pdescp;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getAssetno() {
        return Assetno;
    }

    public void setAssetno(String assetno) {
        Assetno = assetno;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTechInfo() {
        return TechInfo;
    }

    public void setTechInfo(String techInfo) {
        TechInfo = techInfo;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
