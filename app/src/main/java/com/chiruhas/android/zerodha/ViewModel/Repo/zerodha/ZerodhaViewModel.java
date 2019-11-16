package com.chiruhas.android.zerodha.ViewModel.Repo.zerodha;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.Repository;
import com.chiruhas.android.zerodha.ViewModel.Repo.zerodha.Repository2;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ZerodhaViewModel extends AndroidViewModel {
    Repository r;
    Repository2 r2;
    LiveData<List<GodModel>> list ;
    LiveData<List<Commodity>> commodity;
    LiveData<List<Currency>> currency;
    LiveData<List<Futures>> futures;

    public ZerodhaViewModel(@NonNull Application application) {
        super(application);
            r = new Repository();
        r2 = new Repository2();
        list = r.getEquity();
        
        commodity = r2.getCommodity();
        currency=r2.getCurrency();
        futures=r2.getFutures();
    }
    public LiveData<List<GodModel>> fetchEquity()
    {
        return list;
    }
    public LiveData<List<Commodity>> fetchCommodity(){
        Log.d(TAG, "fetchCommodity: ZerodhaViewModel");
        Log.d(TAG, "fetchCommodity: "+commodity.toString());
        return commodity;
    }
    public LiveData<List<Currency>> fetchCurrency(){
        return currency;
    }
    public LiveData<List<Futures>> fetchFutures(){
        return futures;
    }



}
