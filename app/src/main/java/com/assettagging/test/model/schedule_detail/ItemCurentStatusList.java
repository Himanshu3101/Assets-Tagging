package com.assettagging.test.model.schedule_detail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemCurentStatusList implements Serializable {

@SerializedName("Name")
@Expose
private String name;
@SerializedName("StatusId")
@Expose
private String statusId;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getStatusId() {
return statusId;
}

public void setStatusId(String statusId) {
this.statusId = statusId;
}

}


