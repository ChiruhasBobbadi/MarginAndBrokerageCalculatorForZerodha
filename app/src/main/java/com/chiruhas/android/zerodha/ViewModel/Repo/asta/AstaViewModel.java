package com.chiruhas.android.zerodha.ViewModel.Repo.asta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.List;

public class AstaViewModel extends AndroidViewModel {

    AstaRepository r;

    LiveData<List<GodModel>> equity;
    LiveData<List<Commodity>> commodity;
    LiveData<List<Currency>> currency;
    LiveData<List<Futures>> futures;

    public AstaViewModel(@NonNull Application application) {
        super(application);
        r = new AstaRepository();

        equity = r.getEquity();
        commodity = r.getCommodity();
        currency = r.getCurrency();
        futures = r.getFutures();
    }

    public LiveData<List<GodModel>> fetchEquity() {
        return equity;
    }

    public LiveData<List<Commodity>> fetchCommodity() {

        return commodity;
    }

    public LiveData<List<Currency>> fetchCurrency() {
        return currency;
    }

    public LiveData<List<Futures>> fetchFutures() {
        return futures;
    }

}
