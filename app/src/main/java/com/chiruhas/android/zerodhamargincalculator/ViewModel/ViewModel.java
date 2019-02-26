package com.chiruhas.android.zerodhamargincalculator.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;

import com.chiruhas.android.zerodhamargincalculator.ViewModel.Repo.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    Repository r;
    LiveData<List<GodModel>> list ;
    LiveData<List<GodModel>> commodity;
    public ViewModel(@NonNull Application application) {
        super(application);
        r = new Repository();
        list = r.getEquity();
        commodity = r.getCommodity();
    }
    public LiveData<List<GodModel>> fetchEquity()
    {
        return list;
    }
    public LiveData<List<GodModel>> fetchCommodity(){
        return commodity;
    }
}
