package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListToAccount {
    @SerializedName("MainAccountId")
    @Expose
    private String mainAccountId;
    @SerializedName("OffSetAccount")
    @Expose
    private String offSetAccount;

    public String getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(String mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    public String getOffSetAccount() {
        return offSetAccount;
    }

    public void setOffSetAccount(String offSetAccount) {
        this.offSetAccount = offSetAccount;
    }
}
