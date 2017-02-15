package com.example.nogaz.snookercount.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nogaz on 15.02.2017.
 */

public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CONNECTION_RECEIVER", "Metoda onReceive");
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isAvailable() || mobile.isAvailable() ){
            Log.d("CONNECTION_RECEIVER", "Network Connected");
            Toast.makeText(context.getApplicationContext(), "Connected to network", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Disconnected from network", Toast.LENGTH_SHORT).show();
            Log.d("CONNECTION_RECEIVER", "Network Disconnected");
        }

    }
}
