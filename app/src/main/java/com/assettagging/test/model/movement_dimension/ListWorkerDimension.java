package com.assettagging.test.model.movement_dimension;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IACE on 30-Nov-18.
 */

public class ListWorkerDimension {
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("PERSONNELNUMBER")
    @Expose
    private String pERSONNELNUMBER;

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getPERSONNELNUMBER() {
        return pERSONNELNUMBER;
    }

    public void setPERSONNELNUMBER(String pERSONNELNUMBER) {
        this.pERSONNELNUMBER = pERSONNELNUMBER;
    }

    public ListWorkerDimension() {
    }

    @Override
    public String toString() {
        return pERSONNELNUMBER+" - "+nAME;
    }
}
