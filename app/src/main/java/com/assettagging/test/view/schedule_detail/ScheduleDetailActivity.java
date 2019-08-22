package com.assettagging.test.view.schedule_detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.assettagging.test.model.schedule_detail.ItemCurentStatusList;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.model.schedule_detail.UserScheduleDetail;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.custom_control.CustomDialogForMessages;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.custom_control.KeyboardHideOrShow;
import com.assettagging.test.view.dashboard.DashBoardFragment;
import com.assettagging.test.view.locationwise.LocationWiseActivity;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.view.schedule.ScheduleFragmnet;
import com.assettagging.test.view.taskLocationWise.TaskWiseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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

public class ScheduleDetailActivity extends BaseActivity {
    public static JsonArray jsonList;
    private static FrameLayout container;
    public static ScheduleDetailActivity instance;
    public static List<ScheduleDetail_> checkedList = new ArrayList<>();
    String ScheduleId, Location, EmpId, ActivityType, STARTTIME, ENDTIME = "";

    @BindView(R.id.recyclerViewScheduleDetail)
    RecyclerView recyclerViewScheduleDetail;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    static ImageView imageViewExpanded;

    static EditText editTextBarCode;
    Button buttonSubmit;

    private Dialog dialogChangePassword;
    private Handler handler = new Handler();
    boolean firstTime = false;
    public static ScheduleDetailAdapter scheduleDetailAdapter;
    static JsonArray ScannedList;
    private String checkintime = "";
    static Handler mhandler;
    private DataBaseHelper dataBaseHelper;
    private static int mShortAnimationDuration;
    private static Animator mCurrentAnimator;
    private MenuItem menuitem;
    private MenuItem menuitemfilter;
    String ProjectSelected, DepartmentSelected, CostCenterSelected, SiteSelected, WorkerSelected, MainAccountSelected, OffsetAccountSelected;
    private AllData allData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardHideOrShow.hideKeyboard(this);
        setContentView(R.layout.activity_schedule_detail);
        ButterKnife.bind(this);
        instance = this;
        imageViewExpanded = findViewById(R.id.imageViewExpanded);
        container = findViewById(R.id.container);

        dataBaseHelper = new DataBaseHelper(this);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextBarCode = findViewById(R.id.editTextBarCode);

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        getIntentData();
        setActionBarData();
        getCurrentTime();
        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            getScheduleDetailData();
        } else {
            getScheduleDetailDataOffline();
        }
        onRefreshListener();
        editTextBarCodeChangeListener();
        if (ScheduleFragmnet.position == 0) {
            editTextBarCode.setVisibility(View.GONE);
        } else if (ScheduleFragmnet.position == 2) {
            editTextBarCode.setVisibility(View.GONE);
        }
        NavigationActivity.getInstance().action_LoadMore.setVisible(false);

    }

    private void getScheduleDetailDataOffline() {
        List<ScheduleDetail_> scheduleDetail_list = new ArrayList<>();
        Gson gson = new Gson();
        String json = Preferance.getAllDAta(ScheduleDetailActivity.this);
        allData = gson.fromJson(json, AllData.class);
        for (int i = 0; i < allData.getScheduleDetail().size(); i++) {
            if (allData.getScheduleDetail().get(i).getSCHEDULEID().equals(ScheduleId) && allData.getScheduleDetail().get(i).getLOCATION().equals(Location) && allData.getScheduleDetail().get(i).getACTIVITYTYPE().equals(ActivityType)) {
                scheduleDetail_list.add(allData.getScheduleDetail().get(i));
            }
        }
        String checkedListJson = Preferance.getCheckdedList(ScheduleDetailActivity.this);
        Type type = new TypeToken<List<ScheduleDetail_>>() {
        }.getType();
        List<ScheduleDetail_> checkedListOffline = new Gson().fromJson(checkedListJson, type);
        List<ScheduleDetail_> tempList = new ArrayList<>();
        if (checkedListOffline != null) {
            if (checkedListOffline.size() > 0)
                for (int i = 0; i < scheduleDetail_list.size(); i++) {
                    for (int j = 0; j < checkedListOffline.size(); j++) {
                        if (!checkedListOffline.get(j).getBarCode().equals(scheduleDetail_list.get(i).getBarCode())) {
                            tempList.add(scheduleDetail_list.get(i));
                        }
                    }
                }

        } else {
            tempList.addAll(scheduleDetail_list);
        }
        setAssetDetailAdapter(tempList, allData.getItemCurentStatusList());

    }


    private void setAssetDetailAdapter(List<ScheduleDetail_> allAssetDetail, List<ItemCurentStatusList> itemCurentStatusList) {
        if (allAssetDetail.size() > 0) {
            pullToRefresh.setVisibility(View.VISIBLE);
            recyclerViewScheduleDetail.setVisibility(View.VISIBLE);
            buttonSubmit.setVisibility(View.VISIBLE);
            textViewNoSchedule.setVisibility(View.GONE);

            scheduleDetailAdapter = new ScheduleDetailAdapter(this, allAssetDetail, itemCurentStatusList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewScheduleDetail.setLayoutManager(mLayoutManager);
            recyclerViewScheduleDetail.setItemAnimator(new DefaultItemAnimator());
            recyclerViewScheduleDetail.setAdapter(scheduleDetailAdapter);
        } else {
            pullToRefresh.setVisibility(View.GONE);
            recyclerViewScheduleDetail.setVisibility(View.GONE);
            buttonSubmit.setVisibility(View.GONE);
            textViewNoSchedule.setVisibility(View.VISIBLE);
        }

    }

    private void getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        checkintime = df.format(c);
    }

    public void editTextBarCodeChangeListener() {

        editTextBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (editTextBarCode.getText().toString().trim().equals("AA-DRG-N-000031")) {
//                    scheduleDetailAdapter.filter(s.toString());
//                    mhandler = new Handler();
//                    mhandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            editTextBarCode.setText("");
//                            editTextBarCode.requestFocus();
//                            if (mhandler != null) {
//                                mhandler.removeCallbacksAndMessages(null);
//                                mhandler = null;
//
//
//                            }
//                        }
//                    }, 2000);
//                }


                if (!editTextBarCode.getText().toString().trim().equals("")) {
                    scheduleDetailAdapter.filter(s.toString());
                    mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editTextBarCode.setText("");
                            editTextBarCode.requestFocus();
                            if (mhandler != null) {
                                mhandler.removeCallbacksAndMessages(null);
                                mhandler = null;


                            }
                        }
                    }, 2000);
                }
            }
        });

    }

    private void doTheAutoRefresh() {
        CustomDialogForMessages.closeDialog();

//        handler.postDelayed(add_new Runnable() {
//            @Override
//            public void run() {
//                // CustomToast.showToast(ScheduleDetailActivity.this, "running2");
//                getScheduleDetailData();
//                doTheAutoRefresh();
//            }
//        }, 10000);
    }

    private void onRefreshListener() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    getScheduleDetailData();
                } else {
                    CustomToast.showToast(ScheduleDetailActivity.this, getString(R.string.no_internet_connection));
                }
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public void getScheduleDetailData() {
        if (!firstTime) {
            CustomProgress.startProgressContext(this);
        }
        String userId = Preferance.getUserId(this);
        UserScheduleDetail userScheduleDetail = new UserScheduleDetail(userId, EmpId, Location, ScheduleId, ActivityType, STARTTIME, ENDTIME);
        Call<ScheduleDetail> call = MyApplication.apiInterface.getUserScheduleDetail(userScheduleDetail);
        call.enqueue(new Callback<ScheduleDetail>() {
            @Override
            public void onResponse(Call<ScheduleDetail> call, Response<ScheduleDetail> response) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                try {
                    setScheduleResponse(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                firstTime = true;
            }

            @Override
            public void onFailure(Call<ScheduleDetail> call, Throwable t) {
                if (!firstTime) {
                    CustomProgress.endProgressContext();
                }
                try {
                    setScheduleResponse(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
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

    private void setScheduleResponse(ScheduleDetail body) throws IOException {
        if (body == null) {
            CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
        } else {
            if (body.getStatus().equals("success")) {
                if (body.getScheduleDetail().size() > 0) {
                    pullToRefresh.setVisibility(View.VISIBLE);
                    recyclerViewScheduleDetail.setVisibility(View.VISIBLE);
                    textViewNoSchedule.setVisibility(View.GONE);
                    setAssetDetailAdapter(body.getScheduleDetail(), body.getItemCurentStatusList());
                    CustomDialogForMessages.closeDialog();
                    doTheAutoRefresh();
                    saveDataInDataBase(body.getScheduleDetail(), body.getItemCurentStatusList());
                    scheduleDetailAdapter.notifyDataSetChanged();
                } else {
                    pullToRefresh.setVisibility(View.GONE);
                    recyclerViewScheduleDetail.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                    editTextBarCode.setVisibility(View.GONE);
                    textViewNoSchedule.setVisibility(View.VISIBLE);
                    doTheAutoRefresh();
                }
            } else {
                if (firstTime) {
                    CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
                }
            }

        }
    }

    Gson gson = new Gson();

    private void updateDataInDataBase(List<ScheduleDetail_> checkedList) throws IOException {
        if (checkedList != null) {
            String AllDataJson = gson.toJson(checkedList);
            Preferance.saveCheckdedList(ScheduleDetailActivity.this, AllDataJson);
        }
        getScheduleDetailDataOffline();
        scheduleDetailAdapter.notifyDataSetChanged();
    }

    private void saveDataInDataBase(List<ScheduleDetail_> scheduleDetails, List<ItemCurentStatusList> itemCurentStatusList) throws IOException {
        if (scheduleDetails != null) dataBaseHelper.updateScheduleDetail(scheduleDetails);
    }

    private void setActionBarData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ActivityType);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Preferance.getTheme(this).equals("ORANGE")) {
            editTextBarCode.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round, null));
            buttonSubmit.setBackground(getResources().getDrawable(R.drawable.button_background, null));
        } else if (Preferance.getTheme(this).equals("BLUE")) {
            editTextBarCode.setBackground(getResources().getDrawable(R.drawable.edittext_background_not_round_blue, null));
            buttonSubmit.setBackground(getResources().getDrawable(R.drawable.button_background_blue, null));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        menuitem = menu.findItem(R.id.action_add_new);
        menuitemfilter = menu.findItem(R.id.action_filter);
        menuitem.setVisible(false);
        menuitemfilter.setVisible(false);
        MenuItem action_LoadMore = menu.findItem(R.id.action_LoadMore);
        action_LoadMore.setVisible(false);
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
                DashBoardFragment.instance = null;  dataBaseHelper.dropTable();
                NavigationActivity.activity = null;
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


    private void getIntentData() {
        ScheduleId = getIntent().getStringExtra(Constants.SCHEDULE_ID);
        EmpId = getIntent().getStringExtra(Constants.EmpID);
        Location = getIntent().getStringExtra(Constants.LOCATION);
        ActivityType = getIntent().getStringExtra(Constants.ACTIVITYTYPE);
        STARTTIME = getIntent().getStringExtra(Constants.STARTTIME);
        ENDTIME = getIntent().getStringExtra(Constants.ENDTIME);

    }


    public void onSubmitButtonClick(View view) throws IOException {
        if (ScannedList != null) {
            if (ScannedList.size() > 0) {
                String newString = ScannedList.toString().replace("\"", "\'");
                String userId = Preferance.getUserId(this);
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    CustomProgress.startProgressContext(ScheduleDetailActivity.this);
                    UserScannedList userScheduleDetail = new UserScannedList(userId, "", checkintime, ScheduleId, newString);
                    Call<SaveTracking> call = MyApplication.apiInterface.getSaveTracking(userScheduleDetail);
                    call.enqueue(new Callback<SaveTracking>() {
                        @Override
                        public void onResponse(Call<SaveTracking> call, Response<SaveTracking> response) {
                            CustomProgress.endProgressContext();
                            //  CustomToast.showToast(ScheduleDetailActivity.this, response.message());
                            setSaveStatus(response.body());
                        }

                        @Override
                        public void onFailure(Call<SaveTracking> call, Throwable t) {
                            CustomProgress.endProgressContext();
                            setSaveStatus(null);
                        }
                    });
                } else {
                    updateDataInDataBase(scheduleDetailAdapter.checkedList);
                    CustomDialogForMessages.showMessageAlert(this, "Success", "Data saved Successfully");

                }


            } else {
                CustomToast.showToast(this, "Please Scan QR Code");
            }
        } else {
            CustomToast.showToast(this, "QR Code not exist in this list");
        }
//        } else {
//            CustomToast.showToast(this, "Please Scan QR Code");
//
//        }
    }

    private void setSaveStatus(SaveTracking body) {
        if (body != null) {
            if (body.getStatus().equals("success")) {
                getScheduleDetailData();
                NavigationActivity.getInstance().getScheduleData();
                TaskWiseActivity.getInstance().getTaskLocationWiseData();
                LocationWiseActivity.getInstance().getLocationData();
                CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
            } else {
                getScheduleDetailData();
                NavigationActivity.getInstance().getScheduleData();
                TaskWiseActivity.getInstance().getTaskLocationWiseData();
                LocationWiseActivity.getInstance().getLocationData();
                CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
            }
        } else {
            CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
        }
    }


    public static void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

        imageViewExpanded.setImageBitmap(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        imageViewExpanded.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        imageViewExpanded.setPivotX(0f);
        imageViewExpanded.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imageViewExpanded, View.X,
                        startBounds.left + 40, finalBounds.left + 40))
                .with(ObjectAnimator.ofFloat(imageViewExpanded, View.Y,
                        startBounds.top + 40, finalBounds.top + 40))
                .with(ObjectAnimator.ofFloat(imageViewExpanded, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(imageViewExpanded,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.bottomMargin = 40;
        layoutParams.leftMargin = 40;
        layoutParams.rightMargin = 40;
        layoutParams.topMargin = 40;

        imageViewExpanded.setLayoutParams(layoutParams);
        imageViewExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(imageViewExpanded, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(imageViewExpanded,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(imageViewExpanded,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(imageViewExpanded,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        imageViewExpanded.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        imageViewExpanded.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    public static ScheduleDetailActivity getInstance() {
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
