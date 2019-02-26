package com.chiruhas.android.zerodhamargincalculator.room.equity;

import android.app.Application;

import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodhamargincalculator.room.equity.EquityRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EquityViewModel extends AndroidViewModel {

    EquityRepository equityRepository;
    LiveData<List<GodEquity>> all;
    public EquityViewModel(@NonNull Application application) {
        super(application);
        equityRepository = new EquityRepository(application);
        all = equityRepository.getAll();
    }

    public void insert(GodEquity GodModel){
        equityRepository.insert(GodModel);

    }

    public void delete(GodEquity GodModel){
        equityRepository.delete(GodModel);
    }

    public LiveData<List<GodEquity>> getAll()
    {
        return  all;
    }


}
