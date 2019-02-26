package com.chiruhas.android.zerodhamargincalculator.BroadcastReceivers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.chiruhas.android.zerodhamargincalculator.R;

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
