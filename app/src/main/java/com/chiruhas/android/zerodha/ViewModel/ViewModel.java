package com.chiruhas.android.zerodha.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import com.chiruhas.android.zerodha.ViewModel.Repo.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    Repository r;
    LiveData<List<GodModel>> list ;
    LiveData<List<GodModel>> commodity;
    LiveData<List<GodModel>> currency;
    LiveData<List<GodModel>> futures;

    public ViewModel(@NonNull Application application) {
        super(application);
        r = new Repository();
        list = r.getEquity();
        commodity = r.getCommodity();
        currency=r.getCurrency();
        futures=r.getFutures();
    }
    public LiveData<List<GodModel>> fetchEquity()
    {
        return list;
    }
    public LiveData<List<GodModel>> fetchCommodity(){
        return commodity;
    }
    public LiveData<List<GodModel>> fetchCurrency(){
        return currency;
    }
    public LiveData<List<GodModel>> fetchFutures(){
        return futures;
    }



}
