package com.assettagging.test.view.schedule.upcoming;

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


public class UpcomingFragment extends Fragment {
    View view;
    @BindView(R.id.recyclerViewSchedule)
    RecyclerView recyclerViewSchedule;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;

    public static List<Schedule> upComingSchedule = new ArrayList<>();
    public ScheduleAdapter scheduleAdapter;
    Activity activity;

    public static UpcomingFragment newInstance() {
        UpcomingFragment fragment = new UpcomingFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_completed, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        setData();

        return view;
    }

    private void setData() {
        upComingSchedule.clear();
        try {
            upComingSchedule.addAll((Collection<? extends Schedule>) getArguments().getSerializable("List"));
        } catch (Exception e) {
        }
        setAdapter(upComingSchedule);
    }

    public void setAdapter(List<Schedule> upComingSchedule) {
        if (NavigationActivity.getInstance().scheduleData != null) {
            if (upComingSchedule.size() > 0) {
                recyclerViewSchedule.setVisibility(View.VISIBLE);
                textViewNoSchedule.setVisibility(View.GONE);
                scheduleAdapter = new ScheduleAdapter(activity, NavigationActivity.getInstance().scheduleData.getUpcomingSchedule());
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
            if (upComingSchedule.size() > 0) {
                recyclerViewSchedule.setVisibility(View.VISIBLE);
                textViewNoSchedule.setVisibility(View.GONE);
                scheduleAdapter = new ScheduleAdapter(activity, upComingSchedule);
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

    public void setRefreshedData() {
        scheduleAdapter.notifyDataSetChanged();
    }
}
