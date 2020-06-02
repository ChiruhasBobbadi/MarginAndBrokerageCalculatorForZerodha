package com.chiruhas.android.zerodha.View.Activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProviders;

import com.chiruhas.android.zerodha.Model.mmi.Mmi;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.mmi.MmiViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MMI_Activity extends AppCompatActivity {

    private Mmi mmiValue;
    private ProgressBar progressBar, bar;
    private TextView mmiView, mmiMain, mmiSummary, getapp;
    private String mmi = "";
    private MmiViewModel viewModel;
    private FrameLayout adContainerView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmi);

        init();

        fetchData();
    }

    private void fetchData() {
        viewModel = ViewModelProviders.of(this).get(MmiViewModel.class);
        viewModel.fetchMmi().observe(this, mmi -> {
            mmiValue = mmi;

            updateVisibility();
            calculateMMI();
            animate();
            changeContent();


        });
    }

    private void init() {
        getSupportActionBar().setTitle("Market Mood Index");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        getapp = findViewById(R.id.source);
        getapp.setMovementMethod(LinkMovementMethod.getInstance());
        mmiView = findViewById(R.id.mmiValue);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        mmiMain = findViewById(R.id.mmiMain);
        mmiSummary = findViewById(R.id.mmiSummary);
        initAds();
        loadBanner();
    }

    private void initAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.mmi_banner));
        adContainerView.addView(adView);
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

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private void animate() {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) mmiValue.getMmi());
        animation.setDuration(2000); // in milliseconds
        animation.setInterpolator(new AccelerateInterpolator());
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

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}
