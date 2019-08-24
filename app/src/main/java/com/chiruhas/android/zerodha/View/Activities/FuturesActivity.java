package com.chiruhas.android.zerodha.View.Activities;

import android.app.Dialog;
import android.os.Bundle;
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

import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.chiruhas.android.zerodha.room.equity.EquityViewModel;

import java.util.ArrayList;
import java.util.List;

public class FuturesActivity extends AppCompatActivity {

    ViewModel view;
    RecyclerView rv;
    Dialog myDialog;
    FutureAdapter FutureAdapter;
    ProgressBar bar;
    private static final String TAG = "FuturesActivity";
    List<Futures> list = new ArrayList<>();

    //room viewmodel
    EquityViewModel futureViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futures);


        //loading adview
        View view = getWindow().getDecorView().getRootView();
        AdViewHelper.loadBanner(view);
        getSupportActionBar().setTitle("Future Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAdapter();
        fetchData();
        //fetchCache();
        
    }
//    private void fetchCache() {
//        futureViewModel = ViewModelProviders.of(this).get(FutureViewModel.class);
//        futureViewModel.getAll().observe(this, new Observer<List<Future>>() {
//            @Override
//            public void onChanged(List<future> godCommodities) {
//
//                List<Future> lst = new ArrayList<>();
//                for(Future g : godCommodities){
//                    // GodFuture to God Model
//                    lst.add(g);
//                }
//                FutureAdapter.setCache(lst);
//            }
//        });
//    }

    public void setAdapter() {
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        rv = findViewById(R.id.rv);
        // change later
        FutureAdapter =new FutureAdapter(new FutureAdapter.ItemListener() {
            @Override
            public void onItemClick(Futures item) {
                // code for calculating and showing a popup
                AlertHelper alertHelper = new AlertHelper(FuturesActivity.this);

                alertHelper.loadFuturePopUp(item);

            }

            @Override
            public void onBookmarkClick(Futures model) {
                // insert into database

                FutureViewModel.insert(model);

            }

            @Override
            public void onBookmarkUnClick(Futures model) {
                // delete from database
                FutureViewModel.delete(model);
            }
        },FutureActivity.this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(FutureAdapter);


    }

    public void fetchData() {
        view = ViewModelProviders.of(this).get(ViewModel.class);
        view.fetchFutures().observe(this, new Observer<List<Futures>>() {
            @Override
            public void onChanged(List<Futures> Futures) {


                list = Futures;

                FutureAdapter.updateData(Futures);

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
                List<Futures> em = new ArrayList<>();
                for (Futures e : list) {
                    if (e.getScrip().toLowerCase().startsWith(s)) {
                        em.add(e);
                    }
                }
                FutureAdapter.updateData(em);


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
