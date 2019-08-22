package com.assettagging.test.model.schedule_detail;

public class UserScheduleDetail {
    String UserId;
    String EMPID;
    String Location;
    String SCHEDULEID;
    String ACTIVITYTYPE;
    String STARTTIME;
    String ENDTIME;

    public UserScheduleDetail(String UserId, String EMPID, String Location, String SCHEDULEID, String ACTIVITYTYPE, String STARTTIME, String ENDTIME) {
        this.ACTIVITYTYPE = ACTIVITYTYPE;
        this.EMPID = EMPID;
        this.Location = Location;
        this.SCHEDULEID = SCHEDULEID;
        this.UserId = UserId;
        this.STARTTIME = STARTTIME;
        this.ENDTIME = ENDTIME;
    }


}
