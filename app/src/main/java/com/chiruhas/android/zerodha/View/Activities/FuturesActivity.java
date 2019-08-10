package com.chiruhas.android.zerodha.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.chiruhas.android.zerodha.CustomAdapters.Equity.RecyclerViewAdapter;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.R;
import com.chiruhas.android.zerodha.ViewModel.ViewModel;
import com.chiruhas.android.zerodha.room.equity.EquityViewModel;

import java.util.ArrayList;
import java.util.List;

public class FuturesActivity extends AppCompatActivity {

    ViewModel view;
    RecyclerView rv;
    Dialog myDialog;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar bar;
    private static final String TAG = "FuturesActivity";
    List<GodModel> equity = new ArrayList<>();

    //room viewmodel
    EquityViewModel equityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futures);
    }
}
