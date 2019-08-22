package com.assettagging.test.view.schedule;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.controller.Constants;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.locationwise.LocationWiseActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private List<Schedule> scheduleList;
    private Activity activity;
    String disposal;
    //  Set<Schedule> schedulesSet;

    public ScheduleAdapter(Activity activity, List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
        this.activity = activity;
        Collections.sort(this.scheduleList);
        //  schedulesSet = new HashSet<>(this.scheduleList);
        //   this.scheduleList.clear();
        //  this.scheduleList.addAll(schedulesSet);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewDesc)
        public TextView textViewDesc;
        @BindView(R.id.textViewScheduleID)
        public TextView textViewScheduleID;

        @BindView(R.id.textViewDateOfDisposal)
        public TextView textViewDateOfDisposal;
        @BindView(R.id.textViewStatus)
        public TextView textViewStatus;

        @BindView(R.id.linearLayoutRow)
        public LinearLayout linearLayoutRow;
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
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Schedule schedule = scheduleList.get(position);
        holder.textViewDesc.setText(schedule.getSCHEDULEDESCRIPTION());
        holder.textViewScheduleID.setText(schedule.getSCHEDULEID());
        holder.textViewDateOfDisposal.setText(schedule.getSTARTTIME());
        holder.textViewStatus.setText(schedule.getStatus());

        holder.linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ScheduleFragmnet.position == 1 || ScheduleFragmnet.position == 2) {
                    activity.startActivity(new Intent(activity, LocationWiseActivity.class).putExtra(Constants.STATUS, scheduleList.get(position).getStatus()).putExtra(Constants.SCHEDULE_ID, scheduleList.get(position).getSCHEDULEID()).putExtra(Constants.EmpID, Preferance.getEmpId(activity)));
                    activity.overridePendingTransition(0, 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}