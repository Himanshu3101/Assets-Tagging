package com.assettagging.test.view.schedule.Ongoing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.view.schedule.ScheduleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OngoingFragment extends Fragment {
    View view;
    @BindView(R.id.recyclerViewSchedule)
    RecyclerView recyclerViewSchedule;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;

    public static List<Schedule> onGoingSchedule = new ArrayList<>();
    public ScheduleAdapter scheduleAdapter;
    public static Activity activity;

    public static OngoingFragment newInstance() {
        OngoingFragment fragment = new OngoingFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_completed, container, false);
        activity = getActivity();
        ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        onGoingSchedule.clear();
        try {
            onGoingSchedule.addAll((Collection<? extends Schedule>) getArguments().getSerializable("List"));
        } catch (Exception e) {
        }
        setAdapter(onGoingSchedule);
    }

    public void setAdapter(List<Schedule> onGoingSchedule) {
        if (NavigationActivity.getInstance().scheduleData != null) {
            if (onGoingSchedule.size() > 0) {
                recyclerViewSchedule.setVisibility(View.VISIBLE);
                textViewNoSchedule.setVisibility(View.GONE);
                scheduleAdapter = new ScheduleAdapter(activity, NavigationActivity.getInstance().scheduleData.getOngoingSchedule());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                recyclerViewSchedule.setLayoutManager(mLayoutManager);
                recyclerViewSchedule.setItemAnimator(new DefaultItemAnimator());
                recyclerViewSchedule.setAdapter(scheduleAdapter);
                scheduleAdapter.notifyDataSetChanged();
            } else {
                recyclerViewSchedule.setVisibility(View.GONE);
                textViewNoSchedule.setVisibility(View.VISIBLE);
            }

        } else {
            if (onGoingSchedule.size() > 0) {
                recyclerViewSchedule.setVisibility(View.VISIBLE);
                textViewNoSchedule.setVisibility(View.GONE);
                scheduleAdapter = new ScheduleAdapter(activity, onGoingSchedule);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                recyclerViewSchedule.setLayoutManager(mLayoutManager);
                recyclerViewSchedule.setItemAnimator(new DefaultItemAnimator());
                recyclerViewSchedule.setAdapter(scheduleAdapter);
                scheduleAdapter.notifyDataSetChanged();
            } else {
                recyclerViewSchedule.setVisibility(View.GONE);
                textViewNoSchedule.setVisibility(View.VISIBLE);
            }

        }
    }


}
