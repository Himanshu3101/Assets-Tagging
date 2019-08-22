package com.assettagging.test.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.assettagging.test.R;
import com.assettagging.test.preference.Preferance;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Preferance.getTheme(this).equals("ORANGE")) {
            setTheme(R.style.AppTheme);
        } else if (Preferance.getTheme(getApplicationContext()).equals("BLUE")) {
            setTheme(R.style.AppThemeBlue);
        }
        super.onCreate(savedInstanceState);
    }
}