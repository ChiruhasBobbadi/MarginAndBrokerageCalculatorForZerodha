package com.chiruhas.android.zerodha.View.Activities;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CurrencyAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class CurrencyActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG = "CurrencyActivity";
    private ViewModel view;
    private RecyclerView rv;
    private CurrencyAdapter adapter;
    private ProgressBar bar;
    private List<Currency> currency = new ArrayList<>();
    private RewardedVideoAd videoAd;
    private Currency currencyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        Log.d(TAG, "onCreate: sucessful");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Currency Margins");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        videoAd = MobileAds.getRewardedVideoAdInstance(this);
        videoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();


        View rootView = getWindow().getDecorView().getRootView();

        AdViewHelper.loadBanner(rootView);
        setAdapter();
        fetchData();

    }

    /**
     * Reward video helper method
     */
    private void loadRewardedVideoAd() {


        //original
        videoAd.loadAd(getResources().getString(R.string.currency_reward),
                new AdRequest.Builder().build());
//    }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                List<Currency> em = new ArrayList<>();
                for (Currency e : currency) {
                    if (e.getScrip().toLowerCase().startsWith(s)) {
                        em.add(e);
                    }
                }
                adapter.updateData(em);


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        rv = findViewById(R.id.rv);
        adapter = new CurrencyAdapter(item -> {

            currencyItem = item;

            if(videoAd.isLoaded())
           {
               videoAd.show();
           }else{
               AlertHelper alertHelper = new AlertHelper(CurrencyActivity.this);

               alertHelper.loadCurrencyPopUp(item);
           }






        });


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }

    public void showPopup(){
        AlertHelper alertHelper = new AlertHelper(CurrencyActivity.this);

        alertHelper.loadCurrencyPopUp(currencyItem);
    }

    public void fetchData() {
        view = ViewModelProviders.of(this).get(ViewModel.class);
        view.fetchCurrency().observe(this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(List<Currency> godModels) {


                currency = godModels;

                adapter.updateData(godModels);

                bar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        showPopup();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}



