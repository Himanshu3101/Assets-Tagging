package com.assettagging.test.model.schedule_detail;


import com.assettagging.test.model.movement_dimension.ListAccount;
import com.assettagging.test.model.movement_dimension.ListCostcenterDimension;
import com.assettagging.test.model.movement_dimension.ListProjectDimension;
import com.assettagging.test.model.movement_dimension.ListSiteDimension;
import com.assettagging.test.model.movement_dimension.ListWorkerDimension;
import com.assettagging.test.model.movement_dimension.ListdepartmentDimension;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleDetail {

    @SerializedName("ScheduleDetail")
    @Expose
    private List<ScheduleDetail_> scheduleDetail = null;
    @SerializedName("ItemCurentStatusList")
    @Expose
    private List<ItemCurentStatusList> ItemCurentStatusList = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
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

    public List<ItemCurentStatusList> getItemCurentStatusList() {
        return ItemCurentStatusList;
    }

    public void setItemCurentStatusList(List<ItemCurentStatusList> ItemCurentStatusList) {
        this.ItemCurentStatusList = ItemCurentStatusList;
    }


    public List<ScheduleDetail_> getScheduleDetail() {
        return scheduleDetail;
    }

    public void setScheduleDetail(List<ScheduleDetail_> scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
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

