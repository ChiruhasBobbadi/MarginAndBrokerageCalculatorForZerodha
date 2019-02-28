package com.chiruhas.android.zerodha.room.equity;

import android.content.Context;

import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GodEquity.class},version = 1,exportSchema = false)
public abstract class EquityDatabase extends RoomDatabase {
    private static EquityDatabase instance;

    public abstract EquityDao equityDao();

    public static synchronized EquityDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), EquityDatabase.class, "equity_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    }

