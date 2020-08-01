package com.chiruhas.android.zerodha.ViewModel.Repo.mmi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chiruhas.android.zerodha.Model.mmi.Mmi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MmiRepository {
    Retrofit retrofit;
    mmiClient client;
    MutableLiveData<Mmi> mmi;

    public MmiRepository() {
        retrofit = new Retrofit.Builder().baseUrl("http://ec2-13-235-73-156.ap-south-1.compute.amazonaws.com:5000/").addConverterFactory(GsonConverterFactory.create()).build();

        client = retrofit.create(mmiClient.class);
        mmi = new MutableLiveData<>();

    }

    public LiveData<Mmi> getMmi() {
        Call<Mmi> call = client.geMmi();
        call.enqueue(new Callback<Mmi>() {
            @Override
            public void onResponse(Call<Mmi> call, Response<Mmi> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Commodity request failed.");
                    return;
                }
                mmi.postValue(response.body());

            }

            @Override
            public void onFailure(Call<Mmi> call, Throwable t) {

            }
        });
        return mmi;
    }
}
