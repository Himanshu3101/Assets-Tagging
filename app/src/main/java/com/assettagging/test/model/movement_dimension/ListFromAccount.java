package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListFromAccount {
    @SerializedName("MainAccount")
    @Expose
    private String mainAccount;
    @SerializedName("MainAccountId")
    @Expose
    private String mainAccountId;

    public String getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(String mainAccount) {
        this.mainAccount = mainAccount;
    }

    public String getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(String mainAccountId) {
        this.mainAccountId = mainAccountId;
    }
}
