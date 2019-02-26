package com.chiruhas.android.zerodhamargincalculator.ViewModel.Repo;


import com.chiruhas.android.zerodhamargincalculator.Model.Equity.GodModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ZerodhaClient {
    @GET("equity")
    Call<List<GodModel>> getEquity();
    @GET("commodity")
    Call<List<GodModel>> getCommodity();

}
