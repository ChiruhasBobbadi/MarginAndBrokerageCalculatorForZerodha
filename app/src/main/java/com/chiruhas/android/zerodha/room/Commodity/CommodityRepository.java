//package com.chiruhas.android.zerodha.room.Commodity;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import com.chiruhas.android.zerodha.Model.Equity.Commodity;
//import com.chiruhas.android.zerodha.Model.Equity.RoomModels.GodCommodity;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//
//public class CommodityRepository {
//    private CommodityDao commodityDao;
//    private LiveData<List<Commodity>> all;
//
//    public CommodityRepository(Application application){
//        CommodityDatabase commodityDatabase = CommodityDatabase.getInstance(application);
//        commodityDao = commodityDatabase.commodityDao();
//        all = commodityDao.getAll();
//    }
//    public void insert(Commodity commodity){
//        new InsertCommodityAsync(commodityDao).execute(commodity);
//    }
//
//    public void delete(Commodity commodity){
//        new DeleteCommodityAsync(commodityDao).execute(commodity);
//    }
//
//    public LiveData<List<Commodity>> getAll(){
//        return all;
//    }
//
//
//    class InsertCommodityAsync extends AsyncTask<Commodity,Void,Void> {
//
//        private CommodityDao commodityDao;
//
//        InsertCommodityAsync(CommodityDao commodityDao)
//        {
//            this.commodityDao=commodityDao;
//        }
//        @Override
//        protected Void doInBackground(Commodity... commodities) {
//            commodityDao.insert(commodities[0]);
//            return null;
//        }
//    }
//
//    class DeleteCommodityAsync extends AsyncTask<Commodity,Void,Void>{
//        private CommodityDao dao;
//
//        DeleteCommodityAsync(CommodityDao dao){
//            this.dao=dao;
//        }
//
//
//        @Override
//        protected Void doInBackground(Commodity... godCommodities) {
//            dao.delete(godCommodities[0]);
//
//            return null;
//        }
//    }
//
//}
