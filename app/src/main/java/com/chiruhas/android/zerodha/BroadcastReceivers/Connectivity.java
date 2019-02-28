package com.chiruhas.android.zerodha.BroadcastReceivers;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connectivity extends BroadcastReceiver {
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    Dialog dialog;
    @Override
    public void onReceive(Context context, Intent intent) {

        connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo!=null && networkInfo.isConnected()){
                // dismiss


            }
            else {



            }

        }
    }
}
