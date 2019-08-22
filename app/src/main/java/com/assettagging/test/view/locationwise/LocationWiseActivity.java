package com.assettagging.test.view.locationwise;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.Constants;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.locationwise.LocationWise;
import com.assettagging.test.model.locationwise.ScheduleLocation;
import com.assettagging.test.model.locationwise.UserLocationWise;
import com.assettagging.test.model.login.ChangePassword;
import com.assettagging.test.model.login.UserChangePass;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.custom_control.CustomDialogForMessages;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.dashboard.DashBoardFragment;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.view.schedule.ScheduleFragmnet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationWiseActivity extends BaseActivity {
    public static LocationWiseActivity instance;
    @BindView(R.id.recyclerViewData)
    RecyclerView recyclerViewData;
    @BindView(R.id.textViewNoData)
    TextView textViewNoData;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean firstTime = false;
    private String ScheduleId, EmpID, Status;
    private Handler handler = new Handler();
    private LocationWiseAdapter locationWiseAdapter;
    private Dialog dialogChangePassword;
    Call<LocationWise> call;
    private DataBaseHelper dataBaseHelper;
    private MenuItem menuitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_wise);
        ButterKnife.bind(this);
        instance = this;
        dataBaseHelper = new DataBaseHelper(this);
        setActionBarData();
        getIntentData();
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            getLocationData();
        } else {
            getLocationDataOffline();
        }

        onRefreshListener();
    }

    List<ScheduleLocation> scheduleLocationList = new ArrayList<>();

    private void getLocationDataOffline() {
        Gson gson = new Gson();
        String json = Preferance.getAllDAta(LocationWiseActivity.this);
        AllData allData = gson.fromJson(json, AllData.class);
        for (int i = 0; i < allData.getScheduleLocation().size(); i++) {
            if (allData.getScheduleLocation().get(i).getSCHEDULEID().equals(ScheduleId)) {
                scheduleLocationList.add(allData.getScheduleLocation().get(i));
            }
        }
        setLocationWiseAdapter(scheduleLocationList);

    }

    public static LocationWiseActivity getInstance() {
        return instance;
    }

    public void doWork() {
        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String checkintime = df.format(c);
        List<ScheduleDetail_> scheduleDetail_s = new ArrayList<>();
        scheduleDetail_s.addAll(dbHelper.getAllAssets());
        List<ScheduleDetail_> tempList = null;
        for (int i = 0; i < scheduleDetail_s.size(); i++) {
            tempList = new ArrayList<>();
            for (int j = 0; j < scheduleDetail_s.size(); j++) {
                if (scheduleDetail_s.get(i).getSCHEDULEID().equals(scheduleDetail_s.get(j).getSCHEDULEID())) {
                    tempList.add(scheduleDetail_s.get(j));

                }

            }
            for (int j = 0; j < tempList.size(); j++) {
                tempList.get(j).setITEMS("");
            }
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(tempList).getAsJsonArray();
            String newString = myCustomArray.toString().replace("\"", "\'");
            String userId = Preferance.getUserId(getApplicationContext());
            if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                UserScannedList userScheduleDetail = new UserScannedList(userId, "", checkintime, scheduleDetail_s.get(i).getSCHEDULEID(), newString);
                Call<SaveTracking> call = MyApplication.apiInterface.getSaveTracking(userScheduleDetail);
                call.enqueue(new Callback<SaveTracking>() {
                    @Override
                    public void onResponse(Call<SaveTracking> call, Response<SaveTracking> response) {
                        CustomProgress.endProgressContext();

                    }

                    @Override
                    public void onFailure(Call<SaveTracking> call, Throwable t) {
                        CustomProgress.endProgressContext();
                    }
                });
            }

        }
    }

    private void setActionBarData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.scheduleLocations));
    }

    private void getIntentData() {
        EmpID = getIntent().getStringExtra(Constants.EmpID);
        ScheduleId = getIntent().getStringExtra(Constants.SCHEDULE_ID);
        Status = getIntent().getStringExtra(Constants.STATUS);
    }

    public void getLocationData() {
        if (!firstTime) {
            CustomProgress.startProgressContext(this);
        }
        String userId = Preferance.getUserId(this);
        UserLocationWise userLocationWise = new UserLocationWise(userId, ScheduleId);
        if (call != null) {
            call.cancel();
            call = null;
        }
        call = MyApplication.apiInterface.getLocationWise(userLocationWise);
        call.enqueue(new Callback<LocationWise>() {
            @Override
            public void onResponse(Call<LocationWise> call, Response<LocationWise> response) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                setLocationWiseResponse(response.body());

            }

            @Override
            public void onFailure(Call<LocationWise> call, Throwable t) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                setLocationWiseResponse(null);
            }
        });
    }

    private void setLocationWiseResponse(LocationWise body) {
        if (body == null) {
            if (!firstTime) {
                CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
            }
        } else {
            if (body.getStatus().equals("success")) {
                if (body.getScheduleLocation().size() > 0) {
                    pullToRefresh.setVisibility(View.VISIBLE);
                    recyclerViewData.setVisibility(View.VISIBLE);
                    textViewNoData.setVisibility(View.GONE);
                    setLocationWiseAdapter(body.getScheduleLocation());
                    CustomDialogForMessages.closeDialog();
                    doTheAutoRefresh();
                    saveDataInDataBase(body.getScheduleLocation());

                } else {
                    pullToRefresh.setVisibility(View.GONE);
                    recyclerViewData.setVisibility(View.GONE);
                    textViewNoData.setVisibility(View.VISIBLE);
                    doTheAutoRefresh();
                    if (Status.equals("Processing")) {
                        textViewNoData.setText("This Task is completed.");
                    }

                }
                firstTime = true;
            } else {
                recyclerViewData.setVisibility(View.GONE);
                pullToRefresh.setVisibility(View.GONE);
                textViewNoData.setVisibility(View.VISIBLE);
                if (!firstTime) {
                    CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
                }
                firstTime = true;
            }

        }
    }

    private void saveDataInDataBase(List<ScheduleLocation> scheduleLocation) {
        //  dataBaseHelper.dropLocationTable(this);
        dataBaseHelper.updatescheduleLocation(scheduleLocation);
    }

    private void setLocationWiseAdapter(List<ScheduleLocation> scheduleLocation) {
        if (scheduleLocation.size() > 0) {
            pullToRefresh.setVisibility(View.VISIBLE);
            recyclerViewData.setVisibility(View.VISIBLE);
            textViewNoData.setVisibility(View.GONE);
            locationWiseAdapter = new LocationWiseAdapter(this, scheduleLocation);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerViewData.setLayoutManager(mLayoutManager);
            recyclerViewData.setItemAnimator(new DefaultItemAnimator());
            recyclerViewData.setAdapter(locationWiseAdapter);
        } else {
            recyclerViewData.setVisibility(View.GONE);
            pullToRefresh.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }
    }


    private void doTheAutoRefresh() {
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            try {
                CustomDialogForMessages.closeDialog();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getLocationData();
                        doTheAutoRefresh();


                    }
                }, 10000);
            } catch (Exception e) {
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
                // handler = add_new Handler();
            }
        } else {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
        }
    }

    private void onRefreshListener() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    getLocationData();
                } else {
                    CustomToast.showToast(LocationWiseActivity.this, getString(R.string.no_internet_connection));
                }
                pullToRefresh.setRefreshing(false);

            }
        });
    }

    public static MenuItem menuitemfilter, action_LoadMore;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        menuitem = menu.findItem(R.id.action_add_new);
        menuitemfilter = menu.findItem(R.id.action_filter);
        action_LoadMore = menu.findItem(R.id.action_LoadMore);
        action_LoadMore.setVisible(false);
        menuitem.setVisible(false);
        menuitemfilter.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_logout:
                Preferance.clearPreference(this);
                ScheduleFragmnet.instance = null;
                DashBoardFragment.instance = null; NavigationActivity.activity = null;
                dataBaseHelper.dropTable();
                dataBaseHelper.dropAssetandDisposalTable();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LinearLayout linearLayoutContainer;

    public void changePassword() {
        final String userId = Preferance.getUserId(this);

        final EditText edtoldpassword, edtnewpassword, edtconfirmpassword;
        Button tvChangepass;

        dialogChangePassword = new Dialog(this);
        dialogChangePassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChangePassword.setContentView(R.layout.dialog_changepassword);

        Window window = dialogChangePassword.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutContainer = dialogChangePassword.findViewById(R.id.linearLayoutContainer);

        edtoldpassword = dialogChangePassword.findViewById(R.id.edt_oldpassword);
        edtnewpassword = dialogChangePassword.findViewById(R.id.edt_oldnewpassword);
        edtconfirmpassword = dialogChangePassword.findViewById(R.id.edt_confirmpassword);

        tvChangepass = dialogChangePassword.findViewById(R.id.tv_changepassword);
        if (Preferance.getTheme(this).equals("ORANGE")) {
            linearLayoutContainer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            edtoldpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round, null));
            edtnewpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round, null));
            edtconfirmpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round, null));
            tvChangepass.setBackground(getResources().getDrawable(R.drawable.button_background, null));
        } else if (Preferance.getTheme(getApplicationContext()).equals("BLUE")) {
            linearLayoutContainer.setBackgroundColor(getResources().getColor(R.color.colorAccentBlue));
            edtoldpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue, null));
            edtnewpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue, null));
            edtconfirmpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue, null));
            tvChangepass.setBackground(getResources().getDrawable(R.drawable.button_background_blue, null));
        }
        tvChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtoldpassword.getText().toString().trim().equals("")) {
                    edtoldpassword.setError(getString(R.string.fill_old_pass));
                } else if (edtnewpassword.getText().toString().trim().equals("")) {
                    edtnewpassword.setError(getString(R.string.fill_new_password));
                } else if (edtconfirmpassword.getText().toString().trim().equals("")) {
                    edtconfirmpassword.setError(getString(R.string.fill_confirm_pass));
                } else if (!edtnewpassword.getText().toString().trim().equals(edtconfirmpassword.getText().toString().trim())) {
                    edtconfirmpassword.setError(getString(R.string.password_not_motached));
                } else {
                    changepassword(userId, edtnewpassword.getText().toString().trim());
                }
            }

        });
        dialogChangePassword.show();
    }

    private void changepassword(String UserId, String Password) {
        CustomProgress.startProgressContext(this);
        UserChangePass userChangePass = new UserChangePass(UserId, Password);
        Call<ChangePassword> call = MyApplication.apiInterface.getChangePassword(userChangePass);
        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                CustomProgress.endProgressContext();
                setChangePasswordResponse(response.body());
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                CustomProgress.endProgressContext();
                setChangePasswordResponse(null);

            }
        });
    }

    private void setChangePasswordResponse(ChangePassword body) {
        if (body != null) {
            dialogChangePassword.dismiss();
            if (body.getStatus().equals("Success")) {
                CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
            } else {
                CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
            }
        } else {
            CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            doTheAutoRefresh();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        overridePendingTransition(0, 0);
    }
}
