package com.assettagging.test.view.login;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.WebServer.APIClient;
import com.assettagging.test.model.WebServer.APIInterface;
import com.assettagging.test.model.all_user.AllUSerData;
import com.assettagging.test.model.all_user.UserList;
import com.assettagging.test.model.login.ForgotUser;
import com.assettagging.test.model.login.Login;
import com.assettagging.test.model.login.LoginUser;
import com.assettagging.test.model.login.ResetUser;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.custom_control.CustomDialogForMessages;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.custom_control.CustomViews;
import com.assettagging.test.view.custom_control.Custom_Button;
import com.assettagging.test.view.custom_control.KeyboardHideOrShow;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.view.splash.SplashActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements CustomViews {
    @BindView(R.id.editTextUserPin)
    EditText editTextUserPin;
    @BindView(R.id.editTextUserPassword)
    EditText editTextUserPassword;
    @BindView(R.id.textViewSignIn)
    TextView textViewSignIn;
    @BindView(R.id.textViewForgotPassword)
    TextView textViewForgotPassword;
    @BindView(R.id.editTextEmailId)
    EditText editTextEmailId;
    @BindView(R.id.linearLayoutForgotPassword)
    LinearLayout linearLayoutForgotPassword;
    @BindView(R.id.linearLayoutBackground)
    ConstraintLayout linearLayoutBackground;
    @BindView(R.id.linearLayoutSignIn)
    LinearLayout linearLayoutSignIn;
    @BindView(R.id.buttonReset)
    Button buttonReset;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    private boolean mIsBackVisible = false;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    String signInCLicked = "1", changeURL = "2";
    private DataBaseHelper dbHelper;
    private static Activity activity;

    public static final String ipaddressPrefrence = "IPAddress Prefrence";
    private Dialog dialog;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        KeyboardHideOrShow.showKeyboard(this);
        setContentView(R.layout.activity_login);
        gson = new Gson();
        ButterKnife.bind(this);
        if (Preferance.getTheme(this).equals("ORANGE")) {
            editTextUserPin.setBackground(getResources().getDrawable(R.drawable.edittext_background, null));
            editTextUserPassword.setBackground(getResources().getDrawable(R.drawable.edittext_background, null));
            editTextEmailId.setBackground(getResources().getDrawable(R.drawable.edittext_background, null));
            buttonLogin.setBackground(getResources().getDrawable(R.drawable.button_background, null));
            linearLayoutBackground.setBackgroundResource(R.mipmap.background);
        } else if (Preferance.getTheme(this).equals("BLUE")) {
            editTextUserPin.setBackground(getResources().getDrawable(R.drawable.edittext_background_blue, null));
            editTextUserPassword.setBackground(getResources().getDrawable(R.drawable.edittext_background_blue, null));
            editTextEmailId.setBackground(getResources().getDrawable(R.drawable.edittext_background_blue, null));
            buttonLogin.setBackground(getResources().getDrawable(R.drawable.button_background_blue, null));
            linearLayoutBackground.setBackgroundResource(R.mipmap.background_blue);
        }
        dbHelper = new DataBaseHelper(this);

        overridePendingTransition(0, 0);
        textViewSignIn.setText(Html.fromHtml("<u>" + getString(R.string.sign_in) + "</u>"));
        textViewSignIn.setPaintFlags(textViewSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //getSupportActionBar().hide();
        loadAnimations();
        changeCameraDistance(linearLayoutSignIn, linearLayoutForgotPassword);
        linearLayoutForgotPassword.setAlpha(0);
        linearLayoutForgotPassword.setVisibility(View.GONE);
        editTextUserPin.requestFocus();
        getUserList();

    }

    private void getUserList() {
        Call<AllUSerData> call = MyApplication.apiInterface.getAllUser();
        call.enqueue(new Callback<AllUSerData>() {
            @Override
            public void onResponse(Call<AllUSerData> call, Response<AllUSerData> response) {
                finishProgress();
                setAllUserData(response.body());

            }

            @Override
            public void onFailure(Call<AllUSerData> call, Throwable t) {
                finishProgress();
                setAllUserData(null);

            }
        });
    }

    private void setAllUserData(AllUSerData body) {
        if (body != null) {
            if (body.getStatus().equals("success")) {
                String json = gson.toJson(body.getUserList());
                Preferance.setUserList(LoginActivity.this, json);
            } else {
            }
        } else {
        }
    }


    public void onLoginInClick(View view) {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        try {
            if (editTextUserPin.getText().toString().trim().equals("")) {
                editTextUserPin.setError(getString(R.string.enter_user_name));
            } else if (editTextUserPassword.getText().toString().trim().equals("")) {
                editTextUserPassword.setError(getString(R.string.enter_password));
            } else {
                if (CheckInternetConnection.isInternetConnected(getApplicationContext())) {
                    doLogin(editTextUserPin.getText().toString().trim(), editTextUserPassword.getText().toString().trim());
                } else {
                    doLoginOffline();

                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private void doLoginOffline() {
        List<UserList> userLists = new ArrayList<>();
        String connectionsJSONString = Preferance.getUserList(this);
        Type type = new TypeToken<List<UserList>>() {
        }.getType();
        userLists = new Gson().fromJson(connectionsJSONString, type);
        for (int i = 0; i < userLists.size(); i++) {
            if (userLists.get(i).getUserPin().equals(editTextUserPin.getText().toString().trim()) && userLists.get(i).getPassword().equals(editTextUserPassword.getText().toString().trim())) {
                Preferance.saveUserId(this, userLists.get(i).getUserId());
                Preferance.saveUserName(this, userLists.get(i).getUserName());
                Preferance.saveEmpId(this, userLists.get(i).getEMPID());
                Intent intent = new Intent(this, NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                finish();
                LoginActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        }


    }

    private void doLogin(String userPin, String password) {
        startProgress();
        LoginUser loginUser = new LoginUser(userPin, password);
        Call<Login> call = MyApplication.apiInterface.doLogin(loginUser);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                finishProgress();
                try {
                    setLoginResponse(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                finishProgress();
                try {
                    setLoginResponse(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setLoginResponse(Login body) throws IOException {
        if (body == null) {
            CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
        } else {
            if (body.getStatus().equals("success")) {
                CustomToast.showToast(this, getString(R.string.login_successfully));
                Preferance.saveUserName(this, body.getUserName());
                Preferance.saveUserId(this, body.getUserId());
                Preferance.saveIsAllowTagUpdate(this, body.getIsAllowTagUpdate());
                //  SaveDatainDataBase(body);
                Intent intent = new Intent(this, NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                finish();
                LoginActivity.this.finish();
                finish();
                overridePendingTransition(0, 0);
            } else {
                CustomDialogForMessages.showMessageAlert(this, body.getStatus(), body.getMessage());
            }

        }
    }


    @Override
    public void startProgress() {
        CustomProgress.startProgress(this);
    }

    @Override
    public void finishProgress() {
        CustomProgress.endProgress();

    }

    public void onSignInClick(View view) {
        if (!signInCLicked.equals("1")) {
            textViewSignIn.setText(Html.fromHtml("<u>" + getString(R.string.sign_in) + "</u>"));
            textViewSignIn.setPaintFlags(textViewSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textViewForgotPassword.setText(getString(R.string.forgotPassword));
            textViewForgotPassword.setPaintFlags(0);
            textViewSignIn.setTextColor(getResources().getColor(R.color.white));
            textViewForgotPassword.setTextColor(getResources().getColor(R.color.black));
            flipCard();
            editTextUserPin.requestFocus();
            signInCLicked = "1";
        }
    }

    public void onForgotPasswordInClick(View view) {
        if (signInCLicked.equals("1")) {
            textViewSignIn.setText(getString(R.string.sign_in));
            textViewSignIn.setPaintFlags(0);
            textViewForgotPassword.setText(Html.fromHtml("<u>" + getString(R.string.forgotPassword) + "</u>"));
            textViewForgotPassword.setPaintFlags(textViewForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textViewSignIn.setTextColor(getResources().getColor(R.color.black));
            textViewForgotPassword.setTextColor(getResources().getColor(R.color.white));
            editTextEmailId.requestFocus();
            flipCard();
            signInCLicked = "";
        }
    }

    public void onResetPasswordInClick(View view) {
        if (editTextEmailId.getText().toString().trim().equals("")) {
            editTextEmailId.setError(getString(R.string.enter_email_id));
        } else {
            doResetPassword(editTextEmailId.getText().toString().trim());
        }
    }

    private void doResetPassword(String UserName) {
        startProgress();
        ForgotUser forgotUser = new ForgotUser(UserName);
        Call<ResetUser> call = MyApplication.apiInterface.doResetUser(forgotUser);
        call.enqueue(new Callback<ResetUser>() {
            @Override
            public void onResponse(Call<ResetUser> call, Response<ResetUser> response) {
                finishProgress();
                setResetUserResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResetUser> call, Throwable t) {
                finishProgress();
                setResetUserResponse(null);

            }
        });
    }

    private void setResetUserResponse(ResetUser body) {
        if (body == null) {
            CustomDialogForMessages.showMessageAlert(this, getString(R.string.failure), getString(R.string.something_bad_happened));
        } else {
            if (body.getSendEmailResult().getStatus().equals("Success")) {
                CustomToast.showToast(this, getString(R.string.emailSentSuccessFully));
                editTextEmailId.setText("");
            } else {
                CustomDialogForMessages.showMessageAlert(this, body.getSendEmailResult().getStatus(), body.getSendEmailResult().getMessage());
            }

        }
    }

    public void flipCard() {
        if (!mIsBackVisible) {
            linearLayoutForgotPassword.setVisibility(View.VISIBLE);
            mSetRightOut.setTarget(linearLayoutSignIn);
            mSetLeftIn.setTarget(linearLayoutForgotPassword);
            mSetRightOut.start();
            mSetLeftIn.start();

//            editTextEmailId.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(editTextEmailId, InputMethodManager.SHOW_IMPLICIT);
//            userEmailLayout.setVisibility(View.VISIBLE);
//            userIdLayout.setVisibility(View.GONE);

            mIsBackVisible = true;

        } else {
            linearLayoutSignIn.setVisibility(View.VISIBLE);
            mSetRightOut.setTarget(linearLayoutForgotPassword);
            mSetLeftIn.setTarget(linearLayoutSignIn);
            mSetRightOut.start();
            mSetLeftIn.start();

//            edt_userId.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(edt_userId, InputMethodManager.SHOW_IMPLICIT);
//            userEmailLayout.setVisibility(View.GONE);
//            userIdLayout.setVisibility(View.VISIBLE);

            mIsBackVisible = false;
        }
    }

    private void changeCameraDistance(View linearLayoutSignIn, View linearLayoutForgotPassword) {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        linearLayoutSignIn.setCameraDistance(scale);
        linearLayoutForgotPassword.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetRightOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {


            }
        });
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }

    public void onChangeUrl(View view) {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_change_url);

        Custom_Button updateIp = dialog.findViewById(R.id.updateIp);
        final EditText ipadd = dialog.findViewById(R.id.ipadd);
        final EditText editPort = dialog.findViewById(R.id.editPort);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        updateIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ip = ipadd.getText().toString();
                    String port = editPort.getText().toString();
                    if (!ip.equals("") || !ip.equals(null)) {
                        if (!port.equals("") || !port.equals(null)) {
//                        Preferance.saveIpAddress(LoginActivity.this,ip,port);
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(ipaddressPrefrence, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putString("IP", ip);
                            editor.putString("Port", port);
                            editor.apply();
                            MyApplication.apiInterface = APIClient.getClient().create(APIInterface.class);

//                        editor.commit();
                            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                            }
                            finish();
                            LoginActivity.this.finish();

                        }
                    }
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    finish();
                    LoginActivity.this.finish();

                }
            }
        });
        dialog.show();
    }
}