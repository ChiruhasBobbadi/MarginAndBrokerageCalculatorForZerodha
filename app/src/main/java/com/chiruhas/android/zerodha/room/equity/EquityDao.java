package com.chiruhas.android.zerodha.room.equity;

import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EquityDao {

    @Insert
    public void insert(GodEquity godModel);

    @Delete
    public void delete(GodEquity godModel);

    @Query("SELECT * FROM equity_table ")

    public LiveData<List<GodEquity>> getAll();


}
