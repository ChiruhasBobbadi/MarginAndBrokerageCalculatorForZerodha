package com.chiruhas.android.zerodhamargincalculator.room.Commodity;

import android.app.Application;

import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodCommodity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CommodityViewModel extends AndroidViewModel {
    CommodityRepository commodityRepository;
    LiveData<List<GodCommodity>> all;
    public CommodityViewModel(@NonNull Application application) {
        super(application);
        commodityRepository = new CommodityRepository(application);
        all = commodityRepository.getAll();
    }

    public void insert(GodCommodity model){
        commodityRepository.insert(model);
    }

    public void delete(GodCommodity model){
        commodityRepository.delete(model);
    }

    public LiveData<List<GodCommodity>> getAll(){
        return all;
    }

}
