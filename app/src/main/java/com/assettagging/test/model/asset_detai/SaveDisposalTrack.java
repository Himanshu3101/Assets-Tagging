package com.assettagging.test.model.asset_detai;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveDisposalTrack {

@SerializedName("GetDisposalList")
@Expose
private List<GetDisposalList> getDisposalList = null;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;

public List<GetDisposalList> getGetDisposalList() {
return getDisposalList;
}

public void setGetDisposalList(List<GetDisposalList> getDisposalList) {
this.getDisposalList = getDisposalList;
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
