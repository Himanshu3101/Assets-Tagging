package com.assettagging.test.view.tag_update;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.model.Upadte_tag;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.asset_detai.AssetData;
import com.assettagging.test.model.asset_detai.UserAssets;
import com.assettagging.test.model.user_tracking.TrackingStatus;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IACE on 26-Nov-18.
 */

public class TagUpdatedFragment extends Fragment {

    public static TagUpdatedFragment instance;
    View view;
    @BindView(R.id.editTextBarCode)
    EditText editTextBarCode;
    @BindView(R.id.container)
    LinearLayout linearLayoutcontainer;
    @BindView(R.id.linearLayoutBaseContainer)
    LinearLayout linearLayoutBaseContainer;
    @BindView(R.id.textViewAseetId)
    TextView textViewAseetId;
    @BindView(R.id.textViewAssetName)
    TextView textViewAssetName;
    @BindView(R.id.textViewLocation)
    TextView textViewLocation;
    @BindView(R.id.textViewProjectId)
    TextView textViewProjectId;
    @BindView(R.id.textViewProjectName)
    TextView textViewProjectName;
    @BindView(R.id.textViewBarcode)
    TextView textViewBarcode;

    @BindView(R.id.editTextUpdatedBarCode)
    EditText editTextUpdatedBarCode;
    @BindView(R.id.textViewtag)
    TextView textViewtag;
    @BindView(R.id.customButtonSubmit)
    Button customButtonSubmit;
    String olderString, newString = "";
    private int olderStringLength = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tag_updated, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        linearLayoutcontainer.setVisibility(View.GONE);
        editTextUpdatedBarCode.setVisibility(View.GONE);
        customButtonSubmit.setVisibility(View.GONE);
        textViewtag.setVisibility(View.GONE);
        getAssetData();
        customButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateTagService();
            }
        });

        return view;
    }

    boolean firstEntered = false;

    private void getAssetData() {
        editTextBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    if (CheckInternetConnection.isInternetConnected(getActivity())) {
                        callService(s.toString());
                    } else {
                        callServiceOffline(s.toString());

                    }
                }

            }
        });
        editTextBarCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (editTextBarCode.getText().toString().trim().length() > 0)
                        callService(editTextBarCode.getText().toString().trim());
                }
                return false;
            }
        });
        editTextUpdatedBarCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (editTextUpdatedBarCode.getText().toString().trim().length() > 0) {
                        callUpdateTagService();

                    }

                }
                return false;
            }
        });
        editTextUpdatedBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", s.toString());
                olderStringLength = s.toString().length();
                Log.d("beforenewString", olderStringLength + "");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                olderString = s.toString();
                addText(olderString);
                Log.d("afterTextChanged", s.toString());

            }
        });
    }

    private void callServiceOffline(String string) {
        Gson gson = new Gson();
        String json = Preferance.getAllDAta(getActivity());
        AllData allData = gson.fromJson(json, AllData.class);
        if (allData != null)
            for (int i = 0; i < allData.getListGetAssetAssetGruopwise().size(); i++) {
                if (string.equals(allData.getListGetAssetAssetGruopwise().get(i).getBarcode())) {
                    linearLayoutcontainer.setVisibility(View.VISIBLE);
                    textViewtag.setVisibility(View.VISIBLE);
                    editTextUpdatedBarCode.setVisibility(View.VISIBLE);
                    customButtonSubmit.setVisibility(View.VISIBLE);
                    linearLayoutBaseContainer.setAlpha((float) 0.5);
                    editTextBarCode.setEnabled(false);
                    editTextUpdatedBarCode.requestFocus();

                    textViewAseetId.setText(allData.getListGetAssetAssetGruopwise().get(i).getAssetId());
                    textViewAssetName.setText(allData.getListGetAssetAssetGruopwise().get(i).getName());
                    textViewLocation.setText(allData.getListGetAssetAssetGruopwise().get(i).getLocation());
                    textViewBarcode.setText(allData.getListGetAssetAssetGruopwise().get(i).getBarcode());
                    textViewProjectId.setText(allData.getListGetAssetAssetGruopwise().get(i).getProjectId());
                    textViewProjectName.setText("");
                }
            }
    }

    private void addText(String nstring) {
        Log.d("nstringnewString", nstring.length() + "");
        if (olderStringLength > 0) {
            if (olderStringLength < nstring.length()) {
                newString = nstring.substring(olderStringLength, nstring.length());
                Log.d("newString", newString.toString());
                editTextUpdatedBarCode.setText(newString);
                editTextUpdatedBarCode.setSelection(newString.length());
                olderStringLength = 0;
            }
        }
    }


    private void callService(String s) {
        if (s.trim().length() == 0) {
            editTextBarCode.setText("");
            linearLayoutcontainer.setVisibility(View.GONE);
        } else {
            linearLayoutcontainer.setVisibility(View.GONE);
            final String userId = Preferance.getUserId(getActivity());
            CustomProgress.startProgress(getActivity());

            Call<AssetData> call = MyApplication.apiInterface.GetBarcodeWiseProjectData(new UserAssets(userId, s.trim()));
            call.enqueue(new Callback<AssetData>() {
                @Override
                public void onResponse(Call<AssetData> call, Response<AssetData> response) {
                    insertDataInDataBase(response);
                    CustomProgress.endProgress();

                }

                @Override
                public void onFailure(Call<AssetData> call, Throwable t) {
                    CustomProgress.endProgressContext();

                }
            });
        }

    }

    private void insertDataInDataBase(Response<AssetData> response) {
        if (response == null) {
            CustomToast.showToast(getActivity(), getString(R.string.something_bad_happened));
        } else {
            if (response.body() == null) {
                CustomToast.showToast(getActivity(), response.raw().message());
            } else {
                if (response.body().getStatus().equals("success")) {
                    linearLayoutcontainer.setVisibility(View.VISIBLE);
                    textViewtag.setVisibility(View.VISIBLE);
                    editTextUpdatedBarCode.setVisibility(View.VISIBLE);
                    customButtonSubmit.setVisibility(View.VISIBLE);
                    linearLayoutBaseContainer.setAlpha((float) 0.5);
                    editTextBarCode.setEnabled(false);
                    editTextUpdatedBarCode.requestFocus();

                    textViewAseetId.setText(response.body().getBarcodeWiseDataList().get(0).getASSETID());
                    textViewAssetName.setText(response.body().getBarcodeWiseDataList().get(0).getNAME());
                    textViewLocation.setText(response.body().getBarcodeWiseDataList().get(0).getLOCATION());
                    textViewBarcode.setText(response.body().getBarcodeWiseDataList().get(0).getBarcode());
                    textViewProjectId.setText(response.body().getProjId());
                    textViewProjectName.setText(response.body().getProjectName());

                } else if (response.body().getStatus().equals("failure")) {
                    linearLayoutcontainer.setVisibility(View.GONE);
                    textViewtag.setVisibility(View.GONE);
                    editTextUpdatedBarCode.setVisibility(View.GONE);
                    customButtonSubmit.setVisibility(View.GONE);
                    editTextBarCode.setText("");
                    CustomToast.showToast(getActivity(), "QR Code Does Not Exist in DataBase");

                }
            }
        }
    }

    public static TagUpdatedFragment getInstance() {
        return instance;
    }

    public void onUpdateTagButtonClick() {


    }

    public void showAlertForTwoButton(String message) {
        AlertDialog alertDialog = null;
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        alertDialog = new AlertDialog.Builder(
                getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();

        alertDialog.setMessage(message);

        // Setting Icon to Dialog

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                disableAllViews();
                final String userId = Preferance.getUserId(getActivity());
                CustomProgress.startProgress(getActivity());
                if (CheckInternetConnection.isInternetConnected(getActivity())) {
                    Upadte_tag upadte_tag = new Upadte_tag(userId, textViewLocation.getText().toString(), textViewProjectId.getText().toString(), textViewAseetId.getText().toString(), editTextUpdatedBarCode.getText().toString());
                    Call<TrackingStatus> call = MyApplication.apiInterface.UpDateTagNo(upadte_tag);
                    call.enqueue(new Callback<TrackingStatus>() {
                        @Override
                        public void onResponse(Call<TrackingStatus> call, Response<TrackingStatus> response) {
                            CustomProgress.endProgress();
                            if (response.body().getStatus().equals("success")) {
                                if (response.body().getMessage().equals("Tag No. Already Exist.")) {
                                    CustomToast.showToast(getActivity(), getResources().getString(R.string.tagnumberalreadyassigned));
                                } else {
                                    enableAllViews();
                                    linearLayoutBaseContainer.setAlpha((float) 1);
                                    editTextUpdatedBarCode.setVisibility(View.GONE);
                                    linearLayoutcontainer.setVisibility(View.GONE);
                                    customButtonSubmit.setVisibility(View.GONE);
                                    textViewtag.setVisibility(View.GONE);
                                    CustomToast.showToast(getActivity(), getResources().getString(R.string.datasavedsuccessfully));
                                }
                            } else {
                                CustomToast.showToast(getActivity(), getResources().getString(R.string.something_bad_happened));
                            }

                        }

                        @Override
                        public void onFailure(Call<TrackingStatus> call, Throwable t) {
                            CustomProgress.endProgress();

                        }
                    });
                } else {
                    Upadte_tag upadte_tag = new Upadte_tag(userId, textViewLocation.getText().toString(), textViewProjectId.getText().toString(), textViewAseetId.getText().toString(), editTextUpdatedBarCode.getText().toString());
                    SaveUpdateTagOffline(upadte_tag);
                }


                linearLayoutBaseContainer.setAlpha((float) 0.40);
                editTextUpdatedBarCode.setVisibility(View.VISIBLE);
                customButtonSubmit.setVisibility(View.VISIBLE);
                textViewtag.setVisibility(View.VISIBLE);
                editTextUpdatedBarCode.requestFocus();

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                editTextUpdatedBarCode.setVisibility(View.GONE);
                customButtonSubmit.setVisibility(View.GONE);
                textViewtag.setVisibility(View.GONE);
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    List<Upadte_tag> upadte_tagsList = new ArrayList<>();

    private void SaveUpdateTagOffline(Upadte_tag upadte_tag) {
        upadte_tagsList.add(upadte_tag);
        String updateTagJson = new Gson().toJson(upadte_tagsList);
        Preferance.saveUpdateTagList(getActivity(), updateTagJson);
        enableAllViews();
        linearLayoutBaseContainer.setAlpha((float) 1);
        editTextUpdatedBarCode.setVisibility(View.GONE);
        linearLayoutcontainer.setVisibility(View.GONE);
        customButtonSubmit.setVisibility(View.GONE);
        textViewtag.setVisibility(View.GONE);
        CustomToast.showToast(getActivity(), getResources().getString(R.string.datasavedsuccessfully));


    }

    private void callUpdateTagService() {
        if (editTextUpdatedBarCode.getText().toString().trim().length() == 0) {
            CustomToast.showToast(getActivity(), "Please fill QR Code Tag Number");
        } else {
            showAlertForTwoButton("Do you want to update Tag?");

        }
    }

    private void disableAllViews() {
        editTextBarCode.setEnabled(false);
        linearLayoutcontainer.setEnabled(false);
        textViewAseetId.setEnabled(false);
        textViewAssetName.setEnabled(false);
        textViewLocation.setEnabled(false);
        textViewProjectId.setEnabled(false);
        textViewProjectName.setEnabled(false);
        textViewBarcode.setEnabled(false);
    }

    private void enableAllViews() {
        editTextBarCode.setEnabled(true);
        linearLayoutcontainer.setEnabled(true);
        textViewAseetId.setEnabled(true);
        textViewAssetName.setEnabled(true);
        textViewLocation.setEnabled(true);
        textViewProjectId.setEnabled(true);
        textViewProjectName.setEnabled(true);
        textViewBarcode.setEnabled(true);

        editTextBarCode.setText("");
        textViewAseetId.setText("");
        textViewAssetName.setText("");
        textViewLocation.setText("");
        textViewProjectId.setText("");
        textViewProjectName.setText("");
        textViewBarcode.setText("");
    }
}
