package com.assettagging.test.model.all_data;

import com.assettagging.test.model.all_user.UserList;
import com.assettagging.test.model.assetList.assetslistModel;
import com.assettagging.test.model.assetList.disposalassetlist;
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.model.locationwise.ScheduleLocation;
import com.assettagging.test.model.movement_dimension.ListAccount;
import com.assettagging.test.model.movement_dimension.ListCostcenterDimension;
import com.assettagging.test.model.movement_dimension.ListProjectDimension;
import com.assettagging.test.model.movement_dimension.ListSiteDimension;
import com.assettagging.test.model.movement_dimension.ListWorkerDimension;
import com.assettagging.test.model.movement_dimension.ListdepartmentDimension;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.model.movement_dimension.FinacialDimension;
import com.assettagging.test.model.schedule_detail.ItemCurentStatusList;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.tasklocationwise.ScheduleLocationTask;
import com.assettagging.test.model.user_tracking.Location;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllData {

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
    //////////new Lists

    /////////////////
    public List<Schedule> getCompletedSchedule() {
        return completedSchedule;
    }

    public void setCompletedSchedule(List<Schedule> completedSchedule) {
        this.completedSchedule = completedSchedule;
    }

    @SerializedName("CompletedSchedule")
    @Expose
    private List<Schedule> completedSchedule = null;

    @SerializedName("UserList")
    @Expose
    private List<UserList> userList = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("scheduleLocation")
    @Expose
    private List<ScheduleLocation> scheduleLocation = null;

    @SerializedName("listCostcenterDimension")
    @Expose
    private List<ListCostcenterDimension> listCostcenterDimension = null;
    @SerializedName("listProjectDimension")
    @Expose
    private List<ListProjectDimension> listProjectDimension = null;
    @SerializedName("listSiteDimension")
    @Expose
    private List<ListSiteDimension> listSiteDimension = null;
    @SerializedName("listWorkerDimension")
    @Expose
    private List<ListWorkerDimension> listWorkerDimension = null;
    @SerializedName("listdepartmentDimension")
    @Expose
    private List<ListdepartmentDimension> listdepartmentDimension = null;
    @SerializedName("listAccount")
    @Expose
    private List<ListAccount> listlistAccount = null;

    public List<ListAccount> getlistAccount() {
        return listlistAccount;
    }

    public void setlistAccount(List<ListAccount> listlistAccount) {
        this.listlistAccount = listlistAccount;
    }

    public List<ListCostcenterDimension> getListCostcenterDimension() {
        return listCostcenterDimension;
    }

    public void setListCostcenterDimension(List<ListCostcenterDimension> listCostcenterDimension) {
        this.listCostcenterDimension = listCostcenterDimension;
    }

    public List<ListProjectDimension> getListProjectDimension() {
        return listProjectDimension;
    }

    public void setListProjectDimension(List<ListProjectDimension> listProjectDimension) {
        this.listProjectDimension = listProjectDimension;
    }

    public List<ListSiteDimension> getListSiteDimension() {
        return listSiteDimension;
    }

    public void setListSiteDimension(List<ListSiteDimension> listSiteDimension) {
        this.listSiteDimension = listSiteDimension;
    }

    public List<ListWorkerDimension> getListWorkerDimension() {
        return listWorkerDimension;
    }

    public void setListWorkerDimension(List<ListWorkerDimension> listWorkerDimension) {
        this.listWorkerDimension = listWorkerDimension;
    }

    public List<ListdepartmentDimension> getListdepartmentDimension() {
        return listdepartmentDimension;
    }

    public void setListdepartmentDimension(List<ListdepartmentDimension> listdepartmentDimension) {
        this.listdepartmentDimension = listdepartmentDimension;
    }


    @SerializedName("ActvityCount")
    @Expose
    private List<ActvityCount> ActvityCount = null;

    public List<ActvityCount> getActvityCount() {
        return ActvityCount;
    }

    public void setActvityCount(List<ActvityCount> ActvityCount) {
        this.ActvityCount = ActvityCount;
    }


    @SerializedName("status")
    @Expose
    private String status;

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

    @SerializedName("DisposalAssetList")
    @Expose
    private List<disposalassetlist> disposalAssetList = null;

    public List<disposalassetlist> getDisposalAssetList() {
        return disposalAssetList;
    }

    public void setDisposalAssetList(List<disposalassetlist> disposalAssetList) {
        this.disposalAssetList = disposalAssetList;
    }

    @SerializedName("Location")
    @Expose
    private List<Location> location = null;

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    /////////////////////new
    @SerializedName("ListBindLocationWise")
    @Expose
    private List<Location> ListBindLocationWise = null;

    public List<Location> getListBindLocationWise() {
        return ListBindLocationWise;
    }

    public void setListBindLocationWise(List<Location> ListBindLocationWise) {
        this.ListBindLocationWise = ListBindLocationWise;
    }

    @SerializedName("ListGetAssetGroup")
    @Expose
    private List<Location> listGetAssetGroup = null;

    public List<Location> getListGetAssetGroup() {
        return listGetAssetGroup;
    }

    public void setListGetAssetGroup(List<Location> listGetAssetGroup) {
        this.listGetAssetGroup = listGetAssetGroup;
    }

    @SerializedName("ListGetDisposalSchedule")
    @Expose
    private List<CreatedDisposalList> createdDisposalList = null;

    public List<CreatedDisposalList> getCreatedDisposalList() {
        return createdDisposalList;
    }

    public void setCreatedDisposalList(List<CreatedDisposalList> createdDisposalList) {
        this.createdDisposalList = createdDisposalList;
    }

    @SerializedName("ListGetDisposalSchedule1")
    @Expose
    private List<CreatedDisposalList> submittedDisposalList = null;

    public List<CreatedDisposalList> getSubmittedDisposalList() {
        return submittedDisposalList;
    }

    public void setSubmittedDisposalList(List<CreatedDisposalList> submittedDisposalList) {
        this.submittedDisposalList = submittedDisposalList;
    }

    @SerializedName("ListGetAssetAssetGruopwise")
    @Expose
    private List<disposalassetlist> ListGetAssetAssetGruopwise = null;

    public List<disposalassetlist> getListGetAssetAssetGruopwise() {
        return ListGetAssetAssetGruopwise;
    }

    public void setListGetAssetAssetGruopwise(List<disposalassetlist> ListGetAssetAssetGruopwise) {
        this.ListGetAssetAssetGruopwise = ListGetAssetAssetGruopwise;
    }

    @SerializedName("ListGetProjectLocationWise")
    @Expose
    private List<Location> GetProjectLocationWise = null;

    public List<Location> getGetProjectLocationWise() {
        return GetProjectLocationWise;
    }

    public void setGetProjectLocationWise(List<Location> GetProjectLocationWise) {
        this.GetProjectLocationWise = GetProjectLocationWise;
    }
    @SerializedName("ListGetAssetList")
    @Expose
    private List<disposalassetlist> ListGetAssetList = null;

    public List<disposalassetlist> getListGetAssetList() {
        return ListGetAssetList;
    }

    public void setListGetAssetList(List<disposalassetlist> GetListGetAssetList) {
        this.ListGetAssetList = ListGetAssetList;
    }

///////////////////////////////////


}


