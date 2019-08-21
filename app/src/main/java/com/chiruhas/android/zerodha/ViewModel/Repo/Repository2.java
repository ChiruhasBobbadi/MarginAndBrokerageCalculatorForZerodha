package com.chiruhas.android.zerodha.ViewModel.Repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository2 {
    Retrofit retrofit;
    ZerodhaClient zerodhaClient;
    MutableLiveData<List<Commodity>> commodity;
    MutableLiveData<List<Currency>> currency;
    MutableLiveData<List<Futures>> futures;

  public  Repository2(){
        retrofit = new Retrofit.Builder().baseUrl("https://zerodhamargincalculator.web.app/").addConverterFactory(GsonConverterFactory.create()).build();

        zerodhaClient= retrofit.create(ZerodhaClient.class);
        commodity= new MutableLiveData<>();
    }

    public LiveData<List<Commodity>> getCommodity(){
        Call<List<Commodity>> call = zerodhaClient.getCommodity();
        call.enqueue(new Callback<List<Commodity>>() {
            @Override
            public void onResponse(Call<List<Commodity>> call, Response<List<Commodity>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                commodity.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Commodity>> call, Throwable t) {
                Log.d(TAG, "onFailure: inside getCommodity: "+t.getLocalizedMessage());
            }
        });
        return commodity;
    }

}
