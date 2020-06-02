package com.chiruhas.android.zerodha.ViewModel.Repo.mmi;

import com.chiruhas.android.zerodha.Model.mmi.Mmi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface mmiClient {
    @GET("mmi")
    Call<Mmi> geMmi();
}
