package com.chiruhas.android.zerodha.View.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CommodityAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.ZerodhaViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class CommodityActivity extends AppCompatActivity implements RewardedVideoAdListener {


    private static final String TAG = "Commodity Activity";
    private RecyclerView recyclerView;
    private ZerodhaViewModel viewModel;
    private AstaViewModel astaViewModel;
    private CommodityAdapter commodityAdapter;
    private ProgressBar bar;
    //CommodityViewModel commodityViewModel;
    private List<Commodity> list = new ArrayList<>();
    private RewardedVideoAd videoAd;
    private Commodity commodity;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);


        init();


        setAdapter();
        zerodhaCall();


        rg.setOnCheckedChangeListener((group, checkedId) -> {
            commodityAdapter.updateData(new ArrayList<>());
            bar.setVisibility(View.VISIBLE);
            switch (checkedId) {
                case R.id.zerodha:
                    zerodhaCall();
                    break;
                case R.id.asta:
                    astaCall();
                    break;
            }

        });

    }

    private void init() {


        rg = findViewById(R.id.radioGroup);
        //loading adview
        View view = getWindow().getDecorView().getRootView();
        AdViewHelper.loadBanner(view);
        getSupportActionBar().setTitle("Commodity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate: CommodityActivity");
        // initializing add
        videoAd = MobileAds.getRewardedVideoAdInstance(this);
        videoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
    }


    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv);

        commodityAdapter = new CommodityAdapter(item -> {
            commodity = item;
            //showing add
            if (videoAd.isLoaded()) {
                videoAd.show();
            } else {
                // code for calculating and showing a popup
                AlertHelper alertHelper = new AlertHelper(CommodityActivity.this);
                alertHelper.loadCommodityPopUp(item);
            }


        }, CommodityActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commodityAdapter);


    }

    public void showPopup() {
        AlertHelper alertHelper = new AlertHelper(CommodityActivity.this);

        alertHelper.loadCommodityPopUp(commodity);
    }

    public void zerodhaCall() {
        viewModel = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        viewModel.fetchCommodity().observe(this, Commoditys -> {

            list = Commoditys;
            Log.d(TAG, "onChanged: inside fetch commodity data");
            commodityAdapter.updateData(Commoditys);

            bar.setVisibility(View.GONE);
        });
    }

    public void astaCall() {
        astaViewModel = ViewModelProviders.of(this).get(AstaViewModel.class);
        astaViewModel.fetchCommodity().observe(this, Commoditys -> {

            list = Commoditys;
            Log.d(TAG, "onChanged: inside fetch commodity data");
            commodityAdapter.updateData(Commoditys);

            bar.setVisibility(View.GONE);
        });
    }

    //video add
    private void loadRewardedVideoAd() {
        //original
        videoAd.loadAd(getResources().getString(R.string.commodity_reward),
                new AdRequest.Builder().build());
    }

    // searchview

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
                List<Commodity> em = new ArrayList<>();
                for (Commodity e : list) {
                    if (e.getScrip().toLowerCase().startsWith(s)) {
                        em.add(e);
                    }
                }
                commodityAdapter.updateData(em);


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
