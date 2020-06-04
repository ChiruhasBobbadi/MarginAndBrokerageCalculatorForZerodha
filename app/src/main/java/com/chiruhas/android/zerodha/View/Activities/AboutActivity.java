package com.chiruhas.android.zerodha.View.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chiruhas.android.zerodha.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //line.animate().scaleX(-100f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(1000);

    }


}
