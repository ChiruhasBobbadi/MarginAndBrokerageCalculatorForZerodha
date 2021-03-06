package com.chiruhas.android.zerodha.ViewModel.wisdom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.List;

public class WisdomViewModel extends AndroidViewModel {

    WisdomRepository r;

    LiveData<List<GodModel>> equity;
    LiveData<List<Commodity>> commodity;
    LiveData<List<Currency>> currency;
    LiveData<List<Futures>> futures;

    public WisdomViewModel(@NonNull Application application) {
        super(application);
        r = new WisdomRepository();

        equity = r.getEquity();
        /*commodity = r.getCommodity();
        currency = r.getCurrency();
        futures = r.getFutures();*/
    }

    public LiveData<List<GodModel>> fetchEquity() {
        return equity;
    }

   /* public LiveData<List<Commodity>> fetchCommodity() {

        return commodity;
    }

    public LiveData<List<Currency>> fetchCurrency() {
        return currency;
    }

    public LiveData<List<Futures>> fetchFutures() {
        return futures;
    }*/

}
