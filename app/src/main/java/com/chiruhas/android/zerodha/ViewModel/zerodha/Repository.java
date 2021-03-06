package com.chiruhas.android.zerodha.ViewModel.zerodha;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repository {
    Retrofit retrofit;

    ZerodhaClient zerodhaClient;

    MutableLiveData<List<GodModel>> GodModels;

    private static final String TAG = "Repository";
    public Repository() {
        retrofit = new Retrofit.Builder().baseUrl("https://api.kite.trade/margins/").addConverterFactory(GsonConverterFactory.create()).build();

        zerodhaClient = retrofit.create(ZerodhaClient.class);
        GodModels = new MutableLiveData<>();

    }

    public LiveData<List<GodModel>> getEquity() {
        Call<List<GodModel>> call = zerodhaClient.getEquity();
        call.enqueue(new Callback<List<GodModel>>() {
            @Override
            public void onResponse(Call<List<GodModel>> call, Response<List<GodModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: failed " + response.body());

                    return;
                }

                GodModels.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GodModel>> call, Throwable t) {
                Log.d("Repository", "Failure " + t.getLocalizedMessage());
            }
        });
        return GodModels;

    }


}
