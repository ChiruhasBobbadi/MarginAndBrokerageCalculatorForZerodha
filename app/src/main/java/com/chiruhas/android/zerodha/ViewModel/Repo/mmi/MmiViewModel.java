package com.chiruhas.android.zerodha.ViewModel.Repo.mmi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chiruhas.android.zerodha.Model.mmi.Mmi;

public class MmiViewModel extends AndroidViewModel {
    MmiRepository repo;
    LiveData<Mmi> mmi;

    public MmiViewModel(@NonNull Application application) {
        super(application);
        repo = new MmiRepository();

        mmi = repo.getMmi();


    }

    public LiveData<Mmi> fetchMmi() {
        return mmi;
    }
}
