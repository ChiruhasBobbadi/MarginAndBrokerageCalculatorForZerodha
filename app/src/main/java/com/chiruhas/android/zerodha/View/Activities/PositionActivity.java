package com.chiruhas.android.zerodha.View.Activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chiruhas.android.zerodha.HelperClasses.PositionHelper;
import com.chiruhas.android.zerodha.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PositionActivity extends AppCompatActivity {

    private EditText cap, rsk, ent, slm, tar;
    private String capital, risk, entry, sl, target;
    private Button cal;

    private FrameLayout adContainerView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().setTitle("Position Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAds();
        loadBanner();

        cap = findViewById(R.id.capital);
        rsk = findViewById(R.id.risk);
        ent = findViewById(R.id.entry);
        slm = findViewById(R.id.sl);
        tar = findViewById(R.id.target);


        cal = findViewById(R.id.calculate);
        cal.setOnClickListener(view -> {
            capital = cap.getText().toString().trim();
            risk = rsk.getText().toString().trim();
            entry = ent.getText().toString().trim();
            sl = slm.getText().toString().trim();
            target = tar.getText().toString().trim();

            if (capital.isEmpty() || risk.isEmpty() || entry.isEmpty() || sl.isEmpty() || target.isEmpty())
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show();

            else {

                double c, r, e, s, t;
                c = Double.parseDouble(capital);
                r = Double.parseDouble(risk);
                e = Double.parseDouble(entry);
                s = Double.parseDouble(sl);
                t = Double.parseDouble(target);

                if (s == e)
                    Toast.makeText(this, "Stoploss should be greater or less than entry", Toast.LENGTH_SHORT).show();
                else
                    new PositionHelper(PositionActivity.this).calculatePosition(c, r, e, s, t);
            }
        });


    }

    private void initAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.risk_calc_banner));
        adContainerView.addView(adView);
    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
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
