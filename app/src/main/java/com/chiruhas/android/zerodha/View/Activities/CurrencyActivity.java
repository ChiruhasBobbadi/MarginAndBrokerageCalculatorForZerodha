package com.chiruhas.android.zerodha.View.Activities;


import android.os.Bundle;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CommodityAdapter;
import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.HelperClasses.ObjectConverter;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.chiruhas.android.zerodha.R;

import java.util.ArrayList;
import java.util.List;

public class CurrencyActivity extends AppCompatActivity  {
   private ViewModel view;
    private RecyclerView rv;
    CommodityAdapter commodityAdapter;
    ProgressBar bar;
    private static final String TAG = "CurrencyActivity";
    List<GodModel> currency = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        Log.d(TAG, "onCreate: sucessful");

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Currencyb Margins");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        //equityViewModel = ViewModelProviders.of(this).get(EquityViewModel.class);

        View rootView = getWindow().getDecorView().getRootView();

        AdViewHelper.loadBanner(rootView);
//        setAdapter();
//        fetchData();

        }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.app_bar_search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                s = s.toLowerCase();
//                List<GodModel> em = new ArrayList<>();
//                for (GodModel e : currency) {
//                    if (e.getTradingsymbol().toLowerCase().startsWith(s)) {
//                        em.add(e);
//                    }
//                }
//                commodityAdapter.updateData(em);
//
//
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    public void setAdapter() {
//        bar = findViewById(R.id.progress);
//        bar.setVisibility(View.VISIBLE);
//        rv = findViewById(R.id.rv);
//        commodityAdapter =new CommodityAdapter(new CommodityAdapter.ItemListener() {
//            @Override
//            public void onItemClick(GodModel item) {
//                // code for calculating and showing a popup
//                AlertHelper alertHelper = new AlertHelper(CurrencyActivity.this);
//
//                alertHelper.loadEquityPopup(item);
//
//            }
//
//            @Override
//            public void onBookmarkClick(GodModel model) {
//                // insert into database
//
//
//
//            }
//
//            @Override
//            public void onBookmarkUnClick(GodModel model) {
//                // delete from database
//            }
//        },CurrencyActivity.this);
//
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(commodityAdapter);
//
//
//    }
//
//    public void fetchData() {
//        view = ViewModelProviders.of(this).get(ViewModel.class);
//        view.fetchCommodity().observe(this, new Observer<List<GodModel>>() {
//            @Override
//            public void onChanged(List<GodModel> godModels) {
//
////                for (int i = 0; i < godModels.size(); i++) {
////                    godModels.get(i).setLotsize(lotsize[i]);
////                }
//                currency = godModels;
//
//                commodityAdapter.updateData(godModels);
//
//                bar.setVisibility(View.GONE);
//            }
//        });
    }



