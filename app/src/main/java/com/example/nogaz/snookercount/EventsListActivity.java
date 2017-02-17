package com.example.nogaz.snookercount;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.nogaz.snookercount.clientApi.RankingAsyncTaskDownloader;
import com.example.nogaz.snookercount.clientApi.SnookerEvent;
import com.example.nogaz.snookercount.clientApi.SnookerEventAdapter;

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

        setContentView(R.layout.snooker_events_layout);

        eventList = new ArrayList<SnookerEvent>();
        snookerEventAdapter = new SnookerEventAdapter(this, eventList);

        eventsListView = (ListView)findViewById(R.id.eventListView);
        eventsListView.setAdapter(snookerEventAdapter);

        rankingAsyncTaskDownloader = new RankingAsyncTaskDownloader(this);
        rankingAsyncTaskDownloader.execute();
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
}
