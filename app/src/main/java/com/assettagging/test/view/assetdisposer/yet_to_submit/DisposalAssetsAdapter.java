package com.assettagging.test.view.assetdisposer.yet_to_submit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.assetList.disposalassetlist;
import com.assettagging.test.model.asset_disposal.DisposalWiseDataList;
import com.assettagging.test.model.schedule.Schedule;
import com.assettagging.test.preference.Preferance;
import com.assettagging.test.view.assetdisposer.DisposerFragmnet;
import com.assettagging.test.view.assetdisposer.existing.AddAssetDetailActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisposalAssetsAdapter extends RecyclerView.Adapter<DisposalAssetsAdapter.MyViewHolder> {

    public List<disposalassetlist> disposalAssets;
    private Activity activity;
   // Set<disposalassetlist> disposalWiseDataListSet;


    public DisposalAssetsAdapter(Activity activity, List<disposalassetlist> disposalAssets) {
        this.disposalAssets = disposalAssets;
        this.activity = activity;
      //  disposalWiseDataListSet = new HashSet<>(this.disposalAssets);
      //  this.disposalAssets.clear();
      //  this.disposalAssets.addAll(disposalWiseDataListSet);
         }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.textViewAssetId)
        public TextView textViewAssetId;

        @BindView(R.id.textViewAssetName)
        public TextView textViewAssetName;
        @BindView(R.id.textViewAssetStatus)
        public TextView textViewAssetStatus;

        @BindView(R.id.imageViewAssetIcon)
        public ImageView imageViewAssetIcon;
        @BindView(R.id.imageViewClose)
        public ImageView imageViewClose;
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
                .inflate(R.layout.rowadddisposal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textViewAssetId.setText(disposalAssets.get(position).getAssetId());
        holder.textViewAssetName.setText(disposalAssets.get(position).getName());
        holder.textViewAssetStatus.setText(disposalAssets.get(position).getStatus());
        if (DisposerFragmnet.position == 0) {
            holder.imageViewClose.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewClose.setVisibility(View.GONE);
        }
        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForTwoButton("Are you sure you want to delete?", position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return disposalAssets.size();
    }

    public void showAlertForTwoButton(String message, final int position) {
        AlertDialog alertDialog = null;
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        alertDialog = new AlertDialog.Builder(
                activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();

        alertDialog.setMessage(message);

        // Setting Icon to Dialog

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
                dataBaseHelper.dropASSETSROwTable(activity, AddAssetDetailActivity.getInstance().SCHEDULE_ID, disposalAssets.get(position).getAssetId());
                disposalAssets.remove(position);
                notifyDataSetChanged();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}