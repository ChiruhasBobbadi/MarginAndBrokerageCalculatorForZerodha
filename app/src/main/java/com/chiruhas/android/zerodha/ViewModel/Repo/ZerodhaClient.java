package com.chiruhas.android.zerodha.ViewModel.Repo;


import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ZerodhaClient {
    @GET("equity")
    Call<List<GodModel>> getEquity();
    @GET("test.json")
    Call<List<Commodity>> getCommodity();
    @GET("currency")
    Call<List<Currency>> getCurrency();
    @GET("futures")
    Call<List<Futures>> getFutures();

}
