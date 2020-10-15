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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CurrencyAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.SortHelper;
import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.alice.AliceViewModel;
import com.chiruhas.android.zerodha.ViewModel.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.samco.SamcoViewModel;
import com.chiruhas.android.zerodha.ViewModel.wisdom.WisdomViewModel;
import com.chiruhas.android.zerodha.ViewModel.zerodha.ZerodhaViewModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.chip.ChipGroup;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class CurrencyActivity extends AppCompatActivity {
    private static final String TAG = "CurrencyActivity";
    NiceSpinner spinner;
    SamcoViewModel samcoViewModel;
    WisdomViewModel wisdomViewModel;
    AstaViewModel astaViewModel;
    AliceViewModel alice;
    private ZerodhaViewModel view;
    private CurrencyAdapter adapter;
    private ProgressBar bar;
    private List<Currency> currency = new ArrayList<>();
    private double _currency;
    private Currency currencyItem;
    private boolean mish2l, nrmlh2l, priceh2l;
    private ChipGroup chipGroup;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        init();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.currencyList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bar.setVisibility(View.VISIBLE);
                adapter.updateData(new ArrayList<>());
                switch (position) {
                    case 0:

                        zerodhaCall();
                        break;
                    case 2:
                        astaCall();
                        break;
                    case 1:
                        aliceCall();
                        break;
                    case 3:
                        samcoCall();
                        break;
                    case 4:
                        wisdomCall();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void init() {

        SharedPreferences data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        String t = data.getString("equity", "");
        if (!t.equals(""))
            _currency = Double.parseDouble(t);
        mish2l = nrmlh2l = priceh2l = false;
        chipGroup = findViewById(R.id.chipGroup);
        spinner = findViewById(R.id.material_spinner);
        initAds();
        loadBanner();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Currency Margins");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        setAdapter();
        fetchData();
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

    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        RecyclerView rv = findViewById(R.id.rv);
        adapter = new CurrencyAdapter(item -> {

            currencyItem = item;


            showPopup();


        });


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }

    public void zerodhaCall() {
        view = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        view.fetchCurrency().observe(this, GodModels -> {
            currency = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    public void astaCall() {
        astaViewModel = ViewModelProviders.of(this).get(AstaViewModel.class);
        astaViewModel.fetchCurrency().observe(this, GodModels -> {
            currency = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void aliceCall() {
        alice = ViewModelProviders.of(this).get(AliceViewModel.class);
        alice.fetchCurrency().observe(this, GodModels -> {
            currency = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void samcoCall() {
        samcoViewModel = ViewModelProviders.of(this).get(SamcoViewModel.class);
        samcoViewModel.fetchCurrency().observe(this, GodModels -> {
            currency = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void wisdomCall() {
        wisdomViewModel = ViewModelProviders.of(this).get(WisdomViewModel.class);
        wisdomViewModel.fetchCurrency().observe(this, GodModels -> {
            currency = GodModels;

            adapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    public void showPopup() {
        AlertHelper alertHelper = new AlertHelper(CurrencyActivity.this);

        alertHelper.loadCurrencyPopUp(currencyItem, _currency);
    }

    public void fetchData() {
        view = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        view.fetchCurrency().observe(this, godModels -> {


            currency = godModels;

            adapter.updateData(godModels);

            bar.setVisibility(View.GONE);
        });
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

        adapter.updateData(SortHelper.currencySort(adapter.getData(), mish2l, nrmlh2l, priceh2l));

    }

    public void chipClick(View v) {

        Log.d(TAG, "chipClick: " + v.getId());
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
                adapter.updateData(SortHelper.currencyDefaultSort(adapter.getData()));
                chipGroup.setVisibility(View.GONE);
                break;
        }

    }
}



