package com.assettagging.test.view.AddAssetLocation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.assettagging.test.SpinnerAdapter;
import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.assetList.assetslistModel;
import com.assettagging.test.model.assetList.disposalassetlist;
import com.assettagging.test.model.user_tracking.Location;
import com.assettagging.test.model.user_tracking.TrackingStatus_;
import com.assettagging.test.model.user_tracking.UserAssetGroup;
import com.assettagging.test.model.user_tracking.UserAssetGroupDetail;
import com.assettagging.test.model.user_tracking.UserAssetGroupProjectWise;
import com.assettagging.test.model.user_tracking.UserLocation;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.assetdisposer.existing.AddAssetDetailActivity;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.dashboard.DashBoardFragment;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.view.schedule.ScheduleFragmnet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.assettagging.test.controller.Constants.SCHEDULE_ID;

/**
 * Created by IACE on 29-Nov-18.
 */

public class BindLocationDisposalActivity extends BaseActivity {
    @BindView(R.id.recyclerViewData)
    RecyclerView recyclerViewData;
    @BindView(R.id.textViewLocation)
    TextView textViewLocation;
    @BindView(R.id.textViewProject)
    TextView textViewProject;
    @BindView(R.id.textViewAssetGroup)
    TextView textViewAssetGroup;
    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private DataBaseHelper dataBaseHelper;
    private MenuItem addMenu;
    private static BindLocationDisposalActivity instance;
    private GroupWIseAssetListAdapter groupWIseAssetListAdapter;
    private AllData allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);
        instance = this;
        dataBaseHelper = new DataBaseHelper(this);
        setActionBarData();
        NavigationActivity.getInstance().action_LoadMore.setVisible(false);
        if (CheckInternetConnection.isInternetConnected(this)) {
            getLocationData();
        } else {
            getLocationDataOffline();
        }
        onclickMethod();
    }

    private void getLocationDataOffline() {
        Gson gson = new Gson();
        String json = Preferance.getAllDAta(BindLocationDisposalActivity.this);
        allData = gson.fromJson(json, AllData.class);
        getLocation.clear();
        getLocation.addAll(allData.getListBindLocationWise());
        LocationId = getLocation.get(0).getlocation();
        textViewLocation.setText(getLocation.get(0).getLocationNameOff());
        if (recyclerViewData.getVisibility() == View.VISIBLE) {
            recyclerViewData.setVisibility(View.GONE);
            buttonSubmit.setVisibility(View.GONE);
        }
        textViewProject.setText("");
        getProject.clear();
        getAssetGroup.clear();
        textViewAssetGroup.setText("");
        ProjectId = "";
        AssetGroupId = "";
    }

    public void banknamedialog(final List<Location> locations, final String string) {
        final Dialog dialog = new Dialog(BindLocationDisposalActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bankname_dialog);
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        final ListView listsponser = dialog.findViewById(R.id.li_banknamelist);

        listsponser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (string.equals("Location")) {
                    if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                        if (textViewLocation.getText().toString().trim().length() > 0) {
                            textViewLocation.setText("");
                            textViewLocation.setText(locations.get(i).getName());
                        }
                        LocationId = locations.get(i).getId();
                        textViewLocation.setText("");
                        textViewLocation.setText(locations.get(i).getName());
                        getProjectData(locations.get(i).getId());
                    } else {
                        if (textViewLocation.getText().toString().trim().length() > 0) {
                            textViewLocation.setText("");
                            textViewLocation.setText(locations.get(i).getLocationNameOff());
                        }
                        LocationId = locations.get(i).getlocation();
                        textViewLocation.setText("");
                        textViewLocation.setText(locations.get(i).getLocationNameOff());
                        getProject.clear();
                        for (int j = 0; j < allData.getGetProjectLocationWise().size(); j++) {
                            if (allData.getGetProjectLocationWise().get(j).getlocation().equals(LocationId)) {
                                getProject.add(allData.getGetProjectLocationWise().get(j));
                            }
                        }
                        ProjectId = getProject.get(0).getlocation();
                        textViewProject.setText(getProject.get(0).getName());
                        if (recyclerViewData.getVisibility() == View.VISIBLE) {
                            recyclerViewData.setVisibility(View.GONE);
                            buttonSubmit.setVisibility(View.GONE);
                        }
                        getAssetGroup.clear();
                        textViewAssetGroup.setText("");
                        AssetGroupId = "";
                    }
                } else if (string.equals("Project")) {
                    if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                        if (textViewProject.getText().toString().trim().length() > 0) {
                            textViewProject.setText("");
                            textViewProject.setText(locations.get(i).getName());
                        }
                        textViewProject.setText("");
                        textViewProject.setText(locations.get(i).getName());
                        ProjectId = locations.get(i).getId();
                        getAssetGroup(ProjectId, LocationId);
                    } else {
                        if (textViewProject.getText().toString().trim().length() > 0) {
                            textViewProject.setText("");
                            textViewProject.setText(locations.get(i).getName());
                        }
                        textViewProject.setText("");
                        textViewProject.setText(locations.get(i).getName());
                        ProjectId = locations.get(i).getlocation();
                        if (allData.getListGetAssetGroup().size() == 0) {
                            CustomToast.showToast(BindLocationDisposalActivity.this, getResources().getString(R.string.no_data_found));
                        } else {
                            getAssetGroup.clear();
                            for (int j = 0; j < allData.getListGetAssetGroup().size(); j++) {
                                if (allData.getListGetAssetGroup().get(j).getProjectId().equals(ProjectId)) {
                                    getAssetGroup.add(allData.getListGetAssetGroup().get(j));
                                }
                            }
                            textViewAssetGroup.setText(getAssetGroup.get(0).getName());
                            AssetGroupId = getAssetGroup.get(0).getId();
                        }
                    }
                } else if (string.equals("Asset Group")) {

                    if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                        if (textViewAssetGroup.getText().toString().trim().length() > 0) {
                            textViewAssetGroup.setText("");
                            textViewAssetGroup.setText(locations.get(i).getName());
                        }
                        textViewAssetGroup.setText("");
                        textViewAssetGroup.setText(locations.get(i).getName());
                        AssetGroupId = locations.get(i).getId();
                        getAssetGroupDetailData(locations.get(i).getId(), LocationId, ProjectId);
                    } else {
                        if (textViewAssetGroup.getText().toString().trim().length() > 0) {
                            textViewAssetGroup.setText("");
                            textViewAssetGroup.setText(locations.get(i).getName());
                        }
                        textViewAssetGroup.setText("");
                        textViewAssetGroup.setText(locations.get(i).getName());
                        AssetGroupId = locations.get(i).getId();

                        recyclerViewData.setVisibility(View.VISIBLE);
                        buttonSubmit.setVisibility(View.VISIBLE);
                        List<disposalassetlist> ListGetDisposalScheduleDetail = new ArrayList<>();
                        for (int j = 0; j < allData.getListGetAssetAssetGruopwise().size(); j++) {
                            if (allData.getListGetAssetAssetGruopwise().get(j).getProjectId().equals(ProjectId) && allData.getListGetAssetAssetGruopwise().get(j).getLocation().equals(LocationId) && allData.getListGetAssetAssetGruopwise().get(j).getAssetGroup().equals(AssetGroupId)) {
                                ListGetDisposalScheduleDetail.add(allData.getListGetAssetAssetGruopwise().get(j));
                            }
                        }
                        groupWIseAssetListAdapter = new GroupWIseAssetListAdapter(BindLocationDisposalActivity.this, ListGetDisposalScheduleDetail);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewData.setLayoutManager(mLayoutManager);
                        recyclerViewData.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewData.setAdapter(groupWIseAssetListAdapter);
                    }
                }

                dialog.dismiss();

            }
        });

        final EditText edtname = dialog.findViewById(R.id.edt_searchbankname);
        final SpinnerAdapter adapter = new SpinnerAdapter(BindLocationDisposalActivity.this, locations, string);

        listsponser.setAdapter(adapter);

        edtname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = edtname.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });
        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onclickMethod() {
        textViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLocation.size() == 0) {
                    if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                        getLocationData();
                    } else {
                        getLocation.clear();
                        getLocation.addAll(allData.getListBindLocationWise());
                        LocationId = getLocation.get(0).getId();
                        textViewLocation.setText(getLocation.get(0).getName());
                        if (recyclerViewData.getVisibility() == View.VISIBLE) {
                            recyclerViewData.setVisibility(View.GONE);
                            buttonSubmit.setVisibility(View.GONE);
                        }
                        textViewProject.setText("");
                        getProject.clear();
                        getAssetGroup.clear();
                        textViewAssetGroup.setText("");
                        ProjectId = "";
                        AssetGroupId = "";
                    }
                } else {
                    banknamedialog(getLocation, "Location");
                }
            }
        });
        textViewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewLocation.getText().toString().trim().equals("")) {
                    CustomToast.showToast(BindLocationDisposalActivity.this, "Please select Location_ First");
                } else {
                    if (getProject.size() == 0) {
                        if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                            getProjectData(LocationId);
                        } else {
                            if (allData.getGetProjectLocationWise().size() == 0) {
                                CustomToast.showToast(BindLocationDisposalActivity.this, getResources().getString(R.string.no_data_found));
                            } else {
                                getProject.clear();
                                ///according to location
                                for (int j = 0; j < allData.getGetProjectLocationWise().size(); j++) {
                                    if (allData.getGetProjectLocationWise().get(j).getlocation().equals(LocationId)) {
                                        getProject.add(allData.getGetProjectLocationWise().get(j));
                                    }
                                }
                                ProjectId = allData.getGetProjectLocationWise().get(0).getId();
                                textViewProject.setText(allData.getGetProjectLocationWise().get(0).getName());
                                if (recyclerViewData.getVisibility() == View.VISIBLE) {
                                    recyclerViewData.setVisibility(View.GONE);
                                    buttonSubmit.setVisibility(View.GONE);
                                }
                                getAssetGroup.clear();
                                textViewAssetGroup.setText("");
                                AssetGroupId = "";
                            }
                        }

                    } else {
                        banknamedialog(getProject, "Project");
                    }
                }
            }
        });
        textViewAssetGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewLocation.getText().toString().trim().equals("")) {
                    CustomToast.showToast(BindLocationDisposalActivity.this, "Please select Location_ First");
                } else if (textViewProject.getText().toString().trim().equals("")) {
                    CustomToast.showToast(BindLocationDisposalActivity.this, "Please select Project First");
                } else {
                    if (getAssetGroup.size() == 0) {
                        if (CheckInternetConnection.isInternetConnected(BindLocationDisposalActivity.this)) {
                            getAssetGroup(ProjectId, LocationId);
                        } else {
                            if (allData.getListGetAssetGroup().size() == 0) {
                                CustomToast.showToast(BindLocationDisposalActivity.this, getResources().getString(R.string.no_data_found));
                            } else {
                                getAssetGroup.clear();
                                for (int j = 0; j < allData.getListGetAssetGroup().size(); j++) {
                                    if (allData.getListGetAssetGroup().get(j).getProjectId().equals(ProjectId) && allData.getListGetAssetGroup().get(j).getlocation().equals(LocationId)) {
                                        getAssetGroup.add(allData.getListGetAssetGroup().get(j));
                                    }
                                }
                                textViewAssetGroup.setText(getAssetGroup.get(0).getName());
                                AssetGroupId = getAssetGroup.get(0).getId();
                                recyclerViewData.setVisibility(View.VISIBLE);
                                buttonSubmit.setVisibility(View.VISIBLE);
                                List<disposalassetlist> ListGetDisposalScheduleDetail = new ArrayList<>();
                                for (int j = 0; j < allData.getListGetAssetAssetGruopwise().size(); j++) {
                                    if (allData.getListGetAssetAssetGruopwise().get(j).getProjectId().equals(ProjectId) && allData.getListGetAssetAssetGruopwise().get(j).getLocation().equals(LocationId) && allData.getListGetAssetAssetGruopwise().get(j).getAssetGroup().equals(AssetGroupId)) {
                                        ListGetDisposalScheduleDetail.add(allData.getListGetAssetAssetGruopwise().get(j));
                                    }
                                }
                                groupWIseAssetListAdapter = new GroupWIseAssetListAdapter(BindLocationDisposalActivity.this, ListGetDisposalScheduleDetail);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerViewData.setLayoutManager(mLayoutManager);
                                recyclerViewData.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewData.setAdapter(groupWIseAssetListAdapter);
                            }
                        }
                    } else {
                        banknamedialog(getAssetGroup, "Asset Group");
                    }
                }
            }
        });

    }

    public static BindLocationDisposalActivity getInstance() {
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_assets, menu);
        addMenu = menu.findItem(R.id.action_add_new);
        addMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_logout:
                Preferance.clearPreference(this);
                ScheduleFragmnet.instance = null;
                DashBoardFragment.instance = null; dataBaseHelper.dropTable();
                dataBaseHelper.dropAssetandDisposalTable();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_change_password:
                AddAssetDetailActivity.getInstance().changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBarData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.addAsset));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    List<Location> getLocation = new ArrayList<>();
    List<Location> getProject = new ArrayList<>();
    List<Location> getAssetGroup = new ArrayList<>();

    public void getLocationData() {
        final String userId = Preferance.getUserId(this);
        CustomProgress.startProgressContext(this);

        Call<TrackingStatus_> call = MyApplication.apiInterface.getLocations(new UserLocation(userId));
        call.enqueue(new Callback<TrackingStatus_>() {
            @Override
            public void onResponse(Call<TrackingStatus_> call, Response<TrackingStatus_> response) {
                getLocation.clear();
                getLocation.addAll(response.body().getLocation());
                LocationId = getLocation.get(0).getId();
                textViewLocation.setText(getLocation.get(0).getName());
                if (recyclerViewData.getVisibility() == View.VISIBLE) {
                    recyclerViewData.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                }
                textViewProject.setText("");
                getProject.clear();
                getAssetGroup.clear();
                textViewAssetGroup.setText("");
                ProjectId = "";
                AssetGroupId = "";
                CustomProgress.endProgressContext();
            }

            @Override
            public void onFailure(Call<TrackingStatus_> call, Throwable t) {
                textViewProject.setText("");
                getProject.clear();
                getAssetGroup.clear();
                textViewAssetGroup.setText("");
                ProjectId = "";
                AssetGroupId = "";
                if (recyclerViewData.getVisibility() == View.VISIBLE) {
                    recyclerViewData.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                }
                insertDataInDataBase(null);
                CustomProgress.endProgressContext();
            }
        });
    }

    String LocationId, ProjectId, AssetGroupId = "";

    private void insertDataInDataBase(final List<Location> response) {
        CustomToast.showToast(this, response.get(0).getName());
        textViewLocation.setText(response.get(0).getName());
        // getProjectData(response.body().getId().get(0).getId());


    }


    public void OnAssetGroupClick() {
//        if (textViewLocation.getText().toString().equals("")) {
//            CustomToast.showToast(this, "Please select Project");
//        } else if (textViewProject.getText().toString().equals("")) {
//            CustomToast.showToast(this, "Please select Location_");
//        } else {
//            getAssetGroup(location.get(i).getId());
//        }
    }

    private void getAssetGroup(String projId, String locationId) {
        final String userId = Preferance.getUserId(this);
        CustomProgress.startProgressContext(this);
        Call<TrackingStatus_> call = MyApplication.apiInterface.GetAssetGroup(new UserAssetGroup(userId, projId, locationId));
        call.enqueue(new Callback<TrackingStatus_>() {
            @Override
            public void onResponse(Call<TrackingStatus_> call, Response<TrackingStatus_> response) {

                setAssetGroupResponse(response);
                CustomProgress.endProgressContext();


            }

            @Override
            public void onFailure(Call<TrackingStatus_> call, Throwable t) {
                setAssetGroupResponse(null);
                CustomProgress.endProgressContext();

            }
        });
    }

    private void getAssetGroupDetailData(String assetgroupId, String locationId, String projectId) {
        final String userId = Preferance.getUserId(this);
        CustomProgress.startProgressContext(this);
        Call<assetslistModel> call = MyApplication.apiInterface.GetAssetAssetGruopwise(new UserAssetGroupDetail(userId, assetgroupId, projectId, locationId));

        call.enqueue(new Callback<assetslistModel>() {
            @Override
            public void onResponse(Call<assetslistModel> call, Response<assetslistModel> response) {
                setAssetGroupDetailResponse(response);
                CustomProgress.endProgressContext();
            }

            @Override
            public void onFailure(Call<assetslistModel> call, Throwable t) {
                setAssetGroupDetailResponse(null);
                CustomProgress.endProgressContext();
            }
        });
    }

    private void setAssetGroupResponse(final Response<TrackingStatus_> response) {
        if (response == null) {
            CustomToast.showToast(this, getResources().getString(R.string.something_bad_happened));
        } else {
            if (response.body().getStatus().equals("success")) {
                if (response.body().getLocation().size() == 0) {
                    CustomToast.showToast(this, getResources().getString(R.string.no_data_found));
                } else {
                    getAssetGroup.clear();
                    getAssetGroup.addAll(response.body().getLocation());
                    textViewAssetGroup.setText(response.body().getLocation().get(0).getName());
                    AssetGroupId = response.body().getLocation().get(0).getId();
                    getAssetGroupDetailData(AssetGroupId, LocationId, ProjectId);
                }
            } else {
                if (recyclerViewData.getVisibility() == View.VISIBLE) {
                    recyclerViewData.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                }
                getAssetGroup.clear();
                textViewAssetGroup.setText("");
                AssetGroupId = "";
                CustomToast.showToast(this, getResources().getString(R.string.no_data_found));
            }
        }
    }

    private void callServiceforAssetDetail() {
        //  getAssetGroupDetailData(location.get(i).getId());
    }

    private void setAssetGroupDetailResponse(final Response<assetslistModel> response) {
        if (response == null) {
            CustomToast.showToast(this, getResources().getString(R.string.something_bad_happened));
        } else {
            if (response.body().getStatus().equals("success")) {
                if (response.body().getDisposalAssetList().size() == 0) {
                    recyclerViewData.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                    CustomToast.showToast(this, response.body().getMessage());
                } else {
                    recyclerViewData.setVisibility(View.VISIBLE);
                    buttonSubmit.setVisibility(View.VISIBLE);
                    setAdapter(response.body().getDisposalAssetList());
                }
            } else {
                buttonSubmit.setVisibility(View.GONE);
                recyclerViewData.setVisibility(View.GONE);
                CustomToast.showToast(this, getResources().getString(R.string.no_data_found));
            }
        }
    }

    public void setAdapter(List<disposalassetlist> disposalAssetList) {
        groupWIseAssetListAdapter = new GroupWIseAssetListAdapter(this, disposalAssetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewData.setLayoutManager(mLayoutManager);
        recyclerViewData.setItemAnimator(new DefaultItemAnimator());
        recyclerViewData.setAdapter(groupWIseAssetListAdapter);
    }

    public void OnLOcationClick() {
        if (textViewLocation.getText().toString().equals("")) {
            getLocationData();
        }
    }


    private void getProjectData(String location) {
        final String userId = Preferance.getUserId(this);
        CustomProgress.startProgressContext(this);
        Call<TrackingStatus_> call = MyApplication.apiInterface.GetProjectLocationWise(new UserAssetGroupProjectWise(userId, location));
        call.enqueue(new Callback<TrackingStatus_>() {
            @Override
            public void onResponse(Call<TrackingStatus_> call, Response<TrackingStatus_> response) {
                setResponse(response);
                CustomProgress.endProgressContext();
            }

            @Override
            public void onFailure(Call<TrackingStatus_> call, Throwable t) {
                setResponse(null);
                CustomProgress.endProgressContext();
            }
        });
    }

    private void setResponse(final Response<TrackingStatus_> response) {
        if (response == null) {
            CustomToast.showToast(this, getResources().getString(R.string.something_bad_happened));
        } else {
            if (response.body().getStatus().equals("success")) {
                if (response.body().getLocation().size() == 0) {
                    CustomToast.showToast(this, getResources().getString(R.string.no_data_found));
                } else {
                    getProject.clear();
                    getProject.addAll(response.body().getLocation());
                    ProjectId = response.body().getLocation().get(0).getId();
                    textViewProject.setText(response.body().getLocation().get(0).getName());
                    if (recyclerViewData.getVisibility() == View.VISIBLE) {
                        recyclerViewData.setVisibility(View.GONE);
                        buttonSubmit.setVisibility(View.GONE);
                    }
                    getAssetGroup.clear();
                    textViewAssetGroup.setText("");
                    AssetGroupId = "";
                }
            } else {
                getAssetGroup.clear();
                textViewAssetGroup.setText("");
                AssetGroupId = "";
                if (recyclerViewData.getVisibility() == View.VISIBLE) {
                    recyclerViewData.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                }
                getProject.clear();
                textViewProject.setText("");
                ProjectId = "";
                CustomToast.showToast(this, getResources().getString(R.string.no_data_found));

            }
        }
    }

    public void onSubmitButtonClick(View view) {
        if (GroupWIseAssetListAdapter.getInstance() != null) {
            if (groupWIseAssetListAdapter.checkedList.size() > 0) {
                for (int i = 0; i < groupWIseAssetListAdapter.checkedList.size(); i++) {
                    dataBaseHelper.insertAssets(groupWIseAssetListAdapter.checkedList.get(i), AddAssetDetailActivity.getInstance().SCHEDULE_ID);
                }
                AddAssetDetailActivity.getInstance().setAdapter(dataBaseHelper.getParticularDisposalAssets(SCHEDULE_ID));
                finish();
            } else {
                CustomToast.showToast(BindLocationDisposalActivity.this, "Please Select Assets");
            }
        } else {
            CustomToast.showToast(BindLocationDisposalActivity.this, getResources().getString(R.string.something_bad_happened));
        }
    }
}
