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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.CommodityAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;


import java.util.ArrayList;
import java.util.List;

public class CommodityActivity extends AppCompatActivity {


    private static final String TAG = "Commodity Activity";
    RecyclerView recyclerView;
    ViewModel viewModel;
    CommodityAdapter commodityAdapter;
    ProgressBar bar;
    //CommodityViewModel commodityViewModel;
    List<Commodity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        //loading adview
        View view = getWindow().getDecorView().getRootView();
        AdViewHelper.loadBanner(view);
        getSupportActionBar().setTitle("Commodity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate: CommodityActivity");
        setAdapter();
        fetchData();
        //fetchCache();

    }

//    private void fetchCache() {
//        commodityViewModel = ViewModelProviders.of(this).get(CommodityViewModel.class);
//        commodityViewModel.getAll().observe(this, new Observer<List<Commodity>>() {
//            @Override
//            public void onChanged(List<Commodity> godCommodities) {
//
//
//                commodityAdapter.setCache(godCommodities);
//            }
//        });
//    }

    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv);
        commodityAdapter = new CommodityAdapter(new CommodityAdapter.ItemListener() {
            @Override
            public void onItemClick(Commodity item) {
                // code for calculating and showing a popup
                AlertHelper alertHelper = new AlertHelper(CommodityActivity.this);

                alertHelper.loadCommodityPopUp(item);

            }

//            @Override
//            public void onBookmarkClick(Commodity model) {
//                // insert into database
//
//                commodityViewModel.insert(model);
//
//            }

//            @Override
//            public void onBookmarkUnClick(Commodity model) {
//                // delete from database
//                commodityViewModel.delete(model);
//            }
        }, CommodityActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commodityAdapter);


    }

    public void fetchData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.fetchCommodity().observe(this, new Observer<List<Commodity>>() {
            @Override
            public void onChanged(List<Commodity> Commoditys) {


                list = Commoditys;
                Log.d(TAG, "onChanged: inside fetch commodity data");
                commodityAdapter.updateData(Commoditys);

                bar.setVisibility(View.GONE);
            }
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


}
