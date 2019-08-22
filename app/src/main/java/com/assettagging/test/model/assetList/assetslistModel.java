package com.assettagging.test.model.assetList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by IACE on 29-Nov-18.
 */

public class assetslistModel {

    @SerializedName("DisposalAssetList")
    @Expose
    private List<disposalassetlist> disposalAssetList = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<disposalassetlist> getDisposalAssetList() {
        return disposalAssetList;
    }

    public void setDisposalAssetList(List<disposalassetlist> disposalAssetList) {
        this.disposalAssetList = disposalAssetList;
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
