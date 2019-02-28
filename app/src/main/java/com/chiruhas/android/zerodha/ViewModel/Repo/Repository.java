package com.chiruhas.android.zerodha.ViewModel.Repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository {
    Retrofit retrofit;
    ZerodhaClient zerodhaClient;
    MutableLiveData<List<GodModel>> GodModels;
    MutableLiveData<List<GodModel>> commodity;
    public Repository()
    {
         retrofit= new Retrofit.Builder().baseUrl("https://api.kite.trade/margins/").addConverterFactory(GsonConverterFactory.create()).build();
        zerodhaClient= retrofit.create(ZerodhaClient.class);
        GodModels=new MutableLiveData<>();
        commodity= new MutableLiveData<>();
    }

    public LiveData<List<GodModel>> getEquity()
    {
        Call<List<GodModel>> call = zerodhaClient.getEquity();
        call.enqueue(new Callback<List<GodModel>>() {
            @Override
            public void onResponse(Call<List<GodModel>> call, Response<List<GodModel>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
               GodModels.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GodModel>> call, Throwable t) {
                Log.d("Repository","Failure "+t.getLocalizedMessage());
            }
        });
        return GodModels;

    }

    public LiveData<List<GodModel>> getCommodity(){
        Call<List<GodModel>> call = zerodhaClient.getCommodity();
        call.enqueue(new Callback<List<GodModel>>() {
            @Override
            public void onResponse(Call<List<GodModel>> call, Response<List<GodModel>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                commodity.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GodModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: inside getCommodity: "+t.getLocalizedMessage());
            }
        });
        return commodity;
    }
}
