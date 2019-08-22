package com.assettagging.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.assettagging.test.controller.CheckInternetConnection;
import com.assettagging.test.model.user_tracking.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.assettagging.test.R;

public class SpinnerAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Location> banklist = null;
    private ArrayList<Location> arraylist;
    String string;

    public SpinnerAdapter(Context context, List<Location> banklist, String string) {

        mContext = context;
        this.banklist = banklist;
        this.string = string;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Location>();
        this.arraylist.addAll(banklist);
    }

    public class ViewHolder {
        TextView bankname;
    }

    @Override
    public int getCount() {
        return banklist.size();
    }

    @Override
    public Location getItem(int position) {
        return banklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        holder = new ViewHolder();
        view = inflater.inflate(R.layout.bankname_adapter, null);
        // Locate the TextViews in listview_item.xml
        holder.bankname = view.findViewById(R.id.tv_banknameadapter);
        view.setTag(holder);
        if (string.equals("Location")) {
            if (CheckInternetConnection.isInternetConnected(mContext)) {
                if (banklist.get(position).getName().length() > 0)
                    holder.bankname.setText(banklist.get(position).getName());
            } else {
                if (banklist.get(position).getLocationNameOff().length() > 0)
                    holder.bankname.setText(banklist.get(position).getLocationNameOff());
            }
        } else if (string.equals("Project")) {
            if (CheckInternetConnection.isInternetConnected(mContext)) {
                if (banklist.get(position).getName().length() > 0)
                    holder.bankname.setText(banklist.get(position).getName());
            } else {
                if (banklist.get(position).getName().length() > 0)
                    holder.bankname.setText(banklist.get(position).getName());
            }
        } else if (string.equals("Asset Group")) {
            if (CheckInternetConnection.isInternetConnected(mContext)) {
                if (banklist.get(position).getName().length() > 0)
                    holder.bankname.setText(banklist.get(position).getName());
            } else {
                if (banklist.get(position).getName().length() > 0)
                    holder.bankname.setText(banklist.get(position).getName());
            }
        }


        return view;
    }

    String searchString = "";

    public void filter(String charText) {
        searchString = charText;

        charText = charText.toLowerCase(Locale.getDefault());
        banklist.clear();
        if (charText.length() == 0) {
            banklist.addAll(arraylist);
        } else {
            for (Location wp : arraylist) {
                if (CheckInternetConnection.isInternetConnected(mContext)) {
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        banklist.add(wp);
                    }
                } else {
                    if (wp.getLocationNameOff().toLowerCase(Locale.getDefault()).contains(charText)) {
                        banklist.add(wp);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }
}
