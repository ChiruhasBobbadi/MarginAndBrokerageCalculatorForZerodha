package com.chiruhas.android.zerodha.View.Activities;

import android.app.AlertDialog;
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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.FutureAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.SortHelper;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.alice.AliceViewModel;
import com.chiruhas.android.zerodha.ViewModel.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.samco.SamcoViewModel;
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

public class FuturesActivity extends AppCompatActivity {

    private static final String TAG = "FuturesActivity";
    AliceViewModel alice;
    NiceSpinner spinner;
    SamcoViewModel samcoViewModel;
    private FutureAdapter adapter;
    private ProgressBar bar;
    private double _futures;
    private List<Futures> list = new ArrayList<>();
    private InterstitialAd mInterstitialAd;
    private Futures futures;
    private AdView adView;
    private boolean mish2l, nrmlh2l, priceh2l;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futures);
        init();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.FuturesList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                adapter.updateData(new ArrayList<>());
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
                    case 3:
                        samcoCall();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void aliceCall() {
        alice = ViewModelProviders.of(this).get(AliceViewModel.class);
        alice.fetchFutures().observe(this, GodModels -> {
            list = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void samcoCall() {
        samcoViewModel = ViewModelProviders.of(this).get(SamcoViewModel.class);
        samcoViewModel.fetchFutures().observe(this, GodModels -> {
            list = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void astaCall() {
        AstaViewModel astaViewModel = ViewModelProviders.of(this).get(AstaViewModel.class);
        astaViewModel.fetchFutures().observe(this, Futures -> {
            list = Futures;
            adapter.updateData(Futures);
            bar.setVisibility(View.GONE);
        });
    }

    public void zerodhaCall() {
        ZerodhaViewModel viewModel = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        viewModel.fetchFutures().observe(this, Futures -> {


            list = Futures;
            adapter.updateData(Futures);

            bar.setVisibility(View.GONE);
        });
    }

    private void init() {
        SharedPreferences data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        String t = data.getString("futures", "");
        if (!t.equals(""))
            _futures = Double.parseDouble(t);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.future_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mish2l = nrmlh2l = priceh2l = false;
        spinner = findViewById(R.id.material_spinner);
        chipGroup = findViewById(R.id.chipGroup);
        initAds();
        loadBanner();
        getSupportActionBar().setTitle("Future Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //TODO
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean first = sharedPreferences.getBoolean("first", true);
        if (first) {
            showWarning();
        }


        setAdapter();
        zerodhaCall();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: future ad loaded");
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                showPopup();
            }
        });
    }

    private void showWarning() {
        new AlertDialog.Builder(this).setTitle("Attention Required !").setMessage("Thank you for downloading the app, currently this app is able to fetch future's data but calculating margins is not accurate(only for future's) as zerodha has some internal formula .\n\nI am searching for alternate ways,if you still wish to use it you can use it.\n\nBut please don't post threatening reviews after using it, I will publish an update as soon as i figure it.\n\nIt's my responsibility to notify you, and i did.\n\nHappy Trading!. ")
                .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss()).create().show();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first", false);
        editor.apply();
    }

    /**
     * Reward video helper method
     */


    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        RecyclerView rv = findViewById(R.id.rv);
        // change later
        adapter = new FutureAdapter(item -> {
            futures = item;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                // code for calculating and showing a popup
                showPopup();
            }


        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }


    public void showPopup() {
        AlertHelper alertHelper = new AlertHelper(FuturesActivity.this);

        alertHelper.loadFuturePopUp(futures, _futures);
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
                List<Futures> em = new ArrayList<>();

                for (Futures e : list) {
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

        adapter.updateData(SortHelper.futureSort(adapter.getData(), mish2l, nrmlh2l, priceh2l));

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
                adapter.updateData(SortHelper.futuresDefaultSort(adapter.getData()));
                chipGroup.setVisibility(View.GONE);
                break;
        }

    }
}
