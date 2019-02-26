package com.chiruhas.android.zerodhamargincalculator.room.Commodity;

import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodCommodity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CommodityDao {
    @Insert
    void insert(GodCommodity commodity);
    @Delete
    void delete(GodCommodity commodity);

    @Query("SELECT * FROM commodity_table")
    LiveData<List<GodCommodity>> getAll();

}
