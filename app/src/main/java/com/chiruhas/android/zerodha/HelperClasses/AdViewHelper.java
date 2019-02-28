package com.chiruhas.android.zerodha.HelperClasses;

import android.view.View;

import com.chiruhas.android.zerodha.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdViewHelper
{

    public static void  loadBanner(View view){
        AdView adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
