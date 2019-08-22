package com.assettagging.test.model.login;

import com.assettagging.test.model.locationwise.ScheduleLocation;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.model.schedule_detail.ItemCurentStatusList;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.tasklocationwise.ScheduleLocationTask;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {

    @SerializedName("CompSchedule")
    @Expose
    private List<Schedule> compSchedule = null;
    @SerializedName("ItemCurentStatusList")
    @Expose
    private List<ItemCurentStatusList> itemCurentStatusList = null;
    @SerializedName("OngoingSchedule")
    @Expose
    private List<Schedule> ongoingSchedule = null;
    @SerializedName("ScheduleDetail")
    @Expose
    private List<ScheduleDetail_> scheduleDetail = null;
    @SerializedName("ScheduleLocationTask")
    @Expose
    private List<ScheduleLocationTask> scheduleLocationTask = null;
    @SerializedName("UpcomingSchedule")
    @Expose
    private List<Schedule> upcomingSchedule = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("scheduleLocation")
    @Expose
    private List<ScheduleLocation> scheduleLocation = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("IsAllowTagUpdate")
    @Expose
    private String IsAllowTagUpdate;


    public String getIsAllowTagUpdate() {
        return IsAllowTagUpdate;
    }

    public void setIsAllowTagUpdate(String IsAllowTagUpdate) {
        this.IsAllowTagUpdate = IsAllowTagUpdate;
    }


    public List<Schedule> getCompSchedule() {
        return compSchedule;
    }

    public void setCompSchedule(List<Schedule> compSchedule) {
        this.compSchedule = compSchedule;
    }

    public List<ItemCurentStatusList> getItemCurentStatusList() {
        return itemCurentStatusList;
    }

    public void setItemCurentStatusList(List<ItemCurentStatusList> itemCurentStatusList) {
        this.itemCurentStatusList = itemCurentStatusList;
    }

    public List<Schedule> getOngoingSchedule() {
        return ongoingSchedule;
    }

    public void setOngoingSchedule(List<Schedule> ongoingSchedule) {
        this.ongoingSchedule = ongoingSchedule;
    }

    public List<ScheduleDetail_> getScheduleDetail() {
        return scheduleDetail;
    }

    public void setScheduleDetail(List<ScheduleDetail_> scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
    }

    public List<ScheduleLocationTask> getScheduleLocationTask() {
        return scheduleLocationTask;
    }

    public void setScheduleLocationTask(List<ScheduleLocationTask> scheduleLocationTask) {
        this.scheduleLocationTask = scheduleLocationTask;
    }

    public List<Schedule> getUpcomingSchedule() {
        return upcomingSchedule;
    }

    public void setUpcomingSchedule(List<Schedule> upcomingSchedule) {
        this.upcomingSchedule = upcomingSchedule;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
