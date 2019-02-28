package com.chiruhas.android.zerodha.room.equity;

import android.app.Application;
import android.os.AsyncTask;

import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodEquity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EquityRepository {
    EquityDao equityDao;
    LiveData<List<GodEquity>> eq;

    public EquityRepository(Application application)
    {
        EquityDatabase equityDatabase = EquityDatabase.getInstance(application);
        equityDao = equityDatabase.equityDao();
        eq = equityDao.getAll();
    }

    public void insert(GodEquity GodModel){
        new InsertEquityAsync(equityDao).execute(GodModel);
    }

    public void delete(GodEquity GodModel){
        new DeleteAsync(equityDao).execute(GodModel);
    }

    public LiveData<List<GodEquity>> getAll(){
        return eq;
    }






    class InsertEquityAsync extends AsyncTask<GodEquity,Void,Void>{

        private EquityDao equityDao;

        InsertEquityAsync(EquityDao equityDao)
        {
            this.equityDao=equityDao;
        }
        @Override
        protected Void doInBackground(GodEquity... godEquities) {
            equityDao.insert(godEquities[0]);
            return null;
        }
    }

    class DeleteAsync extends AsyncTask<GodEquity,Void,Void>{
private EquityDao equityDao;

        DeleteAsync(EquityDao equityDao){
            this.equityDao=equityDao;
        }


        @Override
        protected Void doInBackground(GodEquity... GodModels) {
            equityDao.delete(GodModels[0]);

            return null;
        }
    }
}
