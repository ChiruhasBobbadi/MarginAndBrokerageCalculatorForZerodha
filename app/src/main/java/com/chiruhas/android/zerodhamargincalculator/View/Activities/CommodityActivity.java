package com.chiruhas.android.zerodhamargincalculator.View.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.chiruhas.android.zerodhamargincalculator.CustomAdapters.Equity.CommodityAdapter;
import com.chiruhas.android.zerodhamargincalculator.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodhamargincalculator.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodhamargincalculator.HelperClasses.ObjectConverter;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodCommodity;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodhamargincalculator.R;
import com.chiruhas.android.zerodhamargincalculator.ViewModel.ViewModel;
import com.chiruhas.android.zerodhamargincalculator.room.Commodity.CommodityViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommodityActivity extends AppCompatActivity {

    String lotsize[] = {"1 MT", "5 MT", "1 MT", "100 KGS", "110 MT", "1 MT", "250 KGS", "25 BALES", "10 MT", "100 BBL", "10 BBL", "1 KGS",
            "8 GRMS", "100 GRMS", "1 GRMS", "5 MT", "1 MT", "360 KGS", "1250 MMBTU", "250 KGS", "100 KGS", "1 MT", "10 MT"
            ,"30 KGS", "5 KGS", "1 KGS", "5 MT", "1 MT"};

    RecyclerView recyclerView;
    ViewModel viewModel;
    CommodityAdapter commodityAdapter;
    ProgressBar bar;
    CommodityViewModel commodityViewModel;
    List<GodModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        //loading adview
        View view = getWindow().getDecorView().getRootView();
        AdViewHelper.loadBanner(view);



        getSupportActionBar().setTitle("Commodity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAdapter();
        fetchData();
        fetchCache();

    }

    private void fetchCache() {
       commodityViewModel = ViewModelProviders.of(this).get(CommodityViewModel.class);
       commodityViewModel.getAll().observe(this, new Observer<List<GodCommodity>>() {
           @Override
           public void onChanged(List<GodCommodity> godCommodities) {
               GodModel godModel = new GodModel();
                List<GodModel> lst = new ArrayList<>();
               for(GodCommodity godCommodity : godCommodities){
                   // GodCommodity to God Model
                   lst.add(new GodModel(godCommodity.getMargin(), godCommodity.getCo_lower(), godCommodity.getMis_multiplier(),
                           godCommodity.getTradingsymbol(), godCommodity.getCo_upper(), godCommodity.getNrml_margin(), godCommodity.getMis_margin()));
               }
               commodityAdapter.setCache(lst);
           }
       });
    }

    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv);
       commodityAdapter =new CommodityAdapter(new CommodityAdapter.ItemListener() {
            @Override
            public void onItemClick(GodModel item) {
                // code for calculating and showing a popup
                AlertHelper alertHelper = new AlertHelper(CommodityActivity.this);

                alertHelper.loadEquityPopup(item);

            }

           @Override
           public void onBookmarkClick(GodModel model) {
               // insert into database

               commodityViewModel.insert(ObjectConverter.GodModeltoGodCommodity(model));

           }

           @Override
           public void onBookmarkUnClick(GodModel model) {
                // delete from database
               commodityViewModel.delete(ObjectConverter.GodModeltoGodCommodity(model));
           }
       },CommodityActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commodityAdapter);


    }

    public void fetchData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.fetchCommodity().observe(this, new Observer<List<GodModel>>() {
            @Override
            public void onChanged(List<GodModel> godModels) {

                for (int i = 0; i < godModels.size(); i++) {
                    godModels.get(i).setLotsize(lotsize[i]);
                }
                list = godModels;

                commodityAdapter.updateData(godModels);

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
                List<GodModel> em = new ArrayList<>();
                for (GodModel e : list) {
                    if (e.getTradingsymbol().toLowerCase().startsWith(s)) {
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
