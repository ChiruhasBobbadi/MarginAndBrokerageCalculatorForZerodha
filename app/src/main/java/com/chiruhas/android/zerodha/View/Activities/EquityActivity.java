package com.chiruhas.android.zerodha.View.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.chiruhas.android.zerodha.room.equity.EquityViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EquityActivity extends AppCompatActivity {
    // retrofit viewmodel
    ViewModel view;
    RecyclerView rv;
    Dialog myDialog;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar bar;
    private static final String TAG = "EquityActivity";
    List<GodModel> equity = new ArrayList<>();

    //room viewmodel
    EquityViewModel equityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);
        rv = findViewById(R.id.rv);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onCreate: sucessful");
        rv.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Equity Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDialog = new Dialog(this);
        equityViewModel = ViewModelProviders.of(this).get(EquityViewModel.class);

        View rootView = getWindow().getDecorView().getRootView();

        //adview

        AdViewHelper.loadBanner(rootView);

        recyclerViewAdapter = new RecyclerViewAdapter(new RecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(final GodModel item) {

                loadAlert(item);
            }

            @Override
            public void onBookmarkClick(GodModel GodModel) {
                // converting object of GodModel into object of GodEquity

                GodEquity godEquity = new GodEquity(GodModel.getMargin(), GodModel.getCo_lower(), GodModel.getMis_multiplier(), GodModel.getTradingsymbol(), GodModel.getCo_upper(), GodModel.getNrml_margin(), GodModel.getMis_margin());

                equityViewModel.insert(godEquity);
            }

            @Override
            public void onBookmarkUnClick(GodModel GodModel) {
                // converting object of GodModel into object of GodEquity
                GodEquity godEquity = new GodEquity(GodModel.getMargin(), GodModel.getCo_lower(), GodModel.getMis_multiplier(), GodModel.getTradingsymbol(), GodModel.getCo_upper(), GodModel.getNrml_margin(), GodModel.getMis_margin());

                equityViewModel.delete(godEquity);
            }
        });
       
        //room data of equity margins
        equityViewModel.getAll().observe(this, new Observer<List<GodEquity>>() {
            @Override
            public void onChanged(List<GodEquity> godEquities) {

                GodModel godModel = new GodModel();
                List<GodModel> lst = new ArrayList<>();
                for(GodEquity godCommodity : godEquities){
                    // GodEquity to God Model
                    lst.add(new GodModel(godCommodity.getMargin(), godCommodity.getCo_lower(), godCommodity.getMis_multiplier(),
                            godCommodity.getTradingsymbol(), godCommodity.getCo_upper(), godCommodity.getNrml_margin(), godCommodity.getMis_margin()));
                }

                recyclerViewAdapter.setCache(lst);
            }
        });
        
        rv.setAdapter(recyclerViewAdapter);
        // retrofit call
        view = ViewModelProviders.of(this).get(ViewModel.class);
        view.fetchEquity().observe(this, new Observer<List<GodModel>>() {
            @Override
            public void onChanged(@Nullable List<GodModel> GodModels) {
                equity = GodModels;

                recyclerViewAdapter.updateData(GodModels);

                bar.setVisibility(View.GONE);
            }
        });

        //TODO
//        if (NetworkHelper.haveNetwork(this)) {
//            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
//        }

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


}
