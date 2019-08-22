package com.assettagging.test.model.schedule_detail;

public class UserScannedList {
    String UserId;
    String DesCription;
    String CheckInTime;
    String SCHEDULEID;
    String ScannedList;
    String STARTTIME;
    String ENDTIME;

    public UserScannedList(String UserId, String DesCription, String CheckInTime, String SCHEDULEID, String ScannedList) {
        this.UserId = UserId;
        this.DesCription = DesCription;
        this.CheckInTime = CheckInTime;
        this.SCHEDULEID = SCHEDULEID;
        this.ScannedList = ScannedList;
        this.STARTTIME = STARTTIME;
        this.ENDTIME = ENDTIME;

    }
}
