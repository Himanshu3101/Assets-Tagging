package com.assettagging.test.view.AddAssetLocation;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.model.user_tracking.Location;

import java.util.HashSet;
import java.util.List;

public class LocationssssAdapter extends ArrayAdapter<Location> {
    private final LayoutInflater mInflater;
  //  private final HashSet<Location> locationsHashSet;
    public List<Location> locations;
    private Activity activity;
    String string;
    int mResources;

    public LocationssssAdapter(Activity activity, int row_add_assets, List<Location> locations) {
        super(activity, row_add_assets, locations);
        this.string = string;
        this.locations = locations;
        this.activity = activity;
        mResources = row_add_assets;
        mInflater = LayoutInflater.from(activity);
    //    locationsHashSet = new HashSet<>(this.locations);
     //   this.locations.clear();
     //   this.locations.addAll(locationsHashSet);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(activity);
        label.setTextColor(Color.BLACK);
        label.setText(locations.get(position).getName());
        return label;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return createItemView(position, convertView, parent);

    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mResources, parent, false);

        TextView textViewNameTitle = (TextView) convertView.findViewById(R.id.textViewNameTitle);
        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);

        Location location = locations.get(position);
        textViewNameTitle.setText(activity.getResources().getString(R.string.locationid));
        textViewName.setText(location.getName());

        return convertView;
    }
}
