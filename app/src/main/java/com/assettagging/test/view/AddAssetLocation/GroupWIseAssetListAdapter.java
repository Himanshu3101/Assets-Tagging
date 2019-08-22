package com.assettagging.test.view.AddAssetLocation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.model.assetList.disposalassetlist;
import com.assettagging.test.model.asset_detai.BarcodeWiseDataList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IACE on 29-Nov-18.
 */

public class GroupWIseAssetListAdapter extends RecyclerView.Adapter<GroupWIseAssetListAdapter.ViewHolder> {
  //  private final HashSet<disposalassetlist> disposalassetlistHashSet;
    private List<disposalassetlist> mData;
    private Activity activity;
    public List<BarcodeWiseDataList> checkedList = new ArrayList<>();
    private static GroupWIseAssetListAdapter instance;
    boolean items[];

    public GroupWIseAssetListAdapter(Activity activity, List<disposalassetlist> groupwiseAssetsList) {
        this.mData = groupwiseAssetsList;
        this.activity = activity;
        instance = this;
        checkedList.clear();
        items = new boolean[this.mData.size()];
    //    disposalassetlistHashSet = new HashSet<>(this.mData);
     //   this.mData.clear();
     //   this.mData.addAll(disposalassetlistHashSet);
    }

    public static GroupWIseAssetListAdapter getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grouppwise_assetlist_adapter, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.textViewAssetsID.setText(mData.get(position).getAssetId());
        viewHolder.textViewName.setText(mData.get(position).getName());
        viewHolder.textViewStatus.setText(mData.get(position).getStatus());

        viewHolder.checkboxSelectedAsset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    items[position] = true;
                } else {
                    items[position] = false;
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkboxSelectedAsset.isChecked()) {
                    viewHolder.checkboxSelectedAsset.setChecked(false);
                    items[position] = false;
                } else {
                    viewHolder.checkboxSelectedAsset.setChecked(true);
                    items[position] = true;
                }
                notifyDataSetChanged();

            }
        });
        if (items[position]) {
            BarcodeWiseDataList barcodeWiseDataList = new BarcodeWiseDataList();
            barcodeWiseDataList.setASSETID(mData.get(position).getAssetId());
            barcodeWiseDataList.setStatus("Started");
            barcodeWiseDataList.setBarcode(mData.get(position).getBarcode());
            barcodeWiseDataList.setLOCATION(mData.get(position).getLocation());
            barcodeWiseDataList.setNAME(mData.get(position).getName());
            checkedList.add(barcodeWiseDataList);
        }

        if (checkedList.size() > 0)
            for (int i = 0; i < checkedList.size(); i++) {
                if (!items[position]) {
                    if (mData.get(position).getAssetId().equals(checkedList.get(i).getASSETID())) {
                        checkedList.remove(i);
                        break;
                    }
                }
            }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewAssetsID)
        public TextView textViewAssetsID;
        @BindView(R.id.textViewName)
        public TextView textViewName;
        @BindView(R.id.textViewStatus)
        public TextView textViewStatus;
        @BindView(R.id.card_view)
        public CardView card_view;

        @BindView(R.id.checkboxSelectedAsset)
        public CheckBox checkboxSelectedAsset;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
