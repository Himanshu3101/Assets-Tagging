package com.assettagging.test.view.assetlink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.assettagging.test.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<UniversalSpinner> {

    LayoutInflater inflater;
    List<UniversalSpinner> universalSpinner;

    Context context;

    public SpinnerAdapter(Activity context, int resouceId, List<UniversalSpinner> list) {
        super(context, resouceId, list);
        inflater = context.getLayoutInflater();

        this.universalSpinner = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UniversalSpinner universalSpinner = getItem(position);

        //View rowview = inflater.inflate(R.layout.spinner_layout,null,true);

        View view = View.inflate(context, R.layout.spinner_layout, null);

        TextView txtTitle = view.findViewById(R.id.text1);
        txtTitle.setText(universalSpinner.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view = View.inflate(context, R.layout.spinner_layout, null);
        final TextView textView = view.findViewById(R.id.text1);
        textView.setText(universalSpinner.get(position).getName());


        return view;
    }
}
