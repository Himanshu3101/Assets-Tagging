package com.assettagging.test.view.schedule_detail;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.movement_dimension.FinacialDimension;
import com.assettagging.test.model.movement_dimension.ListAccount;
import com.assettagging.test.model.movement_dimension.ListCostcenterDimension;
import com.assettagging.test.model.movement_dimension.ListProjectDimension;
import com.assettagging.test.model.movement_dimension.ListSiteDimension;
import com.assettagging.test.model.movement_dimension.ListWorkerDimension;
import com.assettagging.test.model.movement_dimension.ListdepartmentDimension;
import com.assettagging.test.model.schedule_detail.ItemCurentStatusList;
import com.assettagging.test.model.schedule_detail.ScheduleDetail_;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.schedule.ScheduleFragmnet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleDetailAdapter extends RecyclerView.Adapter<ScheduleDetailAdapter.MyViewHolder> {

    public List<ScheduleDetail_> scheduleDetailList;
    private Activity activity;
    private String searchString = "";
    private List<ScheduleDetail_> tempListSchedule;
    private List<ScheduleDetail_> tempListScheduleToShow;
    public static List<ScheduleDetail_> checkedList;
    boolean isFirstTime = false;
    public static boolean barcodeMatched = true;
    List<ItemCurentStatusList> itemCurentStatusList = new ArrayList<>();
    boolean items[];
    boolean itemsEnabled[];
    private String imagepath = "";
    public static String CurentStatus = "-1";
    boolean opened = false;
    private Dialog dialog;
    private Bitmap decodedByte;
    String offsetString, mainString = "";
    private RelativeLayout relativeLayout, relativeLayoutTop;
    FinacialDimension finacialDimension = null;
    private boolean isCheckedManually = true;

    public ScheduleDetailAdapter(Activity activity, List<ScheduleDetail_> scheduleDetailList, List<ItemCurentStatusList> itemCurentStatusList) {
        this.scheduleDetailList = scheduleDetailList;
        this.itemCurentStatusList = itemCurentStatusList;
        this.activity = activity;
        items = new boolean[this.scheduleDetailList.size()];
        itemsEnabled = new boolean[this.scheduleDetailList.size()];
        this.tempListSchedule = new ArrayList<ScheduleDetail_>();
        this.checkedList = new ArrayList<ScheduleDetail_>();
        barcodeMatched = true;
        this.tempListSchedule.addAll(scheduleDetailList);
        this.tempListScheduleToShow = new ArrayList<ScheduleDetail_>();
        for (int i = 0; i < this.scheduleDetailList.size(); i++) {
            this.items[i] = false;
        }
        for (int i = 0; i < this.scheduleDetailList.size(); i++) {
            this.itemsEnabled[i] = false;
        }
        if (ScheduleFragmnet.position == 0) {
            for (int i = 0; i < this.scheduleDetailList.size(); i++) {
                scheduleDetailList.get(i).setTRACKING("1");
            }
        }
        // scheduleDetailList.get(0).setBarCode("");
        if (CheckInternetConnection.isInternetConnected(activity)) {
            if (ScheduleDetailActivity.getInstance().ActivityType.equals("Movement"))
                callFinacialDimensionService(0, scheduleDetailList.get(0));
        } else {
            if (ScheduleDetailActivity.getInstance().ActivityType.equals("Movement")) {
                Gson gson = new Gson();
                String json = Preferance.getAllDAta(activity);
                finacialDimension = new FinacialDimension();
                AllData allData = gson.fromJson(json, AllData.class);
                finacialDimension.setlistAccount(allData.getlistAccount());
                finacialDimension.setListCostcenterDimension(allData.getListCostcenterDimension());
                finacialDimension.setListdepartmentDimension(allData.getListdepartmentDimension());
                finacialDimension.setListProjectDimension(allData.getListProjectDimension());
                finacialDimension.setListSiteDimension(allData.getListSiteDimension());
                finacialDimension.setListWorkerDimension(allData.getListWorkerDimension());
            }
        }
    }

    public static Bitmap getBitmapFromURL(final String src) {
        final Bitmap[] bitmap2 = new Bitmap[1];
        Picasso.get().load(src).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                bitmap2[0] = bitmap;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        return bitmap2[0];


    }

    ScheduleDetail_ scheduleDetail;

    public void filter(String filterString) {
        searchString = filterString;
        Log.d("searchString ", searchString);
        isFirstTime = true;
        CustomToast.showCustomToast(activity, barcodeMatched + " 1");
        if (filterString.length() == 0) {
            CustomToast.showCustomToast(activity, barcodeMatched + " 2");
            barcodeMatched = true;
            scheduleDetailList.clear();
            scheduleDetailList.addAll(tempListSchedule);
        } else {
            CustomToast.showCustomToast(activity, barcodeMatched + " 3");

            barcodeMatched = false;

            for (ScheduleDetail_ wp : tempListSchedule) {
                Log.d("getBarCode ", wp.getBarCode());
                if (wp.getBarCode().equals(searchString)) {
                    CustomToast.showCustomToast(activity, barcodeMatched + " 4");
                    isCheckedManually = false;
                    barcodeMatched = true;
                    opened = true;
                    wp.setTRACKING("1");
                    scheduleDetail = new ScheduleDetail_();
                    scheduleDetail = wp;
                    checkedList.add(wp);
                    //  scheduleDetailList.remove(wp);
                    //  scheduleDetailList.add(0, wp);
                    ScheduleDetailActivity.editTextBarCode.setText("");
                    ScheduleDetailActivity.editTextBarCode.requestFocus();
                }

            }

        }

        notifyDataSetChanged();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewActivityType)
        public TextView textViewActivityType;
        @BindView(R.id.textViewAssetId)
        public TextView textViewAssetId;
        @BindView(R.id.textViewTagReq)
        public TextView textViewTagReq;
        @BindView(R.id.checkboxTracking)
        public CheckBox checkboxTracking;
        @BindView(R.id.imageViewAssetIcon)
        public ImageView imageViewAssetIcon;
        @BindView(R.id.relativeLayout)
        public RelativeLayout relativeLayout;
        @BindView(R.id.card_view)
        public CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            if (Preferance.getTheme(activity).equals("ORANGE")) {
                card_view.setForeground(activity.getResources().getDrawable(R.drawable.cardview_background, null));
            } else if (Preferance.getTheme(activity).equals("BLUE")) {
                card_view.setForeground(activity.getResources().getDrawable(R.drawable.cardview_background_blue, null));
            }
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_detail_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        for (int i = 0; i < this.scheduleDetailList.size(); i++) {
//            this.itemsEnabled[i] = false;
//        }
//        if (scheduleDetailList.get(position).getBarCode().equals("")) {
//            itemsEnabled[position] = true;
//        } else {
//            itemsEnabled[position] = false;
//        }
        if (scheduleDetailList.get(position).getBarCode().equals("")) {
            holder.textViewTagReq.setText("No");
        } else {
            holder.textViewTagReq.setText("Yes");
        }
        holder.textViewActivityType.setText(scheduleDetailList.get(position).getBarCode());
        holder.textViewAssetId.setText(scheduleDetailList.get(position).getASSETID());
        imagepath = scheduleDetailList.get(position).getImagePath();
        if (imagepath == null) {
            imagepath = "";
        }
        if (!CheckInternetConnection.isInternetConnected(activity.getApplicationContext())) {
            if (scheduleDetailList.get(position).getBLOB_IMAGE() != null) {
                decodedByte = BitmapFactory.decodeByteArray(scheduleDetailList.get(position).getBLOB_IMAGE(), 0, scheduleDetailList.get(position).getBLOB_IMAGE().length);
                holder.imageViewAssetIcon.setImageBitmap(decodedByte);
            }
        } else {
            Picasso.get().load(scheduleDetailList.get(position).getImagePath()).into(holder.imageViewAssetIcon);
        }


        HashSet<ScheduleDetail_> hashSet = new HashSet<ScheduleDetail_>();
        hashSet.addAll(checkedList);
        checkedList.clear();
        checkedList.addAll(hashSet);

        if (checkedList.size() == 0) {
            items = new boolean[this.scheduleDetailList.size()];
            for (int j = 0; j < this.scheduleDetailList.size(); j++) {
                this.items[j] = false;
            }
        }
        for (int i = 0; i < scheduleDetailList.size(); i++) {
            for (int j = 0; j < checkedList.size(); j++) {
                if (scheduleDetailList.get(i).getBarCode().equals(checkedList.get(j).getBarCode())) {
                    items[i] = true;
                }
            }

        }

        if (items[position]) {
            holder.checkboxTracking.setChecked(true);
        } else {
            holder.checkboxTracking.setChecked(false);

        }
        if (barcodeMatched == false) {
            CustomToast.showToast(activity, "QR Code not matched");
        }
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        if (itemsEnabled[position]) {
            holder.checkboxTracking.setChecked(true);
            holder.checkboxTracking.setClickable(false);
            holder.checkboxTracking.setFocusable(false);
        } else {
            holder.checkboxTracking.setChecked(false);
        }
        if (scheduleDetailList.get(position).getBarCode().equals(""))
            if (!holder.checkboxTracking.isChecked())
                holder.checkboxTracking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.checkboxTracking.isChecked()) {
                            holder.checkboxTracking.setChecked(true);
                            barcodeMatched = true;
                            opened = true;
                            itemsEnabled[position] = true;
                            ScheduleDetail_ wp = new ScheduleDetail_();
                            wp = scheduleDetailList.get(position);
                            wp.setTRACKING("1");
                            scheduleDetail = new ScheduleDetail_();
                            scheduleDetail = wp;
                            checkedList.add(wp);
                            isCheckedManually = true;//  scheduleDetailList.remove(wp);
                            //  scheduleDetailList.add(0, wp);
                            ScheduleDetailActivity.editTextBarCode.setText("");
                            ScheduleDetailActivity.editTextBarCode.requestFocus();
                            if (scheduleDetail != null) {
                                if (ScheduleDetailActivity.getInstance().ActivityType.equals("Inspection")) {

                                    openDialogforItems(scheduleDetail, itemCurentStatusList, holder.itemView);

                                } else if (ScheduleDetailActivity.getInstance().ActivityType.equals("Movement")) {

                                    if (scheduleDetail.getMovementFlag().equals("Pick")) {
                                        dialogForMovementDimension(position, scheduleDetail, null);
                                    } else if (scheduleDetail.getMovementFlag().equals("Drop")) {
                                        Gson gson = new GsonBuilder().create();
                                        JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                                        ScheduleDetailActivity.ScannedList = myCustomArray;
                                        opened = false;
                                        if (dialog != null) {
                                            dialog.cancel();
                                            dialog.dismiss();
                                            dialog = null;
                                        }
                                    }

                                } else {
                                    Gson gson = new GsonBuilder().create();
                                    JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                                    ScheduleDetailActivity.ScannedList = myCustomArray;
                                    opened = false;
                                    if (dialog != null) {
                                        dialog.cancel();
                                        dialog.dismiss();
                                        dialog = null;
                                    }
                                }

                            }
                        } else {
                            holder.checkboxTracking.setChecked(true);
                        }
                    }

                });


        if (opened) {
            if (scheduleDetail != null) {
                if (ScheduleDetailActivity.getInstance().ActivityType.equals("Inspection")) {

                    openDialogforItems(scheduleDetail, itemCurentStatusList, holder.itemView);

                } else if (ScheduleDetailActivity.getInstance().ActivityType.equals("Movement")) {

                    if (scheduleDetail.getMovementFlag().equals("Pick")) {
                        dialogForMovementDimension(position, scheduleDetail, null);
                    } else if (scheduleDetail.getMovementFlag().equals("Drop")) {
                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                        ScheduleDetailActivity.ScannedList = myCustomArray;
                        opened = false;
                        if (dialog != null) {
                            dialog.cancel();
                            dialog.dismiss();
                            dialog = null;
                        }
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                    ScheduleDetailActivity.ScannedList = myCustomArray;
                    opened = false;
                    if (dialog != null) {
                        dialog.cancel();
                        dialog.dismiss();
                        dialog = null;
                    }
                }

            }
            isCheckedManually = true;
        }

        holder.imageViewAssetIcon.setOnClickListener(new View.OnClickListener()

        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (!CheckInternetConnection.isInternetConnected(activity.getApplicationContext())) {
                    if (scheduleDetailList.get(position).getBLOB_IMAGE() != null) {
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(scheduleDetailList.get(position).getBLOB_IMAGE(), 0, scheduleDetailList.get(position).getBLOB_IMAGE().length);
                        ScheduleDetailActivity.zoomImageFromThumb(v, decodedByte);
                    }
                } else {
                    ScheduleDetailActivity.zoomImageFromThumb(v, getBitmapFromURL(scheduleDetailList.get(position).getImagePath()));
                }
            }
        });
        if (scheduleDetailList.get(position).getTRACKING().equals("1")) {
            holder.checkboxTracking.setChecked(true);
        }


    }

    private void callFinacialDimensionService(final int position,
                                              final ScheduleDetail_ scheduleDetail) {
        CustomProgress.startProgressContext(activity);
        Call<FinacialDimension> call = MyApplication.apiInterface.GetDimesionData();
        call.enqueue(new Callback<FinacialDimension>() {
            @Override
            public void onResponse(Call<FinacialDimension> call, Response<FinacialDimension> response) {
                CustomProgress.endProgressContext();
                finacialDimension = response.body();
            }

            @Override
            public void onFailure(Call<FinacialDimension> call, Throwable t) {
                CustomProgress.endProgressContext();
                finacialDimension = null;
                CustomToast.showToast(activity, activity.getString(R.string.something_bad_happened));
                if (checkedList.size() > 0)
                    if (checkedList.size() >= position + 1) checkedList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void supdateFinacialDimesion(final FinacialDimension body, int position) {
        if (body != null)
            if (body.getStatus().equals("success")) {
                ListProjectDimension listProjectDimension = new ListProjectDimension();
                listProjectDimension.setName("Select Item");
                listProjectDimension.setProjId("");
                if (!body.getListProjectDimension().get(0).getName().contains("Select Item")) {
                    body.getListProjectDimension().add(0, listProjectDimension);
                }


                ListdepartmentDimension listdepartmentDimension = new ListdepartmentDimension();
                listdepartmentDimension.setValue("Select Item");
                listdepartmentDimension.setName("");
                if (!body.getListdepartmentDimension().get(0).getValue().contains("Select Item")) {
                    body.getListdepartmentDimension().add(0, listdepartmentDimension);
                }

                ListCostcenterDimension listCostcenterDimension = new ListCostcenterDimension();
                listCostcenterDimension.setValue("Select Item");
                listCostcenterDimension.setName("");
                if (!body.getListCostcenterDimension().get(0).getValue().contains("Select Item")) {
                    body.getListCostcenterDimension().add(0, listCostcenterDimension);
                }
                ListSiteDimension listSiteDimension = new ListSiteDimension();
                listSiteDimension.setDescription("Select Item");
                listSiteDimension.setValue("");
                if (!body.getListSiteDimension().get(0).getDescription().contains("Select Item")) {
                    body.getListSiteDimension().add(0, listSiteDimension);
                }

                ListWorkerDimension listWorkerDimension = new ListWorkerDimension();
                listWorkerDimension.setNAME("Select Item");
                listWorkerDimension.setPERSONNELNUMBER("");
                if (!body.getListWorkerDimension().get(0).getNAME().contains("Select Item")) {
                    body.getListWorkerDimension().add(0, listWorkerDimension);
                }

                ListAccount listAccount = new ListAccount();
                listAccount.setName("Select Item");
                listAccount.setMainAccountNo("0");
                if (!body.getlistAccount().get(0).getName().contains("Select Item")) {
                    body.getlistAccount().add(0, listAccount);
                }
                project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleDetailActivity.getInstance().ProjectSelected = body.getListProjectDimension().get(position).getProjId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ScheduleDetailActivity.getInstance().ProjectSelected = "";
                    }
                });

                department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleDetailActivity.getInstance().DepartmentSelected = body.getListdepartmentDimension().get(position).getValue();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ScheduleDetailActivity.getInstance().DepartmentSelected = "";
                    }
                });

                costCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleDetailActivity.getInstance().CostCenterSelected = body.getListCostcenterDimension().get(position).getValue();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ScheduleDetailActivity.getInstance().CostCenterSelected = "";
                    }
                });

                site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleDetailActivity.getInstance().SiteSelected = body.getListSiteDimension().get(position).getValue();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ScheduleDetailActivity.getInstance().SiteSelected = "";
                    }
                });

                worker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ScheduleDetailActivity.getInstance().WorkerSelected = body.getListWorkerDimension().get(position).getPERSONNELNUMBER();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ScheduleDetailActivity.getInstance().WorkerSelected = "";
                    }
                });

            } else {
                CustomToast.showToast(activity, activity.getString(R.string.something_bad_happened));
                checkedList.remove(position);
                notifyDataSetChanged();
            }
    }

    SearchableSpinner project, department, costCenter, site, worker;

    public void dialogForMovementDimension(int position, final ScheduleDetail_ scheduleDetail,
                                           final FinacialDimension body) {
        if (dialog == null) {
            dialog = new Dialog(activity);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_finacial_dimension);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            project = dialog.findViewById(R.id.project);
            department = dialog.findViewById(R.id.department);
            costCenter = dialog.findViewById(R.id.costCenter);
            site = dialog.findViewById(R.id.site);
            relativeLayout = dialog.findViewById(R.id.relativeLayout);
            relativeLayoutTop = dialog.findViewById(R.id.relativeLayoutTop);
            worker = dialog.findViewById(R.id.worker);
            Button movement_dimension_submit = dialog.findViewById(R.id.movement_dimension_submit);
            relativeLayoutTop.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
            relativeLayout.setVisibility(View.GONE);
            if (finacialDimension == null) {
                callFinacialDimensionService(position, scheduleDetail);
            } else {
                bindingAdapter(finacialDimension);
                supdateFinacialDimesion(finacialDimension, position);
            }
            movement_dimension_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    for (int i = 0; i < checkedList.size(); i++) {
                        if (checkedList.get(i).getBarCode().equals(scheduleDetail.getBarCode())) {
                            if (ScheduleDetailActivity.getInstance().CostCenterSelected.contains("Select Item")) {
                                ScheduleDetailActivity.getInstance().CostCenterSelected = "";
                            }
                            if (ScheduleDetailActivity.getInstance().ProjectSelected.contains("Select Item")) {
                                ScheduleDetailActivity.getInstance().ProjectSelected = "";
                            }
                            if (ScheduleDetailActivity.getInstance().DepartmentSelected.contains("Select Item")) {
                                ScheduleDetailActivity.getInstance().DepartmentSelected = "";
                            }
                            if (ScheduleDetailActivity.getInstance().SiteSelected.contains("Select Item")) {
                                ScheduleDetailActivity.getInstance().SiteSelected = "";
                            }
                            if (ScheduleDetailActivity.getInstance().WorkerSelected.contains("Select Item")) {
                                ScheduleDetailActivity.getInstance().WorkerSelected = "";
                            }
                            checkedList.get(i).setCostcenter(ScheduleDetailActivity.getInstance().CostCenterSelected);
                            checkedList.get(i).setProject(ScheduleDetailActivity.getInstance().ProjectSelected);
                            checkedList.get(i).setDepartment(ScheduleDetailActivity.getInstance().DepartmentSelected);
                            checkedList.get(i).setSite(ScheduleDetailActivity.getInstance().SiteSelected);
                            checkedList.get(i).setWorker(ScheduleDetailActivity.getInstance().WorkerSelected);
                            ;
                            checkedList.get(i).setITEMS("");
                        }
                    }
                    Gson gson = new GsonBuilder().create();
                    JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                    ScheduleDetailActivity.ScannedList = myCustomArray;
                    opened = false;
                    dialog.cancel();
                    dialog.dismiss();
                    dialog = null;


                }
            });


            dialog.show();

        }
    }

    TextView textViewLocation;
    TextView textViewAssetId;
    TextView textViewActivityType;
    Button customButtonSaveTracking;
    RadioGroup radioGroupItems;
    Dialog dialogInspection;

    private void openDialogforItems(final ScheduleDetail_ SCHEDULE, List<
            ItemCurentStatusList> ITEM, final View itemView) {
        if (dialogInspection == null) {
            dialogInspection = new Dialog(activity);
            dialogInspection.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogInspection.setCanceledOnTouchOutside(false);
            dialogInspection.setCancelable(false);
            dialogInspection.setContentView(R.layout.dialog_items);
            dialogInspection.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            textViewLocation = dialogInspection.findViewById(R.id.textViewLocation);
            textViewAssetId = dialogInspection.findViewById(R.id.textViewAssetId);
            textViewActivityType = dialogInspection.findViewById(R.id.textViewActivityType);
            customButtonSaveTracking = dialogInspection.findViewById(R.id.customButtonSaveTracking);
            radioGroupItems = dialogInspection.findViewById(R.id.radioGroupItems);

            if (Preferance.getTheme(activity).equals("ORANGE")) {
                customButtonSaveTracking.setBackground(activity.getResources().getDrawable(R.drawable.button_background, null));
            } else if (Preferance.getTheme(activity).equals("BLUE")) {
                customButtonSaveTracking.setBackground(activity.getResources().getDrawable(R.drawable.button_background_blue, null));

            }
            for (int i = 0; i < ITEM.size(); i++) {
                RadioButton newRadioButton = new RadioButton(activity);
                newRadioButton.setText(ITEM.get(i).getName());
                newRadioButton.setId(Integer.parseInt(ITEM.get(i).getStatusId()));
                newRadioButton.setTag(ITEM.get(i).getName());
                LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
                layoutParams.topMargin = 10;
                layoutParams.bottomMargin = 10;

                radioGroupItems.addView(newRadioButton, 0, layoutParams);
            }
            textViewActivityType.setText(SCHEDULE.getACTIVITYTYPE());
            textViewAssetId.setText(SCHEDULE.getASSETID());
            textViewLocation.setText(SCHEDULE.getLOCATION());
            radioGroupItems.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //  RadioButton radioButton = itemView.findViewById(checkedId);
                    //  CurentStatus = radioButton.getTag() + "";

                }
            });
            customButtonSaveTracking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CurentStatus = radioGroupItems.getCheckedRadioButtonId() + "";
                    if (CurentStatus.equals("-1")) {
                        CustomToast.showToast(activity, activity.getString(R.string.checkStatusOfAsset));
                    } else {
                        for (int i = 0; i < checkedList.size(); i++) {
                            if (checkedList.get(i).getBarCode().equals(SCHEDULE.getBarCode())) {
                                checkedList.get(i).setCurentStatus(CurentStatus);
                                checkedList.get(i).setITEMS("");
                            }
                        }

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(checkedList).getAsJsonArray();
                        ScheduleDetailActivity.ScannedList = myCustomArray;
                        opened = false;
                        dialogInspection.cancel();
                        dialogInspection.dismiss();
                        dialogInspection = null;

                    }

                }
            });
            dialogInspection.show();
        }
    }

    public void bindingAdapter(FinacialDimension body) {
        if (body != null) {
            ArrayAdapter<ListProjectDimension> adapterList1 = new ArrayAdapter<ListProjectDimension>(activity, android.R.layout.simple_spinner_item, body.getListProjectDimension());
            adapterList1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            project.setAdapter(adapterList1);


            ArrayAdapter<ListdepartmentDimension> adapterList2 = new ArrayAdapter<ListdepartmentDimension>(activity, android.R.layout.simple_spinner_item, body.getListdepartmentDimension());
            adapterList2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            department.setAdapter(adapterList2);

            ArrayAdapter<ListCostcenterDimension> adapterList3 = new ArrayAdapter<ListCostcenterDimension>(activity, android.R.layout.simple_spinner_item, body.getListCostcenterDimension());
            adapterList3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            costCenter.setAdapter(adapterList3);

            ArrayAdapter<ListSiteDimension> adapterList4 = new ArrayAdapter<ListSiteDimension>(activity, android.R.layout.simple_spinner_item, body.getListSiteDimension());
            adapterList4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            site.setAdapter(adapterList4);

            ArrayAdapter<ListWorkerDimension> adapterList5 = new ArrayAdapter<ListWorkerDimension>(activity, android.R.layout.simple_spinner_item, body.getListWorkerDimension());
            adapterList5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            worker.setAdapter(adapterList5);


        }
    }

    @Override
    public int getItemCount() {
        return scheduleDetailList.size();
    }


}