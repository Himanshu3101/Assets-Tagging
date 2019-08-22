package com.assettagging.test.model.schedule;

import com.assettagging.test.model.all_data.ActvityCount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ScheduleData {

    @SerializedName("CompSchedule")
    @Expose
    private List<Schedule> compSchedule = new ArrayList<>();
    @SerializedName("OngoingSchedule")
    @Expose
    private List<Schedule> ongoingSchedule =  new ArrayList<>();
    @SerializedName("UpcomingSchedule")
    @Expose
    private List<Schedule> upcomingSchedule =  new ArrayList<>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ActvityCount")
    @Expose
    private List<ActvityCount> ActvityCount;

    public List<ActvityCount> getActvityCount() {
        return ActvityCount;
    }
    public void setActvityCount(List<ActvityCount> ActvityCount) {
        this.ActvityCount = ActvityCount;
    }

    public List<Schedule> getCompSchedule() {
        return compSchedule;
    }

    public void setCompSchedule(List<Schedule> compSchedule) {
        this.compSchedule = compSchedule;
    }

    public List<Schedule> getOngoingSchedule() {
        return ongoingSchedule;
    }

    public void setOngoingSchedule(List<Schedule> ongoingSchedule) {
        this.ongoingSchedule = ongoingSchedule;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
