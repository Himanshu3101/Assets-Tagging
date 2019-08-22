package com.assettagging.test.view.locationwise;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.view.taskLocationWise.TaskWiseActivity;
import com.assettagging.test.controller.Constants;
import com.assettagging.test.model.locationwise.ScheduleLocation;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationWiseAdapter extends RecyclerView.Adapter<LocationWiseAdapter.MyViewHolder> {

  //  private final HashSet<ScheduleLocation> locationListHashSet;
    private List<ScheduleLocation> locationList;
    private Activity activity;

    public LocationWiseAdapter(Activity activity, List<ScheduleLocation> locationList) {
        this.locationList = locationList;
        this.activity = activity;
    //    locationListHashSet = new HashSet<>(this.locationList);
     //   this.locationList.clear();
     //   this.locationList.addAll(locationListHashSet);
        }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewLocation)
        public TextView textViewLocation;
        @BindView(R.id.textViewLocationNAme)
        public TextView textViewLocationNAme;
        @BindView(R.id.linearLayoutRow)
        public LinearLayout linearLayoutRow;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locationwise_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textViewLocation.setText(locationList.get(position).getLocation());
        holder.textViewLocationNAme.setText(locationList.get(position).getLocationName());

        holder.linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, TaskWiseActivity.class).putExtra(Constants.LOCATION_Name, locationList.get(position).getLocationName()).putExtra(Constants.SCHEDULE_ID, locationList.get(position).getSCHEDULEID()).putExtra(Constants.LOCATION, locationList.get(position).getLocation()).putExtra(Constants.EmpID, locationList.get(position).getEMPID()));
                activity.overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}