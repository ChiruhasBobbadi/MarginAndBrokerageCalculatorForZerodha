package com.chiruhas.android.zerodha.View.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.chiruhas.android.zerodha.CustomAdapters.Equity.FutureAdapter;
import com.chiruhas.android.zerodha.HelperClasses.AdViewHelper;
import com.chiruhas.android.zerodha.HelperClasses.AlertHelper;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.chiruhas.android.zerodha.room.equity.EquityViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;

import java.util.ArrayList;
import java.util.List;

public class FuturesActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private  ViewModel viewModel;
    private RecyclerView rv;
    private FutureAdapter adapter;
    private  ProgressBar bar;
    private static final String TAG = "FuturesActivity";
    private List<Futures> list = new ArrayList<>();
    private RewardedVideoAd videoAd;

    private Futures futures;

    //room viewmodel
   // EquityViewModel futureViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futures);


        //loading adview
        View view = getWindow().getDecorView().getRootView();
        AdViewHelper.loadBanner(view);
        getSupportActionBar().setTitle("Future Margins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        videoAd = MobileAds.getRewardedVideoAdInstance(this);
        videoAd.setRewardedVideoAdListener(this);
        //TODO
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean first = sharedPreferences.getBoolean("first",true);
//        if(first){
            showWarning();
       // }

        loadRewardedVideoAd();
        setAdapter();
        fetchData();
        //fetchCache();
        
    }

    private void showWarning()
    {
        new AlertDialog.Builder(this).setTitle("Attention Required !").setMessage("Thank you for downloading the app, currently this app is able to fetch future's data but calculating margins is not accurate(only for future's) as zerodha has some internal formula .\n\nI am searching for alternate ways,if you still wish to use it you can use it.\n\nBut please don't post threatening reviews after using it, I will publish an update as soon as i figure it.\n\nIt's my responsibility to notify you, and i did.\n\nHappy Trading!. ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first",false);
        editor.apply();
    }
    /**
     * Reward video helper method
     */
    private void loadRewardedVideoAd() {
        // dummy
        videoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());

        //original
//        videoAd.loadAd("ca-app-pub-4351116683020455/7948227926",
//                new AdRequest.Builder().build());
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
        adapter =new FutureAdapter(new FutureAdapter.ItemListener() {
            @Override
            public void onItemClick(Futures item) {
                futures=item;
                if(videoAd.isLoaded()){
                    videoAd.show();

                }else {
                    // code for calculating and showing a popup
                    AlertHelper alertHelper = new AlertHelper(FuturesActivity.this);

                    alertHelper.loadFuturePopUp(item);
                }

            }


        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }

    public void fetchData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.fetchFutures().observe(this, new Observer<List<Futures>>() {
            @Override
            public void onChanged(List<Futures> Futures) {


                list = Futures;
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onChanged: "+list.get(i).getScrip());
                }
                adapter.updateData(Futures);

                bar.setVisibility(View.GONE);
            }
        });
    }
    public void showPopup(){
        AlertHelper alertHelper = new AlertHelper(FuturesActivity.this);

        alertHelper.loadFuturePopUp(futures);
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
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        showPopup();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
