package com.assettagging.test.model.WebServer;

import com.assettagging.test.model.Upadte_tag;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.all_data.GetAllData_USer;
import com.assettagging.test.model.all_user.AllUSerData;
import com.assettagging.test.model.assetList.assetslistModel;
import com.assettagging.test.model.asset_detai.AssetData;
import com.assettagging.test.model.asset_detai.DisposalAssetData;
import com.assettagging.test.model.asset_detai.SaveAssets;
import com.assettagging.test.model.asset_detai.SaveDisposalTrack;
import com.assettagging.test.model.asset_detai.UserAssets;
import com.assettagging.test.model.asset_disposal.AssetDisposal;
import com.assettagging.test.model.asset_disposal.UserAssetDisposal;
import com.assettagging.test.model.asset_disposal.UserDisposalSchedule;
import com.assettagging.test.model.locationwise.LocationWise;
import com.assettagging.test.model.locationwise.UserLocationWise;
import com.assettagging.test.model.login.ChangePassword;
import com.assettagging.test.model.login.ForgotUser;
import com.assettagging.test.model.login.Login;
import com.assettagging.test.model.login.LoginUser;
import com.assettagging.test.model.login.ResetUser;
import com.assettagging.test.model.login.UserChangePass;
import com.assettagging.test.model.schedule.ScheduleData;
import com.assettagging.test.model.schedule.UserSchedule;
import com.assettagging.test.model.movement_dimension.FinacialDimension;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.model.schedule_detail.UserScheduleDetail;
import com.assettagging.test.model.tasklocationwise.TaskLocationWise;
import com.assettagging.test.model.tasklocationwise.UserTaskLocationWise;
import com.assettagging.test.model.user_tracking.TrackingStatus;
import com.assettagging.test.model.user_tracking.TrackingStatus_;
import com.assettagging.test.model.user_tracking.UserAssetGroup;
import com.assettagging.test.model.user_tracking.UserAssetGroupDetail;
import com.assettagging.test.model.user_tracking.UserAssetGroupProjectWise;
import com.assettagging.test.model.user_tracking.UserLocation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("LoginUser")
    Call<Login> doLogin(@Body LoginUser loginUser);

    @POST("GetAllUserLoginData")
    Call<AllUSerData> getAllUser();

    @POST("GetScheduleData")
    Call<ScheduleData> getUserSchedule(@Body UserSchedule userSchedule);

    @POST("SendEmail")
    Call<ResetUser> doResetUser(@Body ForgotUser forgotUser);

    @POST("GetScheduleDetailData")
    Call<ScheduleDetail> getUserScheduleDetail(@Body UserScheduleDetail userScheduleDetail);

    @POST("BindLocation")
    Call<TrackingStatus_> getLocations(@Body UserLocation userLocation);

    @POST("ChangePassword")
    Call<ChangePassword> getChangePassword(@Body UserChangePass userChangePass);

    @POST("GetScheduleLocation")
    Call<LocationWise> getLocationWise(@Body UserLocationWise userLocationWise);

    @POST("GetScheduleTask")
    Call<TaskLocationWise> getTaskLocationWise(@Body UserTaskLocationWise userTaskLocationWise);

    @POST("SaveTracking")
    Call<SaveTracking> getSaveTracking(@Body UserScannedList userScheduleDetail);

    /////sent image
    @POST("GetBarcodeWiseData")
    Call<AssetData> GetBarcodeWiseData(@Body UserAssets userAssets);

    @POST("GetAllData")
    Call<AllData> getAllData(@Body GetAllData_USer userChangePass);

    @POST("SaveDisposal")
    Call<SaveDisposalTrack> getSaveDisposalTracking(@Body SaveAssets saveAssets);
/////ok
    @POST("GetDisposalSchedule")
    Call<AssetDisposal> getGetDisposalSchedule(@Body UserAssetDisposal userAssetDisposal);

    /////sent image
    @POST("GetBarcodeWiseProjectData")
    Call<AssetData> GetBarcodeWiseProjectData(@Body UserAssets userAssets);

    @POST("UpDateTagNo")
    Call<TrackingStatus> UpDateTagNo(@Body Upadte_tag upadte_tag);

    /////sent image ok
    @POST("GetDisposalScheduleDetail")
    Call<DisposalAssetData> GetDisposalScheduleDetail(@Body UserDisposalSchedule userAssetDisposal);
//ok
    @POST("GetProjectLocationWise")
    Call<TrackingStatus_> GetProjectLocationWise(@Body UserAssetGroupProjectWise userProjectLocationWise);
//ok
    @POST("GetAssetGroup")
    Call<TrackingStatus_> GetAssetGroup(@Body UserAssetGroup userAssetGroupProjectWise);
//ok
    @POST("GetAssetAssetGruopwise")
    Call<assetslistModel> GetAssetAssetGruopwise(@Body UserAssetGroupDetail userAssetGroupProjectWise);

    @POST("GetDimesionData")
    Call<FinacialDimension> GetDimesionData();
}