package com.assettagging.test.view.custom_control;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.view.navigation.NavigationActivity;

public class CustomProgress {
    static ProgressDialog pb;
    static ProgressDialog pbContext;

    public static void startProgress(Activity activity) {
        if (NavigationActivity.activity != null) {
            if (CheckInternetConnection.isInternetConnected(NavigationActivity.activity.getApplicationContext())) {
                if (pb != null) {
                    pb.dismiss();
                    pb = null;
                }
                pb = ProgressDialog.show(NavigationActivity.activity, "Loading", "Please wait...", false, false);
                pb.show();
            } else {
                pb = null;
                if (pb != null) {
                    pb.dismiss();
                    pb = null;
                }
            }
        } else {
            if (CheckInternetConnection.isInternetConnected(activity.getApplicationContext())) {
                if (pb != null) {
                    pb.dismiss();
                    pb = null;
                }
                pb = ProgressDialog.show(activity, "Loading", "Please wait...", false, false);
                pb.show();
            } else {
                pb = null;
                if (pb != null) {
                    pb.dismiss();
                    pb = null;
                }
            }
        }

    }

    public static void endProgress() {
        if (pb != null) {
            pb.dismiss();
            pb = null;
        }
    }

    public static void startProgressContext(Context activity) {
        if (CheckInternetConnection.isInternetConnected(activity.getApplicationContext())) {
            if (pbContext != null) {
                pbContext.dismiss();
                pbContext = null;
            }
            pbContext = ProgressDialog.show(activity, "Loading", "Please wait...", false, false);
            pbContext.show();
        } else {
            pbContext = null;
            if (pbContext != null) {
                pbContext.dismiss();
                pbContext = null;
            }
        }
    }

    public static void endProgressContext() {
        if (pbContext != null) {
            pbContext.dismiss();
            pbContext = null;
        }
    }
}
