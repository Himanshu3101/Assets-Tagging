package com.assettagging.test.model.asset_detai;

public class SaveAssetsOffline {

    String UserId;
    String DesCription;
    String Date;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDesCription() {
        return DesCription;
    }

    public void setDesCription(String desCription) {
        DesCription = desCription;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getScannedList() {
        return ScannedList;
    }

    public void setScannedList(String scannedList) {
        ScannedList = scannedList;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    String ScannedList;
    String Type;

    public String getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(String scheduleID) {
        ScheduleID = scheduleID;
    }

    String ScheduleID;

    public String getScheduleName() {
        return ScheduleName;
    }

    public void setScheduleName(String scheduleName) {
        ScheduleName = scheduleName;
    }

    public String getDisposalDate() {
        return DisposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        DisposalDate = disposalDate;
    }

    String ScheduleName;
    String DisposalDate;


}
