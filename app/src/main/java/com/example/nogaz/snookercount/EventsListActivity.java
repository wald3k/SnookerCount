package com.example.nogaz.snookercount;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nogaz.snookercount.clientApi.RankingAsyncTaskDownloader;
import com.example.nogaz.snookercount.clientApi.SnookerEvent;
import com.example.nogaz.snookercount.clientApi.SnookerEventAdapter;
import com.example.nogaz.snookercount.connection.ConnectionReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nogaz on 15.02.2017.
 */

public class EventsListActivity extends Activity {

    public static final int PLEASE_WAIT_DIALOG = 1;

    RankingAsyncTaskDownloader rankingAsyncTaskDownloader = null;
    ListView eventsListView;
    ArrayList<SnookerEvent> eventList;
    SnookerEventAdapter snookerEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ConnectionReceiver.isReciverOn(this) == false){
            ConnectionReceiver.enableReceiver(this);
        }
        setContentView(R.layout.snooker_events_layout);
        eventList = new ArrayList<SnookerEvent>();
        snookerEventAdapter = new SnookerEventAdapter(this, eventList);
        eventsListView = (ListView)findViewById(R.id.eventListView);
        eventsListView.setAdapter(snookerEventAdapter);
        this.launchAsyncTaskDownloader();
    }
    private void launchAsyncTaskDownloader(){
        if(ConnectionReceiver.isNetworkAvailable(this) == true){
            rankingAsyncTaskDownloader = new RankingAsyncTaskDownloader(this);
            rankingAsyncTaskDownloader.execute();
        }
        else{
            Toast.makeText(this, "Cannot download event list...", Toast.LENGTH_SHORT).show();
            this.confirmDialog(this);
        }
    }
    private void confirmDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Context myContext = context;
        builder
                .setMessage("No connection")
                .setPositiveButton("Try again",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        launchAsyncTaskDownloader();
                    }
                })
                .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        finish();//finish EventListActivity
                    }
                })
                .show();
    }
    public boolean isNetworkAvailable(){
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isAvailable() || mobile.isAvailable() ){
            Log.d("CONNECTION_RECEIVER", "Network Connected");
            Toast.makeText(this, "Connected to network", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "Disconnected from network", Toast.LENGTH_SHORT).show();
            Log.d("CONNECTION_RECEIVER", "Network Disconnected");
            return false;
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PLEASE_WAIT_DIALOG:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Downloading event list");
                dialog.setMessage("Please wait...");
                dialog.setCancelable(true);
                return dialog;
            default:
                break;
        }
        return null;
    }

    public  void setUpEventList(JSONArray jsonArray){
        for(int i = 0 ; i < jsonArray.length() ; ++i ){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SnookerEvent newEvent = new SnookerEvent();

                newEvent.setEventName( (String)jsonObject.get("Name") );
                newEvent.setEventStartDate( (String)jsonObject.get("StartDate") );
                newEvent.setEventPlaceCity( (String)jsonObject.get("City") );
                newEvent.setEventPlaceCountry( (String)jsonObject.get("Country") );
                Log.d("EVENT_LIST_ACTIVITY", newEvent.toString());
                snookerEventAdapter.add(newEvent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(ConnectionReceiver.isReciverOn(this)){
            ConnectionReceiver.disableReceiver(this);
        }
        super.onDestroy();
    }
}
