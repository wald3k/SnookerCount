package com.example.nogaz.snookercount.clientApi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nogaz.snookercount.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nogaz on 16.02.2017.
 */

public class SnookerEventAdapter extends ArrayAdapter<SnookerEvent> {

    private ArrayList<SnookerEvent> snookerEventArrayList;
    private static LayoutInflater inflater = null;

    public SnookerEventAdapter(Context context, ArrayList<SnookerEvent> objects) {
        super(context, 0,  objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SnookerEvent snookerEvent = getItem(position);
        if( convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_element, parent, false);
        }
        TextView eventDateDay = (TextView)convertView.findViewById(R.id.eventDateDay);
        TextView eventDateMonth = (TextView)convertView.findViewById(R.id.eventDateMonth);
        TextView eventDateYear = (TextView)convertView.findViewById(R.id.eventDateYear);
        TextView eventName = (TextView)convertView.findViewById(R.id.eventName);
        TextView eventPlace = (TextView)convertView.findViewById(R.id.eventPlaceCountry);

        eventDateDay.setText(snookerEvent.getDay());
        eventDateMonth.setText(snookerEvent.getMonth(snookerEvent.getMonth()));
        eventDateYear.setText(snookerEvent.getYear());
        eventName.setText(snookerEvent.getEventName());
        eventPlace.setText(snookerEvent.getEventPlace());
        return convertView;
    }
}
