package com.assettagging.test.model.asset_disposal;

import android.support.annotation.NonNull;

import com.assettagging.test.view.assetdisposer.yet_to_submit.YetToSubmitDisposerAdapter;
import com.assettagging.test.view.assetdisposer.yet_to_submit.YetToSubmitDisposerFragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedDisposalList implements Comparable<CreatedDisposalList> {

    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DisposalDate")
    @Expose
    private String disposalDate;
    @SerializedName("DisposalScheduleHeaderId")
    @Expose
    private String disposalScheduleHeaderId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getDisposalScheduleHeaderId() {
        return disposalScheduleHeaderId;
    }

    public void setDisposalScheduleHeaderId(String disposalScheduleHeaderId) {
        this.disposalScheduleHeaderId = disposalScheduleHeaderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int compareTo(@NonNull CreatedDisposalList o) {
        if (YetToSubmitDisposerFragment.getInstance() != null) {
            if (YetToSubmitDisposerAdapter.sort == false) {
                return getDisposalDate().compareTo(o.getDisposalDate());
            } else {
                return o.getDisposalDate().compareTo(getDisposalDate());
            }
        } else {
            return getDisposalDate().compareTo(o.getDisposalDate());

        }

    }
}
