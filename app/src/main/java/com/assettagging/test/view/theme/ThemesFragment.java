
package com.assettagging.test.view.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.assettagging.test.MyApplication;
import com.assettagging.test.R;
import com.assettagging.test.preference.Preferance;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThemesFragment extends Fragment {

    View view;
    @BindView(R.id.relativeLayoutOrange)
    RelativeLayout relativeLayoutOrange;
    @BindView(R.id.relativeLayoutBlue)
    RelativeLayout relativeLayoutBlue;
    @BindView(R.id.checkboxOrange)
    CheckBox checkboxOrange;
    @BindView(R.id.checkboxBlue)
    CheckBox checkboxBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_themes, container, false);
        ButterKnife.bind(this, view);
        if (Preferance.getTheme(getActivity()).equals("ORANGE")) {
            checkboxOrange.setChecked(true);
            checkboxBlue.setChecked(false);
        } else if (Preferance.getTheme(getActivity()).equals("BLUE")) {
            checkboxBlue.setChecked(true);
            checkboxOrange.setChecked(false);
        }
        onThemeClick();
        return view;
    }

    private void onThemeClick() {
        relativeLayoutOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxOrange.setChecked(true);
                checkboxBlue.setChecked(false);
                Preferance.saveTheme(getActivity(), "ORANGE");
                MyApplication.getInstance().changeTheme(R.style.AppTheme);
                getActivity().finish();
                final Intent intent = getActivity().getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        relativeLayoutBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxBlue.setChecked(true);
                checkboxBlue.setChecked(false);
                Preferance.saveTheme(getActivity(), "BLUE");
                MyApplication.getInstance().changeTheme(R.style.AppThemeBlue);
                getActivity().finish();
                final Intent intent = getActivity().getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }


}
