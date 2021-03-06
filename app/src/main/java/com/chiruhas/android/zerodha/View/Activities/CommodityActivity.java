package com.chiruhas.android.zerodha.View.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
import com.chiruhas.android.zerodha.ViewModel.alice.AliceViewModel;
import com.chiruhas.android.zerodha.ViewModel.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.zerodha.ZerodhaViewModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.chip.ChipGroup;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class CommodityActivity extends AppCompatActivity {


    private static final String TAG = "Commodity Activity";
    AliceViewModel alice;
    NiceSpinner spinner;
    private CommodityAdapter commodityAdapter;
    private ProgressBar bar;
    private List<Commodity> list = new ArrayList<>();
    private double _commodity;
    private Commodity commodity;
    private InterstitialAd mInterstitialAd;
    private AdView adView;
    private ChipGroup chipGroup;
    private boolean mish2l, nrmlh2l, priceh2l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);


        init();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.commodityList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setAdapter();
        zerodhaCall();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                commodityAdapter.updateData(new ArrayList<>());
                bar.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        zerodhaCall();
                        break;
                    case 1:
                        aliceCall();
                        break;
                    case 2:
                        astaCall();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                showPopup();
            }
        });

    }

    private void init() {

        SharedPreferences data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        String t = data.getString("commodity", "");
        if (!t.equals(""))
            _commodity = Double.parseDouble(t);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.commodity_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mish2l = nrmlh2l = priceh2l = false;
        chipGroup = findViewById(R.id.chipGroup);
        spinner = findViewById(R.id.material_spinner);
        //loading adview
        initAds();
        loadBanner();

        getSupportActionBar().setTitle("Commodity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing add


    }


    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.rv);

        commodityAdapter = new CommodityAdapter(item -> {
            commodity = item;
            //showing add
            if (mInterstitialAd.isLoaded())
                mInterstitialAd.show();
            else {
                // code for calculating and showing a popup
                showPopup();
            }


        }, CommodityActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commodityAdapter);


    }

    public void showPopup() {
        AlertHelper alertHelper = new AlertHelper(CommodityActivity.this);

        alertHelper.loadCommodityPopUp(commodity, _commodity);
    }

    public void zerodhaCall() {
        ZerodhaViewModel viewModel = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        viewModel.fetchCommodity().observe(this, Commoditys -> {

            list = Commoditys;
            Log.d(TAG, "onChanged: inside fetch commodity data");
            commodityAdapter.updateData(Commoditys);

            bar.setVisibility(View.GONE);
        });
    }

    public void astaCall() {
        AstaViewModel astaViewModel = ViewModelProviders.of(this).get(AstaViewModel.class);
        astaViewModel.fetchCommodity().observe(this, Commoditys -> {

            list = Commoditys;

            commodityAdapter.updateData(Commoditys);

            bar.setVisibility(View.GONE);
        });
    }

    public void aliceCall() {
        alice = ViewModelProviders.of(this).get(AliceViewModel.class);
        alice.fetchCommodity().observe(this, GodModels -> {
            list = GodModels;

            commodityAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
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

            YoYo.with(Techniques.FadeIn)
                    .duration(600)
                    .repeat(0)
                    .playOn(chipGroup);
        } else {
            chipGroup.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);

    }


    private void initAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
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


        switch (v.getId()) {
            case R.id.mish2l:
                mish2l = !mish2l;
                nrmlh2l = priceh2l = false;
                sortList();
                break;
            case R.id.nrml_h2l:
                nrmlh2l = !nrmlh2l;
                mish2l = priceh2l = false;
                sortList();
                break;
            case R.id.price:
                priceh2l = !priceh2l;
                mish2l = nrmlh2l = false;
                sortList();
                break;
            case R.id.clear:
                chipGroup.clearCheck();
                commodityAdapter.updateData(SortHelper.commodityDefaultSort(commodityAdapter.getData()));

                chipGroup.setVisibility(View.GONE);
                break;
        }

    }


}
