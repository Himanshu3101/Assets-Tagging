package com.assettagging.test.model.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {

@SerializedName("Bank")
@Expose
private Object bank;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;
@SerializedName("userId")
@Expose
private String userId;
@SerializedName("userName")
@Expose
private Object userName;

public Object getBank() {
return bank;
}

public void setBank(Object bank) {
this.bank = bank;
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

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public Object getUserName() {
return userName;
}

public void setUserName(Object userName) {
this.userName = userName;
}

}