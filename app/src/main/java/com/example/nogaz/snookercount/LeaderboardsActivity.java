package com.example.nogaz.snookercount;

import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class LeaderboardsActivity extends AppCompatActivity {
    private ArrayList<Player> bestPlayers;
    private ArrayAdapter<Player> adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_leaderboards);
        //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
        bestPlayers = new ArrayList<>();
        readScores();
        showRank();
    }

    public void readScores(){

        try {
            String filename = MatchActivity.RESULTS_FILE_NAME;
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line,"||");
                String name = st.nextToken();
                String pts = st.nextToken();
                Player p = new Player();
                p.setPlayerName(name);
                p.setPoints(Integer.valueOf(pts));
                bestPlayers.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.noResultsToShow) ,Toast.LENGTH_SHORT).show();
        }
        if(bestPlayers.size() > 1)
            Collections.sort(bestPlayers,new PlayerComparator());
        for(int i =0; i < bestPlayers.size(); i++){
            Log.d("MATCH ACTIVITY", bestPlayers.get(i).getPlayerName() + " " + bestPlayers.get(i).getPoints());
        }
        Log.d("MATCH ACTIVITY", "odczytano zwyciezcow");

    }
    public void showRank(){
        listView = (ListView)findViewById(R.id.rankingList);
        adapter = new CustomAdapter(this,R.layout.row,bestPlayers);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
//Inny sposob
//    public void showRank(){
//        listView = (ListView)findViewById(R.id.rankingList);
//        adapter = new ArrayAdapter<Player>(getApplicationContext(),android.R.layout.simple_list_item_1,bestPlayers){
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                // Get the Item from ListView
//                View view = super.getView(position, convertView, parent);
//
//                // Initialize a TextView for ListView each Item
//                TextView tv = (TextView) view.findViewById(android.R.id.text1);
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                int height = displaymetrics.heightPixels;
//                int width = displaymetrics.widthPixels;
//                tv.setWidth(400);
//                //tv.setText(String.format("%3s.%s", String.valueOf(position + 1), tv.getText()));
//                if(position+1 <10){
//                    tv.setText(" " + String.valueOf(position + 1) +"."+ tv.getText());
//                }
//                else{
//                    tv.setText(String.valueOf(position + 1) +"."+ tv.getText());
//                }
//
//                // Set the text color of TextView (ListView Item)
//                if(position%2==0){
//                    tv.setTextColor(Color.rgb(255,0,0));
//                }
//                else{
//                    tv.setTextColor(Color.rgb(238,0,0));
//                }
//
//                // Generate ListView Item using TextView
//                return view;
//            }
//        };
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

}
