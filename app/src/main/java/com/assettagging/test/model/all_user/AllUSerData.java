package com.assettagging.test.model.all_user;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllUSerData {

@SerializedName("UserList")
@Expose
private List<UserList> userList = null;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;

public List<UserList> getUserList() {
return userList;
}

public void setUserList(List<UserList> userList) {
this.userList = userList;
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