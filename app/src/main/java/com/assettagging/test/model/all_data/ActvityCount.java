package com.assettagging.test.model.all_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActvityCount {

    @SerializedName("CompInspectionCount")
    @Expose
    private String compInspectionCount;
    @SerializedName("CompMovementCount")
    @Expose
    private String compMovementCount;
    @SerializedName("CompTaggingCount")
    @Expose
    private String compTaggingCount;
    @SerializedName("OngoingInspectionCount")
    @Expose
    private String ongoingInspectionCount;
    @SerializedName("OngoingMovementCount")
    @Expose
    private String ongoingMovementCount;
    @SerializedName("OngoingTaggingCount")
    @Expose
    private String ongoingTaggingCount;
    @SerializedName("UpcomeInspectionCount")
    @Expose
    private String upcomeInspectionCount;
    @SerializedName("UpcomeMovementCount")
    @Expose
    private String upcomeMovementCount;
    @SerializedName("UpcomeTaggingCount")
    @Expose
    private String upcomeTaggingCount;

    @SerializedName("DisposalCreateCount")
    @Expose
    private String DisposalCreateCount;

    @SerializedName("DisposalSubmitCount")
    @Expose
    private String DisposalSubmitCount;

    public String getDisposalSubmitCount() {
        return DisposalSubmitCount;
    }

    public void setDisposalSubmitCount(String DisposalSubmitCount) {
        this.DisposalSubmitCount = DisposalSubmitCount;
    }

    public String getDisposalCreateCount() {
        return DisposalCreateCount;
    }

    public void setDisposalCreateCount(String DisposalCreateCount) {
        this.DisposalCreateCount = DisposalCreateCount;
    }


    public String getCompInspectionCount() {
        return compInspectionCount;
    }

    public void setCompInspectionCount(String compInspectionCount) {
        this.compInspectionCount = compInspectionCount;
    }

    public String getCompMovementCount() {
        return compMovementCount;
    }

    public void setCompMovementCount(String compMovementCount) {
        this.compMovementCount = compMovementCount;
    }

    public String getCompTaggingCount() {
        return compTaggingCount;
    }

    public void setCompTaggingCount(String compTaggingCount) {
        this.compTaggingCount = compTaggingCount;
    }

    public String getOngoingInspectionCount() {
        return ongoingInspectionCount;
    }

    public void setOngoingInspectionCount(String ongoingInspectionCount) {
        this.ongoingInspectionCount = ongoingInspectionCount;
    }

    public String getOngoingMovementCount() {
        return ongoingMovementCount;
    }

    public void setOngoingMovementCount(String ongoingMovementCount) {
        this.ongoingMovementCount = ongoingMovementCount;
    }

    public String getOngoingTaggingCount() {
        return ongoingTaggingCount;
    }

    public void setOngoingTaggingCount(String ongoingTaggingCount) {
        this.ongoingTaggingCount = ongoingTaggingCount;
    }

    public String getUpcomeInspectionCount() {
        return upcomeInspectionCount;
    }

    public void setUpcomeInspectionCount(String upcomeInspectionCount) {
        this.upcomeInspectionCount = upcomeInspectionCount;
    }

    public String getUpcomeMovementCount() {
        return upcomeMovementCount;
    }

    public void setUpcomeMovementCount(String upcomeMovementCount) {
        this.upcomeMovementCount = upcomeMovementCount;
    }

    public String getUpcomeTaggingCount() {
        return upcomeTaggingCount;
    }

    public void setUpcomeTaggingCount(String upcomeTaggingCount) {
        this.upcomeTaggingCount = upcomeTaggingCount;
    }

}