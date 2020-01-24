package com.chiruhas.android.zerodha.View.Activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CommodityAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.SortHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.ZerodhaViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.chip.ChipGroup;

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
    private FrameLayout adContainerView;
    private AdView adView;
    private ChipGroup chipGroup;

    private boolean mish2l, nrmlh2l, priceh2l;

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

        mish2l = nrmlh2l = priceh2l = false;
        chipGroup = findViewById(R.id.chipGroup);
        rg = findViewById(R.id.radioGroup);
        //loading adview
        initAds();
        loadBanner();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort && chipGroup.getVisibility() == View.GONE) {
            chipGroup.setVisibility(View.VISIBLE);
        } else {
            chipGroup.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);

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

    private void initAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.margin_banner));
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

    public void sortList() {

        commodityAdapter.updateData(SortHelper.commoditySort(commodityAdapter.getData(), mish2l, nrmlh2l, priceh2l));

    }

    public void chipClick(View v) {

        Log.d(TAG, "chipClick: " + v.getId());
        switch (v.getId()) {
            case R.id.mish2l:
                mish2l = !mish2l;
                break;
            case R.id.nrml_h2l:
                nrmlh2l = !nrmlh2l;
                break;
            case R.id.price:
                priceh2l = !priceh2l;
                break;
            case R.id.clear:
                chipGroup.clearCheck();
                commodityAdapter.updateData(SortHelper.commodityDefaultSort(commodityAdapter.getData()));
                chipGroup.setVisibility(View.GONE);
                break;
        }
        sortList();
    }


}
