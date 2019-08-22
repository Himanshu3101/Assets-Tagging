package com.assettagging.test.model.asset_disposal;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetDisposal {

@SerializedName("CreatedDisposalList")
@Expose
private List<CreatedDisposalList> createdDisposalList = null;
@SerializedName("SubmittedDisposalList")
@Expose
private List<CreatedDisposalList> submittedDisposalList = null;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;

public List<CreatedDisposalList> getCreatedDisposalList() {
return createdDisposalList;
}

public void setCreatedDisposalList(List<CreatedDisposalList> createdDisposalList) {
this.createdDisposalList = createdDisposalList;
}

public List<CreatedDisposalList> getSubmittedDisposalList() {
return submittedDisposalList;
}

public void setSubmittedDisposalList(List<CreatedDisposalList> submittedDisposalList) {
this.submittedDisposalList = submittedDisposalList;
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

