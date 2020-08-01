package com.chiruhas.android.zerodha.ViewModel.Repo.alice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chiruhas.android.zerodha.Model.Currency;
import com.chiruhas.android.zerodha.Model.Equity.Commodity;
import com.chiruhas.android.zerodha.Model.Equity.Futures;
import com.chiruhas.android.zerodha.Model.Equity.GodModel;
import com.chiruhas.android.zerodha.ViewModel.Repo.asta.AstaClient;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

class AliceRepository {
    Retrofit retrofit;
    AstaClient astaClient;
    MutableLiveData<List<GodModel>> equity;
    MutableLiveData<List<Commodity>> commodity;
    MutableLiveData<List<Currency>> currency;
    MutableLiveData<List<Futures>> futures;


    public AliceRepository() {
        retrofit = new Retrofit.Builder().baseUrl("http://ec2-13-235-73-156.ap-south-1.compute.amazonaws.com:5000/alice/").addConverterFactory(GsonConverterFactory.create()).build();

        astaClient = retrofit.create(AstaClient.class);
        equity = new MutableLiveData<>();
        commodity = new MutableLiveData<>();
        currency = new MutableLiveData<>();
        futures = new MutableLiveData<>();
    }

    public LiveData<List<GodModel>> getEquity() {
        Call<List<GodModel>> call = astaClient.getEquity();
        call.enqueue(new Callback<List<GodModel>>() {
            @Override
            public void onResponse(Call<List<GodModel>> call, Response<List<GodModel>> response) {
                if (!response.isSuccessful()) {
                    //Log.d(TAG, "onResponse: Commodity request failed.");
                    return;
                }
                equity.postValue(response.body());
                //Log.d(TAG, "onResponse: commodity");
            }

            @Override
            public void onFailure(Call<List<GodModel>> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.d(TAG, "Socket Time out. Please try again. astha equity");
                }
            }
        });
        return equity;
    }

    public LiveData<List<Commodity>> getCommodity() {
        Call<List<Commodity>> call = astaClient.getCommodity();
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
                    Log.d(TAG, "Socket Time out. Please try again. astha commodity");
                }
            }
        });
        return commodity;
    }

    public LiveData<List<Futures>> getFutures() {
        Call<List<Futures>> call = astaClient.getFutures();
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
        Call<List<Currency>> call = astaClient.getCurrency();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Log.d(TAG, "onResponse: " + response.message());
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
