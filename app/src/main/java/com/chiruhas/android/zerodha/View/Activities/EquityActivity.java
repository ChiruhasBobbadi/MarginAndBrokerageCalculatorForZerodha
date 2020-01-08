package com.chiruhas.android.zerodha.View.Activities;

import android.app.Dialog;
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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.Repo.asta.AstaViewModel;
import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.ZerodhaViewModel;

import java.util.ArrayList;
import java.util.List;

public class EquityActivity extends AppCompatActivity {
    private static final String TAG = "EquityActivity";
    // retrofit viewmodel
    ZerodhaViewModel view;
    AstaViewModel astaViewModel;
    RecyclerView rv;
    Dialog myDialog;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar bar;
    List<GodModel> equity = new ArrayList<>();
    //room viewmodel

    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);

        init();

        // radio group check chage listener

        rg.setOnCheckedChangeListener((group, checkedId) -> {

            recyclerViewAdapter.updateData(new ArrayList<>());
            switch (checkedId) {
                case R.id.zerodha:
                    bar.setVisibility(View.VISIBLE);
                    zerodhaCall();
                    break;
                case R.id.asta:
                    bar.setVisibility(View.VISIBLE);
                    astaCall();
                    break;

            }

        });


    }

    private void init() {
        rv = findViewById(R.id.rv);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        rg = findViewById(R.id.radioGroup);


        rv.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Equity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDialog = new Dialog(this);


        View rootView = getWindow().getDecorView().getRootView();
        //adview
        AdViewHelper.loadBanner(rootView);

        recyclerViewAdapter = new RecyclerViewAdapter(this::loadAlert);

        rv.setAdapter(recyclerViewAdapter);
        zerodhaCall();


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

    /**
     * @param item object
     */
    public void loadAlert(GodModel item) {
        /**
         * Inflating pop up
         */
        AlertHelper alertHelper = new AlertHelper(EquityActivity.this);

        alertHelper.loadEquityPopup(item);

    }


    public void zerodhaCall() {
        view = ViewModelProviders.of(this).get(ZerodhaViewModel.class);
        view.fetchEquity().observe(this, GodModels -> {
            equity = GodModels;

            Log.d(TAG, "zerodhaCall: " + equity.size());

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

}
