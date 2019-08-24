package com.chiruhas.android.zerodha.room.Commodity;

import android.app.Application;

import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodCommodity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CommodityViewModel extends AndroidViewModel {
    CommodityRepository commodityRepository;
    LiveData<List<Commodity>> all;
    public CommodityViewModel(@NonNull Application application) {
        super(application);
        commodityRepository = new CommodityRepository(application);
        all = commodityRepository.getAll();
    }

    public void insert(Commodity model){
        commodityRepository.insert(model);
    }

    public void delete(Commodity model){
        commodityRepository.delete(model);
    }

    public LiveData<List<Commodity>> getAll(){
        return all;
    }

}
