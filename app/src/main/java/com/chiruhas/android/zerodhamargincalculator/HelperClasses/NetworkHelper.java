package com.chiruhas.android.zerodhamargincalculator.HelperClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class NetworkHelper {

    public static  boolean haveNetwork(Context context)
    {
        boolean have_wifi = false;
        boolean have_mobile=false;
        ConnectivityManager connectivityManager =   (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo i :networkInfo){
            if(i.getTypeName().equalsIgnoreCase("WIFI"))
                if(i.isConnected())
                    have_wifi=true;
            if(i.getTypeName().equalsIgnoreCase("mobile"))
                if(i.isConnected())
                    have_mobile=true;
        }
        return have_mobile||have_wifi;
    }


}
