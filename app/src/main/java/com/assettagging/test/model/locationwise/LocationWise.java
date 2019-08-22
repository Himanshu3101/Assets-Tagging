package com.assettagging.test.model.locationwise;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationWise {

@SerializedName("message")
@Expose
private String message;
@SerializedName("scheduleLocation")
@Expose
private List<ScheduleLocation> scheduleLocation = null;
@SerializedName("status")
@Expose
private String status;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<ScheduleLocation> getScheduleLocation() {
return scheduleLocation;
}

public void setScheduleLocation(List<ScheduleLocation> scheduleLocation) {
this.scheduleLocation = scheduleLocation;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}

