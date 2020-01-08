package com.chiruhas.android.zerodha.ViewModel.Repo.zerodha;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;

import java.net.SocketTimeoutException;
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

    public Repository2() {
        retrofit = new Retrofit.Builder().baseUrl("http://ec2-user@ec2-18-222-28-191.us-east-2.compute.amazonaws.com:5000/").addConverterFactory(GsonConverterFactory.create()).build();

        zerodhaClient = retrofit.create(ZerodhaClient.class);
        commodity = new MutableLiveData<>();
        currency = new MutableLiveData<>();
        futures = new MutableLiveData<>();
    }

    public LiveData<List<Commodity>> getCommodity() {
        Call<List<Commodity>> call = zerodhaClient.getCommodity();
        call.enqueue(new Callback<List<Commodity>>() {
            @Override
            public void onResponse(Call<List<Commodity>> call, Response<List<Commodity>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Commodity request failed.");
                    return;
                }
                commodity.postValue(response.body());
                Log.d(TAG, "onResponse: commodity");
            }

            @Override
            public void onFailure(Call<List<Commodity>> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.d(TAG, "Socket Time out. Please try again. commodity");
                }
            }
        });
        return commodity;
    }

    public LiveData<List<Futures>> getFutures() {
        Call<List<Futures>> call = zerodhaClient.getFutures();
        call.enqueue(new Callback<List<Futures>>() {
            @Override
            public void onResponse(Call<List<Futures>> call, Response<List<Futures>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                futures.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Futures>> call, Throwable t) {
                Log.d(TAG, "onFailure: inside getFutures: " + t.getLocalizedMessage());
            }
        });
        return futures;
    }

    public LiveData<List<Currency>> getCurrency() {
        Call<List<Currency>> call = zerodhaClient.getCurrency();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                currency.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

                Log.d(TAG, "onFailure: inside getCurrency: " + t.getLocalizedMessage());
            }
        });
        return currency;
    }

}
