package com.assettagging.test.view.assetdisposer.yet_to_submit;

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
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.assetdisposer.existing.AddAssetDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YetToSubmitDisposerAdapter extends RecyclerView.Adapter<YetToSubmitDisposerAdapter.MyViewHolder> {

    //  private final HashSet<CreatedDisposalList> scheduleListHashSet;
    private List<CreatedDisposalList> scheduleList;
    private Activity activity;
    String disposal;
    public static boolean sort = false;

    public YetToSubmitDisposerAdapter(Activity activity, List<CreatedDisposalList> scheduleList) {
        this.scheduleList = scheduleList;
        this.activity = activity;
        Collections.sort(this.scheduleList);
        //   scheduleListHashSet = new HashSet<>(this.scheduleList);
        //  this.scheduleList.clear();
        //  this.scheduleList.addAll(scheduleListHashSet);
    }

    public void sortList() {
        if (sort == false) {
            sort = true;
        } else if (sort == true) {
            sort = false;
        }
        Collections.sort(scheduleList);
        notifyDataSetChanged();

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
                .inflate(R.layout.row_asset_disposal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyyhh:mm:ss a");
            Date newDate = null;
            newDate = format.parse(scheduleList.get(position).getDisposalDate());
            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
            holder.textViewDateOfDisposal.setText(format1.format(newDate));
            holder.textViewDesc.setText(scheduleList.get(position).getDescription());
            holder.textViewScheduleID.setText(scheduleList.get(position).getDisposalScheduleHeaderId());
            holder.textViewStatus.setText(scheduleList.get(position).getStatus());
            // Collections.sort(scheduleList);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.textViewDateOfDisposal.setText(scheduleList.get(position).getDisposalDate());
            holder.textViewDesc.setText(scheduleList.get(position).getDescription());
            holder.textViewScheduleID.setText(scheduleList.get(position).getDisposalScheduleHeaderId());
            holder.textViewStatus.setText(scheduleList.get(position).getStatus());
            //   Collections.sort(scheduleList);
        }

        holder.linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = position;
                activity.startActivity(new Intent(activity, AddAssetDetailActivity.class).putExtra(Constants.TYPE, scheduleList.get(i).getType()).putExtra(Constants.SCHEDULE_NAME, scheduleList.get(i).getDescription()).putExtra(Constants.DISPOSAL_DATE, scheduleList.get(i).getDisposalDate()).putExtra(Constants.SCHEDULE_ID, scheduleList.get(i).getDisposalScheduleHeaderId()));
                activity.overridePendingTransition(0, 0);

            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}