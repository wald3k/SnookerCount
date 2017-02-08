package com.example.nogaz.snookercount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.support.v7.app.AlertDialog.*;

public class MainActivity extends AppCompatActivity {

    Button new2PlayerFrameButton;
    Button new3PlayerFrameButton;
    Button leaderBoardsButton;
    Button creditsButton;
    public static final int BUTTON2PLAYERS = 2131492944;
    public static final int BUTTON3PLAYERS = 2131492945;

    String player1Name;
    String player2Name;
    String player3Name;
    Intent matchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new2PlayerFrameButton = (Button)findViewById(R.id.NewFrame2Players_button);
        new2PlayerFrameButton = (Button)findViewById(R.id.NewFrame3Players_button);
        leaderBoardsButton = (Button)findViewById(R.id.LeaderBoards_button);
        creditsButton = (Button)findViewById(R.id.Credits_button);



    }
    public void StartNewFrame(View view){
        int viewId = view.getId();
        matchIntent = new Intent(this, MatchActivity.class);
        Log.d("NUMER PRZYCISKUUUUU", String.valueOf(view.getId()));
        if( viewId == BUTTON2PLAYERS ){
            twoPlayerNameDialog();

        }else if( viewId == BUTTON3PLAYERS ){
            threePlayersNameDialog();

        }

    }
    public void showLeaderboards(View view){
        Intent intent = new Intent(this, LeaderboardsActivity.class);
        startActivity(intent);
    }
    public void showCredits(View view){
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    public void twoPlayerNameDialog(){
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.two_player_name_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText p1Name = (EditText)dialogView.findViewById(R.id.p1_name);
        final EditText p2Name = (EditText)dialogView.findViewById(R.id.p2_name);
        EditText p3Name = (EditText)dialogView.findViewById(R.id.p3_name);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player1Name = p1Name.getText().toString();
                player2Name = p2Name.getText().toString();

                matchIntent.putExtra("PLAYERS", 2);
                matchIntent.putExtra("P_1_NAME", player1Name);
                matchIntent.putExtra("P_2_NAME", player2Name);

                startActivity(matchIntent);
            }
        });
        alertDialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void threePlayersNameDialog(){
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.three_player_name_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText p1Name = (EditText)dialogView.findViewById(R.id.p1_name);
        final EditText p2Name = (EditText)dialogView.findViewById(R.id.p2_name);
        final EditText p3Name = (EditText)dialogView.findViewById(R.id.p3_name);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ok), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player1Name = p1Name.getText().toString();
                player2Name = p2Name.getText().toString();
                player3Name = p3Name.getText().toString();

                matchIntent.putExtra("PLAYERS", 3);
                matchIntent.putExtra("P_1_NAME", player1Name);
                matchIntent.putExtra("P_2_NAME", player2Name);
                matchIntent.putExtra("P_3_NAME", player3Name);

                startActivity(matchIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void saveScore(Player[] players){
        Log.d("MAIN ACTIVITY", "Zapis wynikow ostatniej partii");
        for( int i = 0 ; i < players.length ; ++i ){
            Log.d("SCORE", Integer.toString(players[i].getPlayerNumber()) + ". " + players[i].getPlayerName() + " : " + Integer.toString(players[i].getPoints()) + " pts");
        }
    }

}