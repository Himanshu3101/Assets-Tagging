package com.assettagging.test.model.tasklocationwise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskLocationWise {

@SerializedName("ScheduleLocationTask")
@Expose
private List<ScheduleLocationTask> scheduleLocationTask = null;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;

public List<ScheduleLocationTask> getScheduleLocationTask() {
return scheduleLocationTask;
}

public void setScheduleLocationTask(List<ScheduleLocationTask> scheduleLocationTask) {
this.scheduleLocationTask = scheduleLocationTask;
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
