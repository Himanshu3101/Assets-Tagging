package com.assettagging.test.view.assetdisposer.yet_to_submit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.custom_control.CustomToast;
import com.assettagging.test.view.navigation.NavigationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class YetToSubmitDisposerFragment extends Fragment {
    public static YetToSubmitDisposerFragment instance;
    View view;
    @BindView(R.id.recyclerViewAllSchedules)
    RecyclerView recyclerViewAllSchedules;

    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;
    private Dialog dialogAddNew;
    private Calendar myCalendar;
    private TextView textViewStartDate, textViewEndDate;
    int mMonth, mYear, mDay;
    private YetToSubmitDisposerAdapter scheduleAdapter;
    private DataBaseHelper dataBaseHelper;
    private PopupWindow popupWindow;
    String sortedDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asset_disposer, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        dataBaseHelper = new DataBaseHelper(getActivity());
        myCalendar = Calendar.getInstance();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        NavigationActivity.getInstance().menuitemfilter.setVisible(true);
        NavigationActivity.getInstance().menuitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openAddNewDialog();
                return false;
            }
        });

        // registerForContextMenu(NavigationActivity.getInstance().menuitemfilter.getActionView());

        NavigationActivity.getInstance().menuitemfilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.TOP | Gravity.RIGHT);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                View view = getLayoutInflater().inflate(R.layout.filter_meny, null);
                dialog.setContentView(view, layoutParams);
                final TextView textViewCreatedOn = dialog.findViewById(R.id.textViewCreatedOn);
                final TextView textViewClearAllFileter = dialog.findViewById(R.id.textViewClearAllFileter);
                if (!sortedDate.equals("")) {
                    textViewCreatedOn.setText(getResources().getString(R.string.createdon) + ": " + sortedDate);
                }

                textViewCreatedOn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        try {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            String stringDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                            Date newDate = format.parse(stringDate);
                                            sortedDate = sdf.format(newDate);
                                            textViewCreatedOn.setText(getResources().getString(R.string.createdon) + ": " + sortedDate);
                                            sortListAccToDate(sdf.format(newDate));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                textViewClearAllFileter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        sortedDate = "";
                        textViewCreatedOn.setText(getResources().getString(R.string.createdon));
                        setAdapter(dataBaseHelper.getAllDisposedSchedule());
                        scheduleAdapter.sortList();
                    }
                });

                dialog.show();
                return false;
            }
        });
        setAdapter(dataBaseHelper.getAllDisposedSchedule());

        return view;
    }


    private void sortListAccToDate(String format) {
        List<CreatedDisposalList> createdDisposalLists = new ArrayList<>();
        createdDisposalLists.addAll(dataBaseHelper.getAllDisposedSchedule());
        List<CreatedDisposalList> tempList = new ArrayList<>();
        for (int i = 0; i < createdDisposalLists.size(); i++) {
            if (format.equals(createdDisposalLists.get(i).getDisposalDate())) {
                tempList.add(createdDisposalLists.get(i));
            }
        }
        setAdapter(tempList);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void openAddNewDialog() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final String userId = Preferance.getUserId(getActivity());
        dialogAddNew = new Dialog(getActivity());
        dialogAddNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddNew.setContentView(R.layout.dialog_add_new);
        dialogAddNew.setCanceledOnTouchOutside(false);
        final EditText editTextDescription = dialogAddNew.findViewById(R.id.editTextDescription);
        final ImageView imageViewCross = dialogAddNew.findViewById(R.id.imageViewCross);
        textViewStartDate = dialogAddNew.findViewById(R.id.textViewStartDate);
        Button buttonSubmit = dialogAddNew.findViewById(R.id.buttonSubmit);
        Window window = dialogAddNew.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageViewCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddNew.dismiss();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDescription.getText().toString().trim().equalsIgnoreCase("")) {
                    CustomToast.showToast(getActivity(), "Please Add Schedule Description");
                } else if (textViewStartDate.getText().toString().trim().equalsIgnoreCase("")) {
                    CustomToast.showToast(getActivity(), "Please Add Start Date");
                } else {
                    doAddSchedule(editTextDescription.getText().toString().trim(), textViewStartDate.getText().toString().trim());
                }
            }
        });
        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    String stringDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date newDate = format.parse(stringDate);
                                    textViewStartDate.setText(sdf.format(newDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        dialogAddNew.show();
    }

    private void doAddSchedule(String scheduleDesc, String startDate) {

        showAlertForTwoButton("Do you want to add as:", scheduleDesc, startDate);

    }

    public void showAlertForTwoButton(String message, final String scheduleDesc, final String startDate) {
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
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Disposal Sale", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Schedule schedule = new Schedule();
                schedule.setSCHEDULEID("SN_"+getCurrentTime());
                schedule.setSCHEDULEDESCRIPTION(scheduleDesc);
                schedule.setSTARTTIME(startDate);
                schedule.setStatus("Created");
                schedule.setType("Disposal Sale");
                dataBaseHelper.insertDisposerSCHEDULE(schedule);
                setAdapter(dataBaseHelper.getAllDisposedSchedule());
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Disposal Scrap", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Schedule schedule = new Schedule();
                schedule.setSCHEDULEID("SN_"+getCurrentTime());
                schedule.setSCHEDULEDESCRIPTION(scheduleDesc);
                schedule.setSTARTTIME(startDate);
                schedule.setType("Disposal Scrap");
                schedule.setStatus("Created");
                dataBaseHelper.insertDisposerSCHEDULE(schedule);
                setAdapter(dataBaseHelper.getAllDisposedSchedule());
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        String checkintime = df.format(c);
        return checkintime;
    }

    public static YetToSubmitDisposerFragment getInstance() {
        return instance;
    }

    public void setAdapter(List<CreatedDisposalList> allDisposedSchedule) {
        if (dialogAddNew != null) {
            dialogAddNew.dismiss();
            dialogAddNew = null;
        }
        if (allDisposedSchedule.size() > 0) {
            recyclerViewAllSchedules.setVisibility(View.VISIBLE);
            textViewNoSchedule.setVisibility(View.GONE);
            scheduleAdapter = new YetToSubmitDisposerAdapter(getActivity(), allDisposedSchedule);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewAllSchedules.setLayoutManager(mLayoutManager);
            recyclerViewAllSchedules.setItemAnimator(new DefaultItemAnimator());
            recyclerViewAllSchedules.setAdapter(scheduleAdapter);
        } else {
            recyclerViewAllSchedules.setVisibility(View.GONE);
            textViewNoSchedule.setVisibility(View.VISIBLE);
        }
    }
}