package com.example.nogaz.snookercount;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waldi on 2016-07-02.
 */

/**
 * Klasa sluzaca do wyswietlania elementow w listView.
 */
public class CustomAdapter extends ArrayAdapter {
    public CustomAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
    }

    public CustomAdapter(Context context, int resource, ArrayList<Player> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.row, null);
        }
        Player p = (Player) getItem(position);
        if(p != null){
            TextView tv1 = (TextView) rowView.findViewById(R.id.rankingPosition);
            TextView tv2 = (TextView) rowView.findViewById(R.id.playerNickname);
            TextView tv3 = (TextView) rowView.findViewById(R.id.playerPts);

            if(tv1 != null){

                tv1.setText(String.valueOf(position+1)+". ");
            }
            if(tv2 != null){

                tv2.setText(p.getPlayerName());
            }
            if(tv3 != null){

                tv3.setText(String.valueOf(p.getPoints()));
            }
        }
        return rowView;
    }

}