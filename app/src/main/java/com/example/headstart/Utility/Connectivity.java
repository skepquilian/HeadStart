package com.example.headstart.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connectivity {
    private static final String TAG = "Connectivity";

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager!= null){
            Log.d(TAG, "isConnectedToInternet: connection type is queried");
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo!= null){
                for (int i = 0; i < networkInfo.length; i++){
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
