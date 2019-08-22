package com.assettagging.test.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetUser {

@SerializedName("SendEmailResult")
@Expose
private SendEmailResult sendEmailResult;

public SendEmailResult getSendEmailResult() {
return sendEmailResult;
}

public void setSendEmailResult(SendEmailResult sendEmailResult) {
this.sendEmailResult = sendEmailResult;
}

}
