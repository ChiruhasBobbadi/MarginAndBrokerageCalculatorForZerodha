package com.chiruhas.android.zerodha.View.Activities;

import android.app.Dialog;
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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.SortHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
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

public class EquityActivity extends AppCompatActivity {

    private static final String TAG = "EquityActivity";
    // retrofit viewmodel
    ZerodhaViewModel view;
    AstaViewModel astaViewModel;
    AliceViewModel alice;
    RecyclerView rv;
    Dialog myDialog;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar bar;
    List<GodModel> equity = new ArrayList<>();
    NiceSpinner spinner;
    WisdomViewModel wisdom;
    private boolean mish2l, nrmlh2l;
    private ChipGroup chipGroup;
    private FrameLayout adContainerView;
    private AdView adView;
    //room viewmodel
    private double _equity;
    private SharedPreferences data;
    private SamcoViewModel samco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.equityList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        init();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerViewAdapter.updateData(new ArrayList<>());
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
                    case 4:
                        wisdomCap();
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        zerodhaCall();

    }

    private void wisdomCap() {
        wisdom = ViewModelProviders.of(this).get(WisdomViewModel.class);
        wisdom.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            recyclerViewAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void samcoCall() {
        samco = ViewModelProviders.of(this).get(SamcoViewModel.class);
        samco.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            recyclerViewAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    private void init() {
        data = getSharedPreferences("dataStore",
                MODE_PRIVATE);
        String t = data.getString("equity", "");
        if (!t.equals(""))
            _equity = Double.parseDouble(t);

        rv = findViewById(R.id.rv);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);

        chipGroup = findViewById(R.id.chipGroup);
        mish2l = nrmlh2l = false;
        spinner = findViewById(R.id.material_spinner);

        initAds();
        loadBanner();


        rv.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Equity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myDialog = new Dialog(this);


        recyclerViewAdapter = new RecyclerViewAdapter(this::loadAlert);

        rv.setAdapter(recyclerViewAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: prepared");
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
                List<GodModel> em = new ArrayList<>();
                for (GodModel e : equity) {
                    if (e.getTradingsymbol().toLowerCase().startsWith(s)) {
                        em.add(e);
                    }
                }
                recyclerViewAdapter.updateData(em);


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
            YoYo.with(Techniques.SlideOutUp)
                    .duration(600)
                    .repeat(0)
                    .playOn(chipGroup);

        }
        return super.onOptionsItemSelected(item);
    }

    public void chipClick(View v) {

        Log.d(TAG, "chipClick: " + v.getId());
        switch (v.getId()) {
            case R.id.mish2l:
                mish2l = !mish2l;
                nrmlh2l = false;
                sortList();
                break;
            case R.id.nrml_h2l:
                nrmlh2l = !nrmlh2l;
                mish2l = false;
                sortList();
                break;
            case R.id.clear:
                chipGroup.clearCheck();
                List<GodModel> l = SortHelper.equityDefaultSort(recyclerViewAdapter.getData());
                recyclerViewAdapter.updateData(l);
                chipGroup.setVisibility(View.GONE);
                break;
        }

    }

    /**
     * @param item object
     */
    public void loadAlert(GodModel item) {
        /**
         * Inflating pop up
         */
        AlertHelper alertHelper = new AlertHelper(EquityActivity.this);

        alertHelper.loadEquityPopup(item, _equity);

    }


    public void zerodhaCall() {
        view = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        view.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            recyclerViewAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    public void astaCall() {
        astaViewModel = ViewModelProviders.of(this).get(AstaViewModel.class);
        astaViewModel.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            recyclerViewAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
    }

    public void aliceCall() {
        alice = ViewModelProviders.of(this).get(AliceViewModel.class);
        alice.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            recyclerViewAdapter.updateData(GodModels);

            bar.setVisibility(View.GONE);
        });
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
                new AdRequest.Builder()
                        .build();

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

        recyclerViewAdapter.updateData(SortHelper.equitySort(recyclerViewAdapter.getData(), mish2l, nrmlh2l));

    }


}
