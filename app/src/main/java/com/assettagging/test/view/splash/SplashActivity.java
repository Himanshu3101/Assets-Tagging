package com.assettagging.test.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.Upadte_tag;
import com.assettagging.test.model.asset_detai.SaveAssets;
import com.assettagging.test.model.asset_detai.SaveAssetsOffline;
import com.assettagging.test.model.asset_detai.SaveDisposalTrack;
import com.assettagging.test.model.schedule_detail.SaveTracking;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.model.schedule_detail.UserScannedList;
import com.assettagging.test.model.user_tracking.TrackingStatus;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.assetdisposer.existing.AddAssetDetailActivity;
import com.assettagging.test.view.assetdisposer.existing.ExistingAssetsFragment;
import com.assettagging.test.view.assetdisposer.yet_to_submit.YetToSubmitDisposerFragment;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import static com.assettagging.test.controller.Constants.SCHEDULE_ID;
import static com.assettagging.test.controller.Constants.SCHEDULE_NAME;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.relativeLayoutContainer)
    RelativeLayout relativeLayoutContainer;
    private CountDownTimer cdt;
    int i = 0;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        dbHelper = new DataBaseHelper(this);
        ButterKnife.bind(this);
        if (Preferance.getTheme(this).equals("ORANGE")) {
            relativeLayoutContainer.setBackgroundResource(R.mipmap.background);
        } else if (Preferance.getTheme(this).equals("BLUE")) {
            relativeLayoutContainer.setBackgroundResource(R.mipmap.background_blue);
        }

        if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
            progressBar.setVisibility(View.GONE);
            progressBar2.setVisibility(View.VISIBLE);
            doOfflineWork();
            doUpdateDisposalAssetOffline();
            doUpdateTagWork();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            progressBar2.setVisibility(View.GONE);
            doAnimation();
        }

    }

    private void doUpdateTagWork() {
        List<Upadte_tag> UpdateTagList = new ArrayList<>();
        String UpdateTagListJson = Preferance.getUpdateTagList(this);
        Type type = new TypeToken<List<Upadte_tag>>() {
        }.getType();
        final String userId = Preferance.getUserId(SplashActivity.this);
        UpdateTagList = new Gson().fromJson(UpdateTagListJson, type);
        if (UpdateTagListJson.trim().length() > 0) {
            if (UpdateTagList.size() > 0) {
                for (int i = 0; i < UpdateTagList.size(); i++) {
                    Upadte_tag upadte_tag = UpdateTagList.get(i);
                    //  Upadte_tag upadte_tag = new Upadte_tag(userId, UpdateTagList.get(i), UpdateTagList.get(i).getProject(), UpdateTagList.get(i).getASSETID(), UpdateTagList.get(i).getBarCode());
                    Call<TrackingStatus> call = MyApplication.apiInterface.UpDateTagNo(upadte_tag);
                    final List<Upadte_tag> finalUpdateTagList = UpdateTagList;
                    final int finalI = i;
                    call.enqueue(new Callback<TrackingStatus>() {
                        @Override
                        public void onResponse(Call<TrackingStatus> call, Response<TrackingStatus> response) {
                            CustomProgress.endProgress();
                            if (response.body().getStatus().equals("success")) {
                                finalUpdateTagList.remove(finalI);
                                String saveUpdateTag = new Gson().toJson(finalUpdateTagList);
                                Preferance.saveUpdateTagList(SplashActivity.this, "");
                                if (finalUpdateTagList.size() > 0) {
                                    Preferance.saveUpdateTagList(SplashActivity.this, saveUpdateTag);
                                }
                                final String userId = Preferance.getUserId(SplashActivity.this);
                                if (userId.equals("")) {
                                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();

                                } else {
                                    Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();

                                }
                                finish();
                            } else {
                                final String userId = Preferance.getUserId(SplashActivity.this);
                                if (userId.equals("")) {
                                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();

                                } else {
                                    Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();

                                }
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<TrackingStatus> call, Throwable t) {
                            CustomProgress.endProgress();

                        }
                    });
                }
            } else {
                if (userId.equals("")) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                } else {
                    Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                }
                finish();
            }
        } else {
            if (userId.equals("")) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();

            } else {
                Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();

            }
            finish();
        }
    }

    private void doOfflineWork() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String checkintime = df.format(c);
        String checkedListJson = Preferance.getCheckdedList(this);
        Type type = new TypeToken<List<ScheduleDetail_>>() {
        }.getType();
        final List<ScheduleDetail_> checkedList = new Gson().fromJson(checkedListJson, type);
        if (checkedListJson.trim().length() > 0) {
            if (checkedList.size() > 0) {
                for (int i = 0; i < checkedList.size(); i++) {
                    String newString = checkedListJson.toString().replace("\"", "\'");
                    String userId = Preferance.getUserId(this);
                    UserScannedList userScheduleDetail = new UserScannedList(userId, "", checkintime, checkedList.get(i).getSCHEDULEID(), newString);
                    final List<ScheduleDetail_> finalCheckedList = checkedList;
                    final int finalI = i;
                    Call<SaveTracking> call = MyApplication.apiInterface.getSaveTracking(userScheduleDetail);
                    final int finalI1 = i;
                    call.enqueue(new Callback<SaveTracking>() {
                        @Override
                        public void onResponse(Call<SaveTracking> call, Response<SaveTracking> response) {
                            CustomProgress.endProgress();
                            if (response.body().getStatus().equals("success")) {
                                finalCheckedList.remove(finalI);
                                String saveCheckedList = new Gson().toJson(finalCheckedList);
                                Preferance.saveCheckdedList(SplashActivity.this, "");
                                if (finalCheckedList.size() > 0) {
                                    Preferance.saveCheckdedList(SplashActivity.this, saveCheckedList);
                                }
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<SaveTracking> call, Throwable t) {
                            CustomProgress.endProgress();
                        }
                    });
                }
            }
        }
    }

    private void doUpdateDisposalAssetOffline() {
        List<SaveAssetsOffline> SaveAssetsList = new ArrayList<>();
        String AddDisposalAssetslistJson = Preferance.getAddDisposalAssetslist(this);
        Type type = new TypeToken<List<SaveAssetsOffline>>() {
        }.getType();
        SaveAssetsList = new Gson().fromJson(AddDisposalAssetslistJson, type);
        String userId = Preferance.getUserId(SplashActivity.this);
        if (AddDisposalAssetslistJson.trim().length() > 0)
            if (SaveAssetsList.size() > 0)
                for (int i = 0; i < SaveAssetsList.size(); i++) {
                    SaveAssets saveAssets = new SaveAssets(userId, SaveAssetsList.get(i).getScheduleName(), SaveAssetsList.get(i).getDisposalDate(), SaveAssetsList.get(i).getScannedList(), SaveAssetsList.get(i).getType());
                    Call<SaveDisposalTrack> call = MyApplication.apiInterface.getSaveDisposalTracking(saveAssets);
                    final List<SaveAssetsOffline> finalSaveAssetsList = SaveAssetsList;
                    final int finalI = i;
                    call.enqueue(new Callback<SaveDisposalTrack>() {
                        @Override
                        public void onResponse(Call<SaveDisposalTrack> call, Response<SaveDisposalTrack> response) {
                            CustomProgress.endProgress();
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(SplashActivity.this);
                            dataBaseHelper.dropASSETSTable(SplashActivity.this, finalSaveAssetsList.get(finalI).getScheduleID());
                            dataBaseHelper.dropDisposerTable(SplashActivity.this, finalSaveAssetsList.get(finalI).getScheduleID());
                            finalSaveAssetsList.remove(finalI);
                            String saveAssetsJson = new Gson().toJson(finalSaveAssetsList);
                            Preferance.saveAddDisposalAssetslist(SplashActivity.this, "");
                            if (finalSaveAssetsList.size() > 0) {
                                Preferance.saveAddDisposalAssetslist(SplashActivity.this, saveAssetsJson);
                            }
                        }

                        @Override
                        public void onFailure(Call<SaveDisposalTrack> call, Throwable t) {
                            CustomProgress.endProgress();
                        }
                    });
                }
    }

    private void doAnimation() {
        progressBar.setProgress(i);
        cdt = new CountDownTimer(2800, 26) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                progressBar.setProgress((int) (i));
            }

            @Override
            public void onFinish() {
                //Do what you want

                progressBar.setProgress(100);
                final String userId = Preferance.getUserId(SplashActivity.this);
                if (userId.equals("")) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                } else {
                    Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();

                }
                finish();
            }
        };
        cdt.start();


    }


}
