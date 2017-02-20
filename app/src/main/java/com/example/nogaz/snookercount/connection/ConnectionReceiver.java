package com.example.nogaz.snookercount.connection;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
        if(isNetworkAvailable(context)){
            Toast.makeText(context.getApplicationContext(), "Connected to network", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Disconnected from network", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if device is connected to WIFI or MOBILE NETWORK
     * @param context
     * @return boolean true if device is connected
     */
    public static boolean isNetworkAvailable(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            for (int networkType : networkTypes) {
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Checks if broadcastreceiver is turned on.
     * @param context
     * @return
     */
    public static boolean isReciverOn(Context context){
        ComponentName component = new ComponentName(context, ConnectionReceiver.class);

        int status = context.getPackageManager().getComponentEnabledSetting(component);
        if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.d("ConnectionReceiver","receiver is enabled");
            return  true;
        } else if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            Log.d("ConnectionReceiver","receiver is disabled");
            return false;
        }
        return false;
    }

    /**
     * Enables broadcast receiver for WIFI and MOBILE NETWORK
     * @param context
     */
    public static void enableReceiver(Context context){
        ComponentName component = new ComponentName(context, ConnectionReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);
        Toast.makeText(context.getApplicationContext(), "Broadcast receiver activated", Toast.LENGTH_SHORT).show();
    }

    /**
     * Disables broadcast receiver for WIFI and MOBILE NETWORK
     * @param context
     */
    public static void disableReceiver(Context context){
        ComponentName component = new ComponentName(context, ConnectionReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED , PackageManager.DONT_KILL_APP);
        Toast.makeText(context.getApplicationContext(), "Broadcast receiver deactivated", Toast.LENGTH_SHORT).show();
    }
}
