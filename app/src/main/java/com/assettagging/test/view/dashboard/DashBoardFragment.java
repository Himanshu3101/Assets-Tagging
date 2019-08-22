package com.assettagging.test.view.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.all_data.ActvityCount;
import com.assettagging.test.model.all_data.AllData;
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.model.schedule.ScheduleData;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.custom_control.CustomProgress;
import com.assettagging.test.view.navigation.NavigationActivity;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardFragment extends Fragment {
    @BindView(R.id.textViewCompleted)
    TextView textViewCompleted;
    @BindView(R.id.textViewTotal)
    TextView textViewTotal;
    @BindView(R.id.textViewOngoing)
    TextView textViewOngoing;
    @BindView(R.id.textViewUpcoming)
    TextView textViewUpcoming;
    @BindView(R.id.textViewAssetDisposer)
    TextView textViewAssetDisposer;
    @BindView(R.id.linearLayoutTotal)
    LinearLayout linearLayoutTotal;
    @BindView(R.id.linearLayoutDisposer)
    LinearLayout linearLayoutDisposer;
    @BindView(R.id.linearLayoutCompleted)
    LinearLayout linearLayoutCompleted;

    @BindView(R.id.linearLayoutOngoing)
    LinearLayout linearLayoutOngoing;
    @BindView(R.id.linearLayoutUpComing)
    LinearLayout linearLayoutUpComing;
    List<ActvityCount> actvityCountList;

    public static DashBoardFragment instance;
    public PopupWindow popupWindow;

//    public static DashBoardFragment newInstance() {
//        DashBoardFragment fragment = new DashBoardFragment();
//
//        return fragment;
//    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        if (Preferance.getTheme(getActivity()).equals("ORANGE")) {
            setFragmentAccTheme(getActivity().getResources().getColor(R.color.colorAccent));
        } else if (Preferance.getTheme(getActivity()).equals("BLUE")) {
            setFragmentAccTheme(getActivity().getResources().getColor(R.color.colorAccentBlue));
        }
        CustomProgress.startProgress(getActivity());
        setdata(null);
        if (CheckInternetConnection.isInternetConnected(getContext())) {
            NavigationActivity.getInstance().getScheduleData();
        } else {
            CustomProgress.startProgress(getActivity());
            NavigationActivity.getInstance().getDataFromDatabase();
        }
        showInfo();
        return view;
    }

    private void showInfo() {
        updatingForDynamicLocationViews();
    }

    private void updatingForDynamicLocationViews() {

    }

    private void setFragmentAccTheme(int color) {
        linearLayoutTotal.setBackgroundColor(color);
        linearLayoutUpComing.setBackgroundColor(color);
        linearLayoutOngoing.setBackgroundColor(color);
        linearLayoutCompleted.setBackgroundColor(color);
        linearLayoutDisposer.setBackgroundColor(color);
    }


    private void setAllDataResponse(AllData body) throws IOException {
        if (body == null) {
            SaveDatainDataBase(null);
        } else {
            SaveDatainDataBase(body);

        }

    }


    private void SaveDatainDataBase(AllData body) throws IOException {
        if (body != null) {
            DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
            dbHelper.dropTable();
            dbHelper.insertUSER(body.getUserList());
            dbHelper.insertOngoingSCHEDULE(body.getOngoingSchedule());
            dbHelper.insertUpcomingSCHEDULE(body.getUpcomingSchedule());
            dbHelper.insertScheduleDetail(body.getScheduleDetail(), body.getItemCurentStatusList());
            dbHelper.insertScheduleLocationTask(body.getScheduleLocationTask());
            dbHelper.insertscheduleLocation(body.getScheduleLocation());
            actvityCountList = new ArrayList<>();
            actvityCountList.clear();
            actvityCountList.addAll(body.getActvityCount());
            if (actvityCountList.get(0).getCompInspectionCount() == null) {
                actvityCountList.get(0).setCompInspectionCount("0");
            }
            if (actvityCountList.get(0).getCompMovementCount() == null) {
                actvityCountList.get(0).setCompMovementCount("0");
            }
            if (actvityCountList.get(0).getCompTaggingCount() == null) {
                actvityCountList.get(0).setCompTaggingCount("0");
            }
            if (actvityCountList.get(0).getUpcomeMovementCount() == null) {
                actvityCountList.get(0).setUpcomeMovementCount("0");
            }
            if (actvityCountList.get(0).getUpcomeTaggingCount() == null) {
                actvityCountList.get(0).setUpcomeTaggingCount("0");
            }
            if (actvityCountList.get(0).getUpcomeInspectionCount() == null) {
                actvityCountList.get(0).setUpcomeInspectionCount("0");
            }
            if (actvityCountList.get(0).getOngoingMovementCount() == null) {
                actvityCountList.get(0).setOngoingMovementCount("0");
            }
            if (actvityCountList.get(0).getOngoingTaggingCount() == null) {
                actvityCountList.get(0).setOngoingTaggingCount("0");
            }
            if (actvityCountList.get(0).getOngoingInspectionCount() == null) {
                actvityCountList.get(0).setOngoingInspectionCount("0");
            }

        }


    }

    public void setdata(final ScheduleData scheduleData) {
        if (scheduleData != null) {
            if (scheduleData.getUpcomingSchedule() == null) {
                List<Schedule> schedules = new ArrayList<>();
                scheduleData.setUpcomingSchedule(schedules);
            }
            if (scheduleData.getOngoingSchedule() == null) {
                List<Schedule> schedules = new ArrayList<>();
                scheduleData.setOngoingSchedule(schedules);
            }
            if (scheduleData.getCompSchedule() == null) {
                List<Schedule> schedules = new ArrayList<>();
                scheduleData.setCompSchedule(schedules);
            }
            if (scheduleData.getActvityCount() != null) {
                if (scheduleData.getActvityCount().get(0).getDisposalCreateCount() == null) {
                    scheduleData.getActvityCount().get(0).setDisposalCreateCount("0");
                }
                if (scheduleData.getActvityCount().get(0).getDisposalSubmitCount() == null) {
                    scheduleData.getActvityCount().get(0).setDisposalSubmitCount("0");
                }
            } else {
                scheduleData.getActvityCount().get(0).setDisposalCreateCount("0");
                scheduleData.getActvityCount().get(0).setDisposalSubmitCount("0");
            }
            List<CreatedDisposalList> schedules = new ArrayList<>();
            if (!NavigationActivity.firstTime) {
                showValueWithAnimation(scheduleData.getCompSchedule().size(), textViewCompleted);
                showValueWithAnimation(scheduleData.getOngoingSchedule().size(), textViewOngoing);
                showValueWithAnimation(scheduleData.getUpcomingSchedule().size(), textViewUpcoming);
                showValueWithAnimation(Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalCreateCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalSubmitCount()) + schedules.size(), textViewAssetDisposer);
                showValueWithAnimation( scheduleData.getCompSchedule().size() + scheduleData.getOngoingSchedule().size()+scheduleData.getUpcomingSchedule().size()+Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalCreateCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalSubmitCount()) + schedules.size(), textViewTotal);
            } else {
                textViewCompleted.setText(scheduleData.getCompSchedule().size() + "");
                textViewOngoing.setText(scheduleData.getOngoingSchedule().size() + "");
                textViewUpcoming.setText(scheduleData.getUpcomingSchedule().size() + "");
                textViewTotal.setText(Integer.parseInt(textViewCompleted.getText().toString()) + Integer.parseInt(textViewOngoing.getText().toString()) + Integer.parseInt(textViewUpcoming.getText().toString())+Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalCreateCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalSubmitCount()) + schedules.size() + "");
                textViewAssetDisposer.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalCreateCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalSubmitCount()) + schedules.size() + "");
            }

            linearLayoutTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptions(linearLayoutTotal, scheduleData);
                }
            });
            linearLayoutCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptions(linearLayoutCompleted, scheduleData);
                }
            });
            linearLayoutOngoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptions(linearLayoutOngoing, scheduleData);
                }
            });
            linearLayoutUpComing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptions(linearLayoutUpComing, scheduleData);
                }
            });
            linearLayoutDisposer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptions(linearLayoutDisposer, scheduleData);
                }
            });
        }
        CustomProgress.endProgress();

    }

    private void showValueWithAnimation(int total, final TextView textView) {
        ValueAnimator animator = ValueAnimator.ofInt(0, total);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    public boolean opened = false;

    private void showOptions(View view, ScheduleData scheduleData) {
        if (opened == false) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.options_layout, null);
            TextView textViewNormal = customView.findViewById(R.id.textViewNormal);
            TextView textViewInspection = customView.findViewById(R.id.textViewInspection);
            TextView textViewTagging = customView.findViewById(R.id.textViewTagging);

            TextView firstName = customView.findViewById(R.id.firstName);
            TextView secondName = customView.findViewById(R.id.secondName);
            TextView thirdName = customView.findViewById(R.id.thirdName);
            if (scheduleData.getActvityCount().get(0).getCompInspectionCount() == null) {
                scheduleData.getActvityCount().get(0).setCompInspectionCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getCompMovementCount() == null) {
                scheduleData.getActvityCount().get(0).setCompMovementCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getCompTaggingCount() == null) {
                scheduleData.getActvityCount().get(0).setCompTaggingCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getOngoingInspectionCount() == null) {
                scheduleData.getActvityCount().get(0).setOngoingInspectionCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getOngoingMovementCount() == null) {
                scheduleData.getActvityCount().get(0).setOngoingMovementCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getOngoingTaggingCount() == null) {
                scheduleData.getActvityCount().get(0).setOngoingTaggingCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getUpcomeInspectionCount() == null) {
                scheduleData.getActvityCount().get(0).setUpcomeInspectionCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getUpcomeMovementCount() == null) {
                scheduleData.getActvityCount().get(0).setUpcomeMovementCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getUpcomeTaggingCount() == null) {
                scheduleData.getActvityCount().get(0).setUpcomeTaggingCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getDisposalSubmitCount() == null) {
                scheduleData.getActvityCount().get(0).setDisposalSubmitCount("0");
            }
            if (scheduleData.getActvityCount().get(0).getDisposalCreateCount() == null) {
                scheduleData.getActvityCount().get(0).setDisposalCreateCount("0");
            }
            if (view == linearLayoutCompleted) {
                textViewInspection.setText(scheduleData.getActvityCount().get(0).getCompInspectionCount() + "");
                textViewNormal.setText(scheduleData.getActvityCount().get(0).getCompMovementCount() + "");
                textViewTagging.setText(scheduleData.getActvityCount().get(0).getCompTaggingCount() + "");
            } else if (view == linearLayoutOngoing) {

                textViewInspection.setText(scheduleData.getActvityCount().get(0).getOngoingInspectionCount() + "");
                textViewNormal.setText(scheduleData.getActvityCount().get(0).getOngoingMovementCount() + "");
                textViewTagging.setText(scheduleData.getActvityCount().get(0).getOngoingTaggingCount() + "");
            } else if (view == linearLayoutUpComing) {

                textViewInspection.setText(scheduleData.getActvityCount().get(0).getUpcomeInspectionCount() + "");
                textViewNormal.setText(scheduleData.getActvityCount().get(0).getUpcomeMovementCount() + "");
                textViewTagging.setText(scheduleData.getActvityCount().get(0).getUpcomeTaggingCount() + "");
            } else if (view == linearLayoutTotal) {
                textViewInspection.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getCompInspectionCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getUpcomeInspectionCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getOngoingInspectionCount()) + "");
                textViewNormal.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getCompMovementCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getUpcomeMovementCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getOngoingMovementCount()) + "");
                textViewTagging.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getCompTaggingCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getUpcomeTaggingCount()) + Integer.parseInt(scheduleData.getActvityCount().get(0).getOngoingTaggingCount()) + "");
            } else if (view == linearLayoutDisposer) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
                List<CreatedDisposalList> schedules = new ArrayList<>();
                schedules.addAll(dataBaseHelper.getAllDisposedSchedule());
                textViewInspection.setText(schedules.size() + "");
                textViewNormal.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalCreateCount()) + "");
                textViewTagging.setText(Integer.parseInt(scheduleData.getActvityCount().get(0).getDisposalSubmitCount()) + "");
                firstName.setText("Yet To Submit:");
                secondName.setText("OnGoing:");
                thirdName.setText("Completed");
            }
            if (popupWindow != null) {
                popupWindow.dismiss();
                popupWindow = null;
            }
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (view == linearLayoutDisposer) {
                popupWindow.showAsDropDown(view, 10, 50, 0);
            } else {
                popupWindow.showAsDropDown(view, 10, -50, 0);
            }
            opened = true;
        } else {
            popupWindow.dismiss();
            popupWindow = null;
            opened = false;

        }
    }

    private void startCountAnimation(final int finsh) {
        ValueAnimator animator = ValueAnimator.ofInt(0, finsh);
        animator.setDuration(700);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textViewTotal.setText(finsh + "");
            }
        });
        animator.start();
    }


    public static DashBoardFragment getInstance() {
        return instance;
    }
}
