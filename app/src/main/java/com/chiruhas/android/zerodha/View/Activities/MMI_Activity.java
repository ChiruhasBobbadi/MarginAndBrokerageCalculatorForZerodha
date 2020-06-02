package com.chiruhas.android.zerodha.View.Activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.Model.mmi.Mmi;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.mmi.MmiViewModel;

public class MMI_Activity extends AppCompatActivity {
    private Mmi mmiValue;
    private ProgressBar progressBar, bar;
    private TextView mmiView, mmiMain, mmiSummary, getapp;
    private String mmi = "";
    private MmiViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmi);
        progressBar = findViewById(R.id.progressBar);
        getapp = findViewById(R.id.source);
        getapp.setMovementMethod(LinkMovementMethod.getInstance());
        mmiView = findViewById(R.id.mmiValue);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        mmiMain = findViewById(R.id.mmiMain);
        mmiSummary = findViewById(R.id.mmiSummary);

        viewModel = ViewModelProviders.of(this).get(MmiViewModel.class);
        viewModel.fetchMmi().observe(this, mmi -> {
            mmiValue = mmi;

            updateVisibility();
            calculateMMI();
            animate();
            changeContent();


        });
    }

    private void updateVisibility() {
        bar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getapp.setVisibility(View.VISIBLE);
        mmiView.setVisibility(View.VISIBLE);
        mmiMain.setVisibility(View.VISIBLE);
        mmiSummary.setVisibility(View.VISIBLE);

    }

    private void calculateMMI() {
        int myColor;
        if (mmiValue.getMmi() < 30) {
            mmi = "Extreme Fear";
            myColor = getResources().getColor(R.color.bg_green);

        } else if (mmiValue.getMmi() > 30 && mmiValue.getMmi() < 50) {
            mmi = "Fear";
            myColor = getResources().getColor(R.color.bg_fear);
        } else if (mmiValue.getMmi() > 50 && mmiValue.getMmi() < 70) {
            myColor = getResources().getColor(R.color.bg_greed);
            mmi = "Greed";
        } else {
            mmi = "Extreme Greed";
            myColor = getResources().getColor(R.color.bg_red);
        }
        DrawableCompat.setTint(progressBar.getProgressDrawable(), myColor);
        mmiView.setText(mmiValue.getMmi() + "");
        mmiView.setTextColor(myColor);
    }

    private void animate() {

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) mmiValue.getMmi());
        animation.setDuration(3000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void changeContent() {
        switch (mmi) {
            case "Extreme Fear":
                mmiMain.setText("MMI is in the Extreme Fear Zone.");
                mmiSummary.setText("High extreme fear (<20) suggests a good time to open fresh positions, as markets are likely to be oversold and might turn upwards");
                break;
            case "Fear":
                mmiMain.setText("MMI is in the Fear Zone.");
                mmiSummary.setText("If it is dropping from Greed to Fear, it means fear is increasing in the market & investors should wait till it reaches Extreme Fear, as that is when the market is expected to turn upwards\n\nIf MMI is coming from Extreme fear, it means fear is reducing in the market. If not best, might be a good time to open fresh positions.");
                break;
            case "Greed":
                mmiMain.setText("MMI is in the Greed Zone.");
                mmiSummary.setText("If MMI is coming Neutral towards Greed zone, it means greed is increasing in the market and investors should be cautious in opening new positions.\n\nIf MMI is dropping from Extreme Greed, it means greed is reducing in the market. But more patience is suggested before looking for fresh opportunities.");
                break;
            case "Extreme Greed":
                mmiMain.setText("MMI is in the Extreme Greed Zone.");
                mmiSummary.setText("High extreme greed (>80) suggests investors should be cautious in opening fresh positions as markets are overbought and likely to turn downwards.");
                break;


        }
    }
}
