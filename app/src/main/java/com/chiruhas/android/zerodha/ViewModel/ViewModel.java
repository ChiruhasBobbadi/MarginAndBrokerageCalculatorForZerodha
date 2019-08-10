package com.chiruhas.android.zerodha.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import com.chiruhas.android.zerodha.ViewModel.Repo.Repository;
import com.chiruhas.android.zerodha.ViewModel.Repo.Repository2;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    Repository r;
    Repository2 r2;
    LiveData<List<GodModel>> list ;
    LiveData<List<Commodity>> commodity;
    LiveData<List<GodModel>> currency;
    LiveData<List<GodModel>> futures;

    public ViewModel(@NonNull Application application) {
        super(application);
        r = new Repository();
        r2 = new Repository2();
        list = r.getEquity();
        commodity = r2.getCommodity();
        currency=r.getCurrency();
        //futures=r.getFutures();
    }
    public LiveData<List<GodModel>> fetchEquity()
    {
        return list;
    }
    public LiveData<List<Commodity>> fetchCommodity(){
        return commodity;
    }
    public LiveData<List<GodModel>> fetchCurrency(){
        return currency;
    }
    public LiveData<List<GodModel>> fetchFutures(){
        return futures;
    }



}
