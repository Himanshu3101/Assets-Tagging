package com.assettagging.test.view.assetlink;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.model.pca.ChildListModel;
import com.assettagging.test.model.pca.assetLinkScannedDetailModel;
import com.assettagging.test.view.custom_control.Custom_Button;
import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssetLinkingFragment extends Fragment {

    public static AssetLinkingFragment instance;
    Activity activity;
    public static int position = 0;
    View view;
    @BindView(R.id.relativeLayoutHeaderTag)
    RelativeLayout relativeLayoutHeaderTag;
    @BindView(R.id.editTextScanBarCode)
    EditText editTextScanBarCode;
    @BindView(R.id.textViewHeaderSerialnoID)
    TextView textViewHeaderSerialnoID;

    @BindView(R.id.linearLayoutFloatingAddAsset)
    LinearLayout linearLayoutFloatingAddAsset;

    @BindView(R.id.textViewHeaderLocationID)
    TextView textViewHeaderLocationID;

    @BindView(R.id.textViewHeaderAssetnoID)
    TextView textViewHeaderAssetnoID;

    @BindView(R.id.textViewHeaderTagID)
    TextView textViewHeaderTagID;

    @BindView(R.id.textViewHeaderProjectID)
    TextView textViewHeaderProjectID;

    @BindView(R.id.textViewHeaderDescpID)
    TextView textViewHeaderDescpID;
    @BindView(R.id.linearLayoutHeaderScannedDetail)
    LinearLayout linearLayoutHeaderScannedDetail;
    @BindView(R.id.textViewHeaderTechinfoID)
    TextView textViewHeaderTechinfoID;
    @BindView(R.id.textViewParentChildText)
    TextView textViewParentChildText;
    @BindView(R.id.linearLayoutParentChildText)
    LinearLayout linearLayoutParentChildText;
    @BindView(R.id.linearLayoutViewAfterScanedDetail)
    LinearLayout linearLayoutViewAfterScanedDetail;
    @BindView(R.id.recyclerViewChildsScannedDetail)
    RecyclerView recyclerViewChildsScannedDetail;
    @BindView(R.id.cardViewParentScannedDetail)
    CardView cardViewParentScannedDetail;
    @BindView(R.id.textViewParentScannedDetailSerialnoID)
    TextView textViewParentScannedDetailSerialnoID;
    @BindView(R.id.textViewParentScannedDetailDespID)
    TextView textViewParentScannedDetailDespID;
    @BindView(R.id.textViewParentScannedDetailAssetnoID)
    TextView textViewParentScannedDetailAssetnoID;
    @BindView(R.id.textViewParentScannedDetailTagID)
    TextView textViewParentScannedDetailTagID;
    @BindView(R.id.swipeRevealLayout)
    SwipeRevealLayout swipeRevealLayout;

    AssetLinkingAdapter assetLinkingRecyclerAdapter;
    ArrayList<assetLinkScannedDetailModel> assetLinkScannedDetailModelArrayList;

    ArrayList<ChildListModel> childListModellist;




    //-----------------------------Add asset dialog objects---------------------------------
    LinearLayout linearLayoutAssetDetail;
    EditText editTextSerialNumber;

    Custom_Button buttonAddAsset;
    TextView textViewTech, textViewSerialNo, textViewAssetNo, textViewLocation, textViewProject, textViewDescription;

    //-----------------------------Remove asset dialog objects------------------------------
    Spinner spinnerLocation;
    Custom_Button buttonRemove;
    RadioButton radioButtonInUse, radioButtonSpare, radioButtonFaulty, radioButtonObsolete, radioButtonDammaged, radioButtonDecommissioned;

    //-----------------------------Remove asset dialog objects------------------------------
    TextView textViewScheduleID, textViewDateOfDisposal, textViewDesc, textViewStatus;
    Custom_Button buttonOk, buttonDispose;
    LinearLayout linearLayoutDisposalRequest;

    boolean isDamage = false, isScheduled = false;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parent_child_asset, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        activity = getActivity();
        Listalldata();
        editTextScanBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 3) {
                    String scan = editTextScanBarCode.getText().toString();

                    SearchAssetData(scan);
                } else {
                    initialState();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

//    @OnTextChanged(value = R.id.editTextScanTag, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    void onBeforeTextChanged(CharSequence text ) {
//    }

    private void initialState() {

        linearLayoutParentChildText.setVisibility(View.INVISIBLE);
        linearLayoutViewAfterScanedDetail.setVisibility(View.INVISIBLE);
        linearLayoutHeaderScannedDetail.setVisibility(View.INVISIBLE);
    }

    private void SearchAssetData(String serachasset) {
        //String serachasset = pcaeditTextBarCode.getText().toString();
        //  String serachasset = "AAA";
        for (int i = 0; i < assetLinkScannedDetailModelArrayList.size(); i++) {
            if (assetLinkScannedDetailModelArrayList.get(i).getSerialno().equals(serachasset) ||
                    assetLinkScannedDetailModelArrayList.get(i).getTag().equals(serachasset)) {
                BindData(assetLinkScannedDetailModelArrayList.get(i));
            }
        }
    }

    private void BindData(assetLinkScannedDetailModel assetLinkScannedDetailModel) {
        linearLayoutParentChildText.setVisibility(View.VISIBLE);
        linearLayoutViewAfterScanedDetail.setVisibility(View.VISIBLE);
        linearLayoutHeaderScannedDetail.setVisibility(View.VISIBLE);
        textViewHeaderSerialnoID.setText(assetLinkScannedDetailModel.getSerialno());
        textViewHeaderAssetnoID.setText(assetLinkScannedDetailModel.getAssetno());
        textViewHeaderProjectID.setText(assetLinkScannedDetailModel.getProject());
        textViewHeaderDescpID.setText(assetLinkScannedDetailModel.getDescp());
        textViewHeaderLocationID.setText(assetLinkScannedDetailModel.getLocation());
        textViewHeaderTechinfoID.setText(assetLinkScannedDetailModel.getTechInfo());
        if (assetLinkScannedDetailModel.getIschild().equals("0")) {

            swipeRevealLayout.close(true);
            swipeRevealLayout.setLockDrag(true);
            cardViewParentScannedDetail.setVisibility(View.INVISIBLE);
            linearLayoutFloatingAddAsset.setVisibility(View.VISIBLE);
            relativeLayoutHeaderTag.setVisibility(View.VISIBLE);
            recyclerViewChildsScannedDetail.setVisibility(View.VISIBLE);


            textViewParentChildText.setText("Childs");
            textViewHeaderTagID.setText(assetLinkScannedDetailModel.getTag());
            assetLinkingRecyclerAdapter = new AssetLinkingAdapter(activity, assetLinkScannedDetailModel.getChildListModelList());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            recyclerViewChildsScannedDetail.setLayoutManager(mLayoutManager);
            recyclerViewChildsScannedDetail.setItemAnimator(new DefaultItemAnimator());
            recyclerViewChildsScannedDetail.setAdapter(assetLinkingRecyclerAdapter);
            assetLinkingRecyclerAdapter.notifyDataSetChanged();

        } else if (assetLinkScannedDetailModel.getIschild().equals("1")) {

            swipeRevealLayout.setLockDrag(false);
            cardViewParentScannedDetail.setVisibility(View.VISIBLE);
            relativeLayoutHeaderTag.setVisibility(View.GONE);
            linearLayoutFloatingAddAsset.setVisibility(View.INVISIBLE);
            recyclerViewChildsScannedDetail.setVisibility(View.INVISIBLE);

            textViewParentChildText.setText("Parent");
            textViewParentScannedDetailSerialnoID.setText(assetLinkScannedDetailModel.getpAssetno());
            textViewParentScannedDetailSerialnoID.setText(assetLinkScannedDetailModel.getPserialno());
            textViewParentScannedDetailDespID.setText(assetLinkScannedDetailModel.getPdescp());
            textViewParentScannedDetailTagID.setText(assetLinkScannedDetailModel.getTag());
        }
    }

    private void Listalldata() {
        childListModellist = new ArrayList<>();
        ChildListModel one = new ChildListModel("aaa", "A-BA-001", "NodeBchld1");
        ChildListModel two = new ChildListModel("bbb", "A-CA-001", "NodeBchld2");
        ChildListModel three = new ChildListModel("ccc", "A-DA-001", "NodeBchld3");
        childListModellist.add(one);
        childListModellist.add(two);
        childListModellist.add(three);


        assetLinkScannedDetailModelArrayList = new ArrayList<>();
        assetLinkScannedDetailModel item = new assetLinkScannedDetailModel("AAA", "A-AA-001", "DRG-TAG-001",
                "LOFO18", "D0857", "Huawei", "NodeB", childListModellist, "0");
        assetLinkScannedDetailModel item1 = new assetLinkScannedDetailModel("BBB", "B-BB-001", "DRG-TAG-002",
                "LOFO19", "D0858", "Huawei", "NodeB2", childListModellist, "0");
        assetLinkScannedDetailModel item2 = new assetLinkScannedDetailModel("aaa", "A-AA-001", "DRG-TAG-003",
                "LOFO18", "D0857", "Huawei", "NodeB", "AAA", "A-BA-001", "NodeBchld1", "1");
        assetLinkScannedDetailModel item3 = new assetLinkScannedDetailModel("bbb", "A-AA-001", "DRG-TAG-004",
                "LOFO18", "D0857", "Huawei", "NodeB", "AAA", "A-CA-0021", "NodeBchld2", "1");
        assetLinkScannedDetailModelArrayList.add(item);
        assetLinkScannedDetailModelArrayList.add(item1);
        assetLinkScannedDetailModelArrayList.add(item2);
        assetLinkScannedDetailModelArrayList.add(item3);
    }

    public static AssetLinkingFragment getInstance() {
        return instance;
    }

    @OnClick(R.id.floatingAddAssetButton)
    void onClickfloatingAddAssetButton() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_asset);

        editTextSerialNumber = dialog.findViewById(R.id.editTextSerialNumber);
        buttonAddAsset = dialog.findViewById(R.id.buttonAddAsset);
        linearLayoutAssetDetail = dialog.findViewById(R.id.linearLayoutAssetDetail);
        textViewSerialNo = dialog.findViewById(R.id.textViewSerialNo);
        textViewTech = dialog.findViewById(R.id.textViewTech);
        textViewAssetNo = dialog.findViewById(R.id.textViewAssetNo);
        textViewLocation = dialog.findViewById(R.id.textViewLocation);
        textViewProject = dialog.findViewById(R.id.textViewProject);
        textViewDescription = dialog.findViewById(R.id.textViewDescription);

        editTextSerialNumber.requestFocus();
        editTextSerialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().toLowerCase().length() >= 4) {
                    buttonAddAsset.setVisibility(View.VISIBLE);
                    linearLayoutAssetDetail.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonAddAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.imageViewCross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @OnClick(R.id.imageViewHeaderScannedDetailDelete)
    void onClickimageViewHeaderScannedDetailDelete(){
        removeAssetDialog();
    }


    void removeAssetDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_asset);

        spinnerLocation = dialog.findViewById(R.id.spinnerLocation);
        editTextSerialNumber = dialog.findViewById(R.id.editTextSerialNumber);
        buttonRemove = dialog.findViewById(R.id.buttonRemove);
        ArrayList<UniversalSpinner> list = new ArrayList<>();
        list.add(new UniversalSpinner("00", "Select Location"));
        list.add(new UniversalSpinner("1", "LOF018"));
        list.add(new UniversalSpinner("2", "LOF012"));
        list.add(new UniversalSpinner("3", "LOF234"));

        spinnerLocation.setAdapter(new SpinnerAdapter(getActivity(), R.layout.spinner_layout, list));

        radioButtonInUse = dialog.findViewById(R.id.radioButtonInUse);
        radioButtonSpare = dialog.findViewById(R.id.radioButtonSpare);
        radioButtonFaulty = dialog.findViewById(R.id.radioButtonFaulty);
        radioButtonObsolete = dialog.findViewById(R.id.radioButtonObsolete);
        radioButtonDammaged = dialog.findViewById(R.id.radioButtonDammaged);
        radioButtonDecommissioned = dialog.findViewById(R.id.radioButtonDecommissioned);

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (radioButtonDammaged.isChecked()) {
                    isDamage = true;
                } else {
                    isDamage = false;
                }
                removeConfirmationDialog();
            }
        });

        dialog.findViewById(R.id.imageViewCross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void removeConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to remove this asset!!")
                .setTitle("Remove Confirmation")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDamage) {
                            disposeAssetDialog();                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    void disposeAssetDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_disposal_request);

        linearLayoutAssetDetail = dialog.findViewById(R.id.linearLayoutAssetDetail);
        textViewSerialNo = dialog.findViewById(R.id.textViewSerialNo);
        textViewTech = dialog.findViewById(R.id.textViewTech);
        textViewAssetNo = dialog.findViewById(R.id.textViewAssetNo);
        textViewLocation = dialog.findViewById(R.id.textViewLocation);
        textViewProject = dialog.findViewById(R.id.textViewProject);
        textViewDescription = dialog.findViewById(R.id.textViewDescription);
        buttonDispose = dialog.findViewById(R.id.buttonDispose);

        linearLayoutDisposalRequest = dialog.findViewById(R.id.linearLayoutDisposalRequest);
        textViewScheduleID = dialog.findViewById(R.id.textViewScheduleID);
        textViewDateOfDisposal = dialog.findViewById(R.id.textViewDateOfDisposal);
        textViewDesc = dialog.findViewById(R.id.textViewDesc);
        textViewStatus = dialog.findViewById(R.id.textViewStatus);
        buttonOk = dialog.findViewById(R.id.buttonOk);

        if (isScheduled) {
            linearLayoutAssetDetail.setVisibility(View.GONE);
            linearLayoutDisposalRequest.setVisibility(View.VISIBLE);
            isScheduled = false;
        } else {
            linearLayoutAssetDetail.setVisibility(View.VISIBLE);
            linearLayoutDisposalRequest.setVisibility(View.GONE);
            isScheduled = true;
        }
        buttonDispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.imageViewCross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}


