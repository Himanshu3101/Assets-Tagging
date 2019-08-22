package com.assettagging.test.model.asset_detai;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDisposalList {

@SerializedName("Date")
@Expose
private String date;
@SerializedName("Description")
@Expose
private String description;
@SerializedName("DisposalScheduleHeaderID")
@Expose
private String disposalScheduleHeaderID;
@SerializedName("Status")
@Expose
private String status;

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getDisposalScheduleHeaderID() {
return disposalScheduleHeaderID;
}

public void setDisposalScheduleHeaderID(String disposalScheduleHeaderID) {
this.disposalScheduleHeaderID = disposalScheduleHeaderID;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}

