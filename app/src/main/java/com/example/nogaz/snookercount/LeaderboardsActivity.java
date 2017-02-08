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

/**
 * Activity responsible for presenting highscores on a screen.
 */
public class LeaderboardsActivity extends AppCompatActivity {
    private ArrayList<Player> bestPlayers;
    private ArrayAdapter<Player> adapter;
    private ListView listView;
    private DbManager dbManager;
    private long MAX_NUMBER_OF_PLAYERS_LISTED = 5;//limit of best results displayed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_leaderboards);
        //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
        dbManager = new DbManager(this);
        bestPlayers = new ArrayList<>();
        readScores();
        showRank();
    }
    /**
     * Assigns queried list of players to bestPlayers variable. Also prints highscores to a result.
     */
    public void readScores(){
        bestPlayers = dbManager.getPlayerResults(MAX_NUMBER_OF_PLAYERS_LISTED);
        //Keep code below just in case. The sorting is done now when querying the database!
        //Sorting list by a score using custom Comparator class.
//        if(bestPlayers.size() > 1) {
//            Collections.sort(bestPlayers, new PlayerComparator());//sorting list of players
//        }
        for(int i =0; i < bestPlayers.size(); i++){
            Log.d("MATCH ACTIVITY", bestPlayers.get(i).getPlayerName() + " " + bestPlayers.get(i).getPoints());
        }
        Log.d("MATCH ACTIVITY", "odczytano zwyciezcow");
    }
    /**
     * Shows bestPlayers list on a device screen.
     */
    public void showRank(){
        listView = (ListView)findViewById(R.id.rankingList);
        adapter = new CustomAdapter(this,R.layout.row,bestPlayers);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
