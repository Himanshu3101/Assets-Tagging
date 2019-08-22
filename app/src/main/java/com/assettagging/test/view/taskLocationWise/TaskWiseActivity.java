package com.assettagging.test.view.taskLocationWise;

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
import com.assettagging.test.model.login.ChangePassword;
import com.assettagging.test.model.login.UserChangePass;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.model.tasklocationwise.ScheduleLocationTask;
import com.assettagging.test.model.tasklocationwise.TaskLocationWise;
import com.assettagging.test.model.tasklocationwise.UserTaskLocationWise;
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

public class TaskWiseActivity extends BaseActivity {
    public static TaskWiseActivity instance;
    private String ScheduleId, Location, EmpId,LocationName = "";
    @BindView(R.id.recyclerViewData)
    RecyclerView recyclerViewData;
    @BindView(R.id.textViewNoData)
    TextView textViewNoData;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Handler handler = new Handler();
    private Dialog dialogChangePassword;
    private Call<TaskLocationWise> call;
    private boolean firstTime = false;
    private TaskWiseAdapter taskWiseAdapter;
    private DataBaseHelper dataBaseHelper;
    private LinearLayout linearLayoutContainer;
    private MenuItem menuitem;
    private List<ScheduleLocationTask> tasklist = new ArrayList<ScheduleLocationTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_wise);
        ButterKnife.bind(this);
        instance = this;
        dataBaseHelper = new DataBaseHelper(this);
        getIntentData();
        setActionBarData();
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            getTaskLocationWiseData();
        } else {
            getTaskLocationWiseDataOffline();
        }
        NavigationActivity.getInstance().action_LoadMore.setVisible(false);
        onRefreshListener();
    }

    private void getTaskLocationWiseDataOffline() {
        Gson gson = new Gson();
        String json = Preferance.getAllDAta(TaskWiseActivity.this);
        AllData allData = gson.fromJson(json, AllData.class);
        for (int i = 0; i < allData.getScheduleLocationTask().size(); i++) {
            if ( allData.getScheduleLocationTask().get(i).getSCHEDULEID().equals(ScheduleId) && allData.getScheduleLocationTask().get(i).getLOCATION().equals(Location)) {
                tasklist.add(allData.getScheduleLocationTask().get(i));
            }
        }
        setTaskWiseAdapter(tasklist);

    }

    public void getTaskLocationWiseData() {
        if (!firstTime) {
            CustomProgress.startProgressContext(this);
        }
        String userId = Preferance.getUserId(this);
        UserTaskLocationWise userTaskLocationWise = new UserTaskLocationWise(userId, EmpId, Location, ScheduleId);
        if (call != null) {
            call.cancel();
            call = null;
        }
        call = MyApplication.apiInterface.getTaskLocationWise(userTaskLocationWise);
        call.enqueue(new Callback<TaskLocationWise>() {
            @Override
            public void onResponse(Call<TaskLocationWise> call, Response<TaskLocationWise> response) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                setTaskLocationWiseResponse(response.body());

            }

            @Override
            public void onFailure(Call<TaskLocationWise> call, Throwable t) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                setTaskLocationWiseResponse(null);
            }
        });
    }

    private void setTaskLocationWiseResponse(TaskLocationWise body) {
        if (body == null) {
            if (!firstTime) {
                CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
            }
        } else {
            if (body.getStatus().equals("success")) {
                if (body.getScheduleLocationTask().size() > 0) {
                    pullToRefresh.setVisibility(View.VISIBLE);
                    recyclerViewData.setVisibility(View.VISIBLE);
                    textViewNoData.setVisibility(View.GONE);
                    setTaskWiseAdapter(body.getScheduleLocationTask());
                    doTheAutoRefresh();
                    CustomDialogForMessages.closeDialog();
                    saveDataInDataBase(body.getScheduleLocationTask());
                } else {
                    pullToRefresh.setVisibility(View.GONE);
                    recyclerViewData.setVisibility(View.GONE);
                    textViewNoData.setVisibility(View.VISIBLE);
                    doTheAutoRefresh();
                }
                firstTime = true;
            } else {
                recyclerViewData.setVisibility(View.GONE);
                textViewNoData.setVisibility(View.VISIBLE);
                pullToRefresh.setVisibility(View.GONE);
                if (!firstTime) {
                    CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
                }
                firstTime = true;
            }

        }
    }

    private void saveDataInDataBase(List<ScheduleLocationTask> schedule) {
        //  dataBaseHelper.dropTAskTable(this);
        dataBaseHelper.updateScheduleLocationTask(schedule);
    }

    private void setTaskWiseAdapter(List<ScheduleLocationTask> scheduleLocationTask) {
        if (scheduleLocationTask.size() > 0) {
            pullToRefresh.setVisibility(View.VISIBLE);
            recyclerViewData.setVisibility(View.VISIBLE);
            textViewNoData.setVisibility(View.GONE);
            taskWiseAdapter = new TaskWiseAdapter(this, scheduleLocationTask, Location);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerViewData.setLayoutManager(mLayoutManager);
            recyclerViewData.setItemAnimator(new DefaultItemAnimator());
            recyclerViewData.setAdapter(taskWiseAdapter);
        } else {
            recyclerViewData.setVisibility(View.GONE);
            pullToRefresh.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }

    }

    private void setActionBarData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(LocationName);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getIntentData() {
        ScheduleId = getIntent().getStringExtra(Constants.SCHEDULE_ID);
        Location = getIntent().getStringExtra(Constants.LOCATION);
        LocationName = getIntent().getStringExtra(Constants.LOCATION_Name);
        EmpId = getIntent().getStringExtra(Constants.EmpID);
    }

    private void onRefreshListener() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    getTaskLocationWiseData();
                } else {
                    CustomToast.showToast(TaskWiseActivity.this, getString(R.string.no_internet_connection));
                }
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void doTheAutoRefresh() {
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            CustomDialogForMessages.closeDialog();
            try {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getTaskLocationWiseData();
                        doTheAutoRefresh();
                    }
                }, 10000);
            } catch (NullPointerException e) {
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
                //handler = add_new Handler();
            } catch (Exception e) {
            }
        } else {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
        }
    }

    public static MenuItem menuitemfilter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        menuitem = menu.findItem(R.id.action_add_new);
        menuitemfilter = menu.findItem(R.id.action_filter);
        menuitem.setVisible(false);
        menuitemfilter.setVisible(false);
        MenuItem action_LoadMore = menu.findItem(R.id.action_LoadMore);
        action_LoadMore.setVisible(false);   return true;
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
                DashBoardFragment.instance = null;  NavigationActivity.activity = null;
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

    public void changePassword() {
        final String userId = Preferance.getUserId(this);

        final EditText edtoldpassword, edtnewpassword, edtconfirmpassword;
        Button tvChangepass;

        dialogChangePassword = new Dialog(this);
        dialogChangePassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChangePassword.setContentView(R.layout.dialog_changepassword);

        Window window = dialogChangePassword.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edtoldpassword = dialogChangePassword.findViewById(R.id.edt_oldpassword);
        edtnewpassword = dialogChangePassword.findViewById(R.id.edt_oldnewpassword);
        edtconfirmpassword = dialogChangePassword.findViewById(R.id.edt_confirmpassword);
        linearLayoutContainer = dialogChangePassword.findViewById(R.id.linearLayoutContainer);

        tvChangepass = dialogChangePassword.findViewById(R.id.tv_changepassword);
        if (Preferance.getTheme(this).equals("ORANGE")) {
            linearLayoutContainer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            edtoldpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round,null));
            edtnewpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round,null));
            edtconfirmpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round,null));
            tvChangepass.setBackground(getResources().getDrawable(R.drawable.button_background,null));
        } else if (Preferance.getTheme(getApplicationContext()).equals("BLUE")) {
            linearLayoutContainer.setBackgroundColor(getResources().getColor(R.color.colorAccentBlue));
            edtoldpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue,null));
            edtnewpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue,null));
            edtconfirmpassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue,null));
            tvChangepass.setBackground(getResources().getDrawable(R.drawable.button_background_blue,null));
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
        doTheAutoRefresh();
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

    public static TaskWiseActivity getInstance() {
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
}
