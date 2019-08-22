package com.assettagging.test.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.assettagging.test.controller.Constants;
import com.assettagging.test.model.asset_detai.SaveAssets;
import com.assettagging.test.view.assetdisposer.existing.AddAssetDetailActivity;
import com.assettagging.test.view.login.LoginActivity;
import com.assettagging.test.view.schedule_detail.ScheduleDetailActivity;

public class Preferance {
    private static String SHARED_PREF_NAME = "ASSET_TAGGING_PREF";
    private static String USERNAME = "USERNAME";
    private static String USERID = "USERID";
    private static String THEME = "THEME";
    private static String SHARED_PREF_NAME_THEME = "THEME";
    private static String EMP_ID = "EMP_ID";
    public static String ALL_DATA = "ALL_DATA";
    private static String USER_LIST = "USER_LIST";
    private static String CHECKED_LIST = "CHECKED_LIST";
    private static String UPDATE_TAG_LIST = "UPDATE_TAG_LIST";
    private static String ASSETS_LIST = "ASSETS_LIST";
    private static String IS_ALLOW_TAG_UPDATE = "IS_ALLOW_TAG_UPDATE";


    public static boolean saveUserName(Activity activity, String userName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, userName);
        editor.apply();
        return true;
    }

    public static String getUserName(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, "");
    }


    public static boolean saveUserId(Activity activity, String userId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, userId);
        editor.apply();
        return true;
    }

    public static String getUserId(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERID, "");
    }

    public static boolean saveEmpId(Activity activity, String empId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMP_ID, empId);
        editor.apply();
        return true;
    }

    public static String getEmpId(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMP_ID, "");
    }

    public static void clearPreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesTheme = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sharedPreferencesTheme.edit().clear().apply();
    }

    public static boolean saveActionCount(Activity activity, String actionCounnt) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.Action_Count, actionCounnt);
        editor.apply();
        return true;
    }


    public static String getActionCount(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.Action_Count, "");
    }

    public static boolean saveTheme(Activity activity, String actionCounnt) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME, actionCounnt);
        editor.apply();
        return true;
    }

    public static String getTheme(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(THEME, "BLUE");
    }


    public static boolean saveAllDAta(Activity activity, String allData) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_DATA, allData);
        editor.apply();
        return true;
    }

    public static String getAllDAta(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ALL_DATA, "");
    }

    public static boolean setUserList(Activity activity, String userList) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_LIST, userList);
        editor.apply();
        return true;
    }

    public static String getUserList(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_LIST, "");
    }

    public static boolean saveCheckdedList(Activity activity, String checkedJson) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHECKED_LIST, checkedJson);
        editor.apply();
        return true;
    }

    public static String getCheckdedList(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CHECKED_LIST, "");
    }

    public static boolean saveUpdateTagList(Activity activity, String updateTagJson) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UPDATE_TAG_LIST, updateTagJson);
        editor.apply();
        return true;
    }

    public static String getUpdateTagList(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UPDATE_TAG_LIST, "");
    }

    public static boolean saveAddDisposalAssetslist(Activity activity, String saveAssets) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ASSETS_LIST, saveAssets);
        editor.apply();
        return true;
    }

    public static String getAddDisposalAssetslist(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ASSETS_LIST, "");
    }

    public static boolean saveIsAllowTagUpdate(Activity activity, String isAllowTagUpdate) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_ALLOW_TAG_UPDATE, isAllowTagUpdate);
        editor.apply();
        return true;
    }

    public static String getIsAllowTagUpdate(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_ALLOW_TAG_UPDATE, "");
    }
}
