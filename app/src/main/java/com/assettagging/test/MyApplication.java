package com.assettagging.test;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.crashlytics.android.Crashlytics;
import com.assettagging.test.model.WebServer.APIClient;
import com.assettagging.test.model.WebServer.APIInterface;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.login.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.assettagging.test.R;


import io.fabric.sdk.android.Fabric;



public class MyApplication extends Application {

    public static APIInterface apiInterface;
    public static MyApplication instance;
    String ip = "", port = "";
    Handler handler;
    public static Context contextOfApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        contextOfApplication = getApplicationContext();
        instance = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        if (Preferance.getTheme(getApplicationContext()).equals("ORANGE")) {
            changeTheme(R.style.AppTheme);
        } else if (Preferance.getTheme(getApplicationContext()).equals("BLUE")) {
            changeTheme(R.style.AppThemeBlue);
        }


    }

    public void getAllData() {
        handler = new Handler();
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                        if (NavigationActivity.getInstance() != null)
                            NavigationActivity.getInstance().getAllData();
                }
            }, 20000);
        } catch (Exception e) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            //handler = add_new Handler();
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void changeTheme(int appTheme) {
        this.setTheme(appTheme);
    }
}
