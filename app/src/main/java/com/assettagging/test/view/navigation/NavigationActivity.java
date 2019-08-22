package com.assettagging.test.view.navigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.all_data.ActvityCount;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.all_data.GetAllData_USer;
import com.assettagging.test.model.login.ChangePassword;
import com.assettagging.test.model.login.UserChangePass;
import com.assettagging.test.model.schedule.ScheduleData;
import com.assettagging.test.model.schedule.UserSchedule;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.assetdisposer.DisposerFragmnet;
import com.assettagging.test.view.assetdisposer.completed.CompletedAssetsFragment;
import com.assettagging.test.view.assetdisposer.existing.ExistingAssetsFragment;
import com.assettagging.test.view.assetdisposer.yet_to_submit.YetToSubmitDisposerFragment;
import com.assettagging.test.view.custom_control.CustomDialogForMessages;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.dashboard.DashBoardFragment;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.schedule.ScheduleFragmnet;
import com.assettagging.test.view.schedule.upcoming.UpcomingFragment;
import com.assettagging.test.view.schedule_detail.ScheduleDetailActivity;
import com.assettagging.test.view.tag_update.TagUpdatedFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    TextView textViewName;
    TextView textViewEmployeeName;
    LinearLayout linearLayoutNav;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;

    private DataBaseHelper dataBaseHelper;
    private Dialog dialogChangePassword;
    private Call<ChangePassword> call;
    public static boolean firstTime = false;
    public static Handler handler = new Handler();
    static NavigationActivity instance;
    private LinearLayout linearLayoutContainer;
    public static MenuItem menuitem, menuitemfilter, action_LoadMore;
    private String UserId;
    public static Activity activity;
    private ShowcaseView showCaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        activity = NavigationActivity.this;
        UserId = Preferance.getUserId(this);
        View header = navigationView.getHeaderView(0);
        textViewEmployeeName = header.findViewById(R.id.textViewEmployeeName);
        textViewName = header.findViewById(R.id.textViewName);
        linearLayoutNav = header.findViewById(R.id.linearLayoutNav);
        scheduleData = new ScheduleData();
        instance = this;
        dataBaseHelper = new DataBaseHelper(this);
        setAppBar();
        showFragment(new DashBoardFragment(), getString(R.string.dashboard));
        navigationView.getMenu().getItem(0).setChecked(true);
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            if (DashBoardFragment.getInstance() != null || ScheduleFragmnet.getInstance() != null)
                getScheduleData();
        } else {
            getDataFromDatabase();
        }
        if (TagUpdatedFragment.instance == null) {
            onRefreshListener();
        } else {
            pullToRefresh.setRefreshing(false);
        }
        if (Preferance.getIsAllowTagUpdate(this).equals("True")) {
            navigationView.getMenu().getItem(4).setVisible(true);
        } else {
            navigationView.getMenu().getItem(4).setVisible(false);
        }
    }

    Gson gson = new Gson();

    public void getDataFromDatabase() {
        String json = Preferance.getAllDAta(NavigationActivity.this);
        if (json.trim().length() > 0) {
            AllData allData = gson.fromJson(json, AllData.class);
            scheduleData.setActvityCount(allData.getActvityCount());
            scheduleData.setUpcomingSchedule(allData.getUpcomingSchedule());
            scheduleData.setOngoingSchedule(allData.getOngoingSchedule());
            setupViewPager(scheduleData);
        } else {
            CustomToast.showToast(this, getString(R.string.no_internet_connection));
        }
    }

    public static NavigationActivity getInstance() {
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
                        CustomProgress.endProgress();

                    }

                    @Override
                    public void onFailure(Call<SaveTracking> call, Throwable t) {
                        CustomProgress.endProgress();
                    }
                });
            }

        }
    }

    private void onRefreshListener() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                        handler = null;
                    }
                    if (DashBoardFragment.getInstance() != null || ScheduleFragmnet.getInstance() != null)
                        if (ScheduleDetailActivity.getInstance() == null)
                            getScheduleData();
                    if (ExistingAssetsFragment.getInstance() != null) {
                        ExistingAssetsFragment.getInstance().getAssetDisposalData();
                    }
                } else {
                }
                pullToRefresh.setRefreshing(false);

            }
        });
    }

    Call<ScheduleData> callScheduleData;

    public void getScheduleData() {
        if (!firstTime) {
            CustomProgress.startProgress(NavigationActivity.this);
        }
        String userId = Preferance.getUserId(NavigationActivity.this);
        UserSchedule userSchedule = new UserSchedule(userId);
        callScheduleData = MyApplication.apiInterface.getUserSchedule(userSchedule);
        callScheduleData.enqueue(new Callback<ScheduleData>() {
            @Override
            public void onResponse(Call<ScheduleData> call, Response<ScheduleData> response) {
                if (!firstTime) {
                    CustomProgress.endProgress();
                }
                setScheduleResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScheduleData> call, Throwable t) {
                if (!firstTime) {
                    CustomProgress.endProgress();
                }
                setScheduleResponse(null);
            }
        });
    }


    private void setScheduleResponse(ScheduleData body) {
        if (body == null) {
            if (!firstTime) {
                CustomDialogForMessages.showMessageAlert(NavigationActivity.this, getString(R.string.failure), getString(R.string.something_bad_happened));
            }
        } else {
            if (!firstTime) {
                String json1 = Preferance.getAllDAta(NavigationActivity.this);
                if (json1.trim().length() > 0) {
                    AllData allData = gson.fromJson(json1, AllData.class);
                    if (allData == null) {
                        showInfoView();
                    }
                } else {
                    showInfoView();
                }
            }
            Type type = new TypeToken<List<ActvityCount>>() {
            }.getType();
            String json = gson.toJson(body.getActvityCount(), type);
            Preferance.saveActionCount(this, json.toString());
            setupViewPager(body);

            if (handler == null) {
                handler = new Handler();
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    doTheAutoRefresh();
                }
            }
            if (body.getStatus().equals("success")) {
                CustomDialogForMessages.closeDialog();
                firstTime = true;
            } else {
                if (!firstTime) {
                    CustomDialogForMessages.showMessageAlert(NavigationActivity.this, body.getStatus(), body.getMessage());
                }
                firstTime = true;

            }

        }
    }

    public ScheduleData scheduleData;

    public ScheduleData setupViewPager(ScheduleData body) {
        scheduleData = body;
        if (ScheduleFragmnet.getInstance() != null) {
            ScheduleFragmnet.getInstance().setupViewPager(scheduleData, firstTime);
        }
        if (DashBoardFragment.getInstance() != null) {
            DashBoardFragment.getInstance().setdata(scheduleData);
        }

        return scheduleData;
    }

    public void doTheAutoRefresh() {
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            CustomDialogForMessages.closeDialog();
            try {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (DashBoardFragment.getInstance() != null || ScheduleFragmnet.getInstance() != null)
                            if (ScheduleDetailActivity.getInstance() == null) getScheduleData();
                        CustomProgress.endProgress();
                        doTheAutoRefresh();


                    }
                }, 10000);
            } catch (Exception e) {
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
                //handler = add_new Handler();
            }
        } else {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
        }
    }


    private void setAppBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                if (newState == drawer.getMeasuredState()) {
                    if (DashBoardFragment.getInstance() != null) {
                        if (DashBoardFragment.getInstance().popupWindow != null) {
                            DashBoardFragment.getInstance().popupWindow.dismiss();
                            DashBoardFragment.getInstance().popupWindow = null;
                            DashBoardFragment.getInstance().opened = false;
                        }
                    }
                }
            }
        });
        if (Preferance.getTheme(getApplicationContext()).equals("ORANGE")) {
            linearLayoutNav.setBackground(getResources().getDrawable(R.drawable.nav_header_drawable, null));
        } else if (Preferance.getTheme(getApplicationContext()).equals("BLUE")) {
            linearLayoutNav.setBackground(getResources().getDrawable(R.drawable.nav_header_drawable_blue, null));
        }
        navigationView.setNavigationItemSelectedListener(this);
        //  textViewName.setText(Preferance.getUserName(this));
        textViewEmployeeName.setText(Preferance.getUserName(this));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onPause() {
        Log.d("onPause", "");
        super.onPause();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onResume() {
        Log.d("onResume", "");
        super.onResume();
        handler = new Handler();
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            doTheAutoRefresh();
        }

    }

    Menu menus;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menus = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        menuitem = menu.findItem(R.id.action_add_new);
        menuitemfilter = menu.findItem(R.id.action_filter);
        action_LoadMore = menu.findItem(R.id.action_LoadMore);
        if (CheckInternetConnection.isInternetConnected(NavigationActivity.this)) {
            action_LoadMore.setVisible(true);
        } else {
            action_LoadMore.setVisible(false);
        }


        menuitemfilter.setVisible(false);
        menuitem.setVisible(false);

        return true;
    }

    private void showInfoView() {
        final Handler handlerShowCaseView = new Handler();
        handlerShowCaseView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (showCaseView != null) {
                    showCaseView.hide();
                    showCaseView = null;
                    handlerShowCaseView.removeCallbacksAndMessages(null);
                }
            }
        }, 2500);

        if (toolbar.getMenu().size() == 0) {
            return;
        }
        MenuItem item = toolbar.getMenu().getItem(0);
        try {
            showCaseView = new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(new ViewTarget(item.getItemId(), NavigationActivity.this))
                    .setContentTitle("Click \"Load Data\" button to load data for Offline use")
                    .setStyle(R.style.CustomShowcaseTheme)
                    .build();
            showCaseView.hideButton();
            showCaseView.show();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    MenuItem items;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                Preferance.clearPreference(this);
                NavigationActivity.activity = null;
                //   NavigationActivity.activity.finish();
                ScheduleFragmnet.instance = null;
                DashBoardFragment.instance = null;
                finish();
                dataBaseHelper.dropAssetandDisposalTable();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_change_password:
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    changePassword();
                } else {
                    CustomToast.showToast(this, getString(R.string.if_you_want_to_change_pass_connect_net));
                }
                return true;

            case R.id.action_LoadMore:
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    getAllData();
                } else {
                    CustomToast.showToast(this, getString(R.string.no_internet_connection));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getAllData() {
        CustomProgress.startProgress(NavigationActivity.this);
        String userId = Preferance.getUserId(this);
        GetAllData_USer getAllData_uSer = new GetAllData_USer(userId);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        Call<AllData> call = MyApplication.apiInterface.getAllData(getAllData_uSer);
        call.enqueue(new Callback<AllData>() {
            @Override
            public void onResponse(Call<AllData> call, Response<AllData> response) {
                setAllDataResponse(response.body());
            }

            @Override
            public void onFailure(Call<AllData> call, Throwable t) {
                setAllDataResponse(null);

            }
        });
    }

    private void setAllDataResponse(AllData body) {
        CustomProgress.endProgress();
        if (body != null) {
            handler = new Handler();
            if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                doTheAutoRefresh();
            }
            if (body.getStatus().equals("success")) {
                String allDataJson = gson.toJson(body);
                Preferance.saveAllDAta(this, allDataJson);
                for (int i = 0; i < body.getScheduleDetail().size(); i++) {
                    saveImagesToDevice(body.getScheduleDetail().get(i).getImagePath(), body.getScheduleDetail().get(i).getASSETID());
                }
            } else {
                CustomToast.showToast(this, getString(R.string.something_bad_happened));
            }
        } else {
            CustomToast.showToast(this, getString(R.string.something_bad_happened));
        }

    }

    private void saveImagesToDevice(String body, String assetid) {
        try {
//                http://192.168.1.23:810/api/DownloadMandate/PhysicalMandate
            URL url = new URL(body);//1
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            try {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.flush();
                writer.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String msg = conn.getResponseMessage();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                        For PDF Download

                try {
                    int fileLength = conn.getContentLength();
                    InputStream input = new BufferedInputStream(conn.getInputStream());
                    OutputStream output = new FileOutputStream("/sdcard/Download/" + assetid + ".jpg");

                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        Bundle resultData = new Bundle();
                        resultData.putInt("progress", (int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        CustomProgress.startProgress(this);
        UserChangePass userChangePass = new UserChangePass(UserId, Password);
        if (call != null) {
            call.cancel();
            call = null;
        }
        call = MyApplication.apiInterface.getChangePassword(userChangePass);
        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                CustomProgress.endProgress();
                setChangePasswordResponse(response.body());
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                CustomProgress.endProgress();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (DashBoardFragment.getInstance() != null) {
            if (DashBoardFragment.getInstance().popupWindow != null) {
                DashBoardFragment.getInstance().popupWindow.dismiss();
                DashBoardFragment.getInstance().popupWindow = null;
                DashBoardFragment.getInstance().opened = false;
            }
        }
        int id = item.getItemId();
        if (id == R.id.nav_Dashboard) {
            showFragment(new DashBoardFragment(), getString(R.string.dashboard));
            ScheduleFragmnet.instance = null;
            DisposerFragmnet.instance = null;
            YetToSubmitDisposerFragment.instance = null;
            ExistingAssetsFragment.instance = null;
            CompletedAssetsFragment.instance = null;
            menuitem.setVisible(false);
            pullToRefresh.setEnabled(true);
            action_LoadMore.setVisible(true);
        } else if (id == R.id.nav_Schedules) {
            showFragment(new ScheduleFragmnet(), getString(R.string.daily_schedule));
            DisposerFragmnet.instance = null;
            DashBoardFragment.instance = null;
            YetToSubmitDisposerFragment.instance = null;
            ExistingAssetsFragment.instance = null;
            CompletedAssetsFragment.instance = null;
            menuitem.setVisible(false);
            pullToRefresh.setEnabled(true);
            action_LoadMore.setVisible(false);
        } else if (id == R.id.nav_disposer) {
            showFragment(new DisposerFragmnet(), getString(R.string.assetDisposer));
            DashBoardFragment.instance = null;
            ScheduleFragmnet.instance = null;
            menuitem.setVisible(true);
            pullToRefresh.setEnabled(true);
            action_LoadMore.setVisible(false);
        } else if (id == R.id.tag_update) {
            showFragment(new TagUpdatedFragment(), getString(R.string.tagupdate));
            DashBoardFragment.instance = null;
            ScheduleFragmnet.instance = null;
            DisposerFragmnet.instance = null;
            pullToRefresh.setRefreshing(false);
            pullToRefresh.setEnabled(false);
            YetToSubmitDisposerFragment.instance = null;
            ExistingAssetsFragment.instance = null;
            CompletedAssetsFragment.instance = null;
            menuitem.setVisible(false);
            menuitemfilter.setVisible(false);
            action_LoadMore.setVisible(false);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment, String title) {
        getSupportActionBar().setTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}
