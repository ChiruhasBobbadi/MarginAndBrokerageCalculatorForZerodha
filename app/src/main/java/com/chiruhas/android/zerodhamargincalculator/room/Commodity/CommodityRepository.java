package com.chiruhas.android.zerodhamargincalculator.room.Commodity;

import android.app.Application;
import android.os.AsyncTask;

import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodCommodity;
import com.chiruhas.android.zerodhamargincalculator.Model.Equity.RoomModels.GodEquity;
import com.chiruhas.android.zerodhamargincalculator.room.equity.EquityDao;
import com.chiruhas.android.zerodhamargincalculator.room.equity.EquityRepository;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CommodityRepository {
    private CommodityDao commodityDao;
    private LiveData<List<GodCommodity>> all;

    public CommodityRepository(Application application){
        CommodityDatabase commodityDatabase = CommodityDatabase.getInstance(application);
        commodityDao = commodityDatabase.commodityDao();
        all = commodityDao.getAll();
    }
    public void insert(GodCommodity GodModel){
        new InsertCommodityAsync(commodityDao).execute(GodModel);
    }

    public void delete(GodCommodity GodModel){
        new DeleteCommodityAsync(commodityDao).execute(GodModel);
    }

    public LiveData<List<GodCommodity>> getAll(){
        return all;
    }


    class InsertCommodityAsync extends AsyncTask<GodCommodity,Void,Void> {

        private CommodityDao commodityDao;

        InsertCommodityAsync(CommodityDao commodityDao)
        {
            this.commodityDao=commodityDao;
        }
        @Override
        protected Void doInBackground(GodCommodity... commodities) {
            commodityDao.insert(commodities[0]);
            return null;
        }
    }

    class DeleteCommodityAsync extends AsyncTask<GodCommodity,Void,Void>{
        private CommodityDao dao;

        DeleteCommodityAsync(CommodityDao dao){
            this.dao=dao;
        }


        @Override
        protected Void doInBackground(GodCommodity... godCommodities) {
            dao.delete(godCommodities[0]);

            return null;
        }
    }

}
