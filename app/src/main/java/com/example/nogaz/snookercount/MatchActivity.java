package com.example.nogaz.snookercount;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class MatchActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADDPOINTID = 2131492976;
    public static final int ADDFOULPOINT = 2131492977;
    public static final int NEXTPLAYER = 2131492978;

    public static final int RED_POINT = 2131492948 ;
    public static final int YELLOW_POINT = 2131492949 ;
    public static final int GREEN_POINT = 2131492950 ;
    public static final int BROWN_POINT = 2131492951 ;
    public static final int BLUE_POINT = 2131492952 ;
    public static final int PINK_POINT = 2131492953 ;
    public static final int BLACK_POINT = 2131492954 ;


    public static final int WHITE_FOUL = 2131492955;
    public static final int BLUE_FOUL = 2131492956;
    public static final int PINK_FOUL = 2131492957;
    public static final int BLACK_FOUL = 2131492958;

    public static final String RESULTS_FILE_NAME="results.txt";
    public static final int HIGHSCORES_PERSON_LIMIT = 20;

    Button redBallButton;
    Button yellowBallButton;
    Button greenBallButton;
    Button brownBallButton;
    Button blueBallButton;
    Button pinkBallButton;
    Button blackBallButton;

    int howMuchPlayers;
    Player[] players;
    View[] views;
    PlayerView[] playerViews;
    Player gameWinner;

    int whoPlays = 0;


    int redBallCount = 15;
    final int redBallPoint = 1;
    final int yellowBallPoint = 2;
    final int greenBallPoint = 3;
    final int brownBallPoint = 4;
    final int blueBallPoint = 5;
    final int pinkBallPoint = 6;
    final int blackBallPoint = 7;

    final int whiteFoulPoint = 4;
    final int blueFoulPoint = 5;
    final int pinkFoulPoint = 6;
    final int blackFoulPoint = 7;


    boolean colorBallMove = false;
    boolean redLeft = false;
    boolean yellowLeft = false;
    boolean greenLeft = false;
    boolean brownLeft = false;
    boolean blueLeft = false;
    boolean pinkLeft = false;
    boolean blackLeft = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MATCH ACTIVITY", "metoda onCreate");
        setContentView(R.layout.match_layout);
/*
        redBallButton = (Button)findViewById(R.id.red_ball);
        yellowBallButton = (Button)findViewById(R.id.yellow_ball);
        greenBallButton = (Button)findViewById(R.id.green_ball);
        brownBallButton = (Button)findViewById(R.id.brown_ball);
        blueBallButton = (Button)findViewById(R.id.blue_ball);
        blackBallButton = (Button)findViewById(R.id.black_ball);*/

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String p1Name;
        String p2Name;
        String p3Name;
        if( bundle != null ){
            howMuchPlayers = bundle.getInt("PLAYERS");
            /*if( howMuchPlayers == 2 ){
                p1Name = bundle.getString("P_1_NAME");
                p2Name = bundle.getString("P_2_NAME");
                players[0].setPlayerName(p1Name);
                players[1].setPlayerName(p2Name);
            }else if( howMuchPlayers == 3 ){
                p1Name = bundle.getString("P_1_NAME");
                p2Name = bundle.getString("P_2_NAME");
                p3Name = bundle.getString("P_3_NAME");
                players[0].setPlayerName(p1Name);
                players[1].setPlayerName(p2Name);
                players[2].setPlayerName(p3Name);
            }*/
        }

        LayoutInflater vi = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup insertPoint = (ViewGroup)findViewById(R.id.playersCardHolder);
        players = new Player[howMuchPlayers];
        views = new View[howMuchPlayers];
        //playerViews = new PlayerView[howMuchPlayers];
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            players[i] = new Player(i);
            int tmp = i+1;
            players[i].setPlayerName(bundle.getString("P_" + tmp + "_NAME"));
            //playerViews[i] = new PlayerView(getApplicationContext());


            views[i] = vi.inflate(R.layout.player_frame_window, null);
            insertPoint.addView(views[i]);
            //insertPoint.addView(playerViews[i]);

            TextView tv = (TextView)views[i].findViewById(R.id.playerId);
            tv.setText(players[i].getPlayerName());

            views[i].findViewById(R.id.addPoint).setOnClickListener(this);
            views[i].findViewById(R.id.foulPoint).setOnClickListener(this);
            views[i].findViewById(R.id.nextPlayer).setOnClickListener(this);

            /*views[i].findViewById(R.id.foulPoint);
            views[i].findViewById(R.id.nextPlayer);*/


        }

        setActivePlayer();


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MATCH ACTIVITY", "metoda onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MATCH ACTIVITY", "metoda onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MATCH ACTIVITY", "metoda onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MATCH ACTIVITY", "metoda onStop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.decRedBallCount:
                decrementRedBallCount();
                return true;
            case R.id.showTable:
                showTable();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Log.d("MATCH ACTIVITY", "Wcisnieto przycisk: " + String.valueOf(v.getId()));
        if( viewId == NEXTPLAYER ){
            nextPlayer();
            Log.d("CHANGE PLAYER", "kolej gracza nr " + whoPlays);
        }else if( viewId == ADDPOINTID ){
            showAddPointPopup(v);
        }else if( viewId == ADDFOULPOINT ){
            showFoulPopup(v);
        }
    }

    public void setActivePlayer(){
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( i == whoPlays ){
                views[i].setAlpha(1.0f);
                views[i].setActivated(true);
                views[i].findViewById(R.id.addPoint).setOnClickListener(this);
                views[i].findViewById(R.id.nextPlayer).setOnClickListener(this);
                views[i].findViewById(R.id.foulPoint).setOnClickListener(this);
            }else{
                views[i].setAlpha(0.5f);
                views[i].setActivated(false);
                views[i].findViewById(R.id.addPoint).setOnClickListener(null);
                views[i].findViewById(R.id.nextPlayer).setOnClickListener(null);
                views[i].findViewById(R.id.foulPoint).setOnClickListener(null);
            }

        }
    }
    public void nextPlayer(){
        whoPlays += 1;
        whoPlays = whoPlays%howMuchPlayers;
        colorBallMove = false;
       setActivePlayer();
    }

    public void updatePlayerPoints(int player, int points){
        players[player].addPoints(points);
        TextView tc = (TextView)views[player].findViewById(R.id.playerScore);
        Log.d("SCORE" , tc.toString());
        String str = Integer.toString(players[player].getPoints()) + " pts";
        tc.setText(str);
    }
    public void updateFoulPoints(int player, int points){
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( i != player ){
                players[i].addPoints(points);
                String str = Integer.toString(players[i].getPoints()) + " pts";
                TextView tc = (TextView)views[i].findViewById(R.id.playerScore);
                tc.setText(str);
            }
        }
    }




    public void showAddPointPopup(View view){
        Log.d("MATCH ACTIVITY", "wejscie do metody showAddPointPopup");

        final Dialog addPointDialog = new Dialog(MatchActivity.this);
        addPointDialog.setContentView(R.layout.add_point_popup_layout);
        ImageButton redBallButton = (ImageButton)addPointDialog.findViewById(R.id.red_ball);
        ImageButton yellowBallButton = (ImageButton)addPointDialog.findViewById(R.id.yellow_ball);
        ImageButton greenBallButton = (ImageButton)addPointDialog.findViewById(R.id.green_ball);
        ImageButton brownBallButton = (ImageButton)addPointDialog.findViewById(R.id.brown_ball);
        ImageButton blueBallButton = (ImageButton)addPointDialog.findViewById(R.id.blue_ball);
        ImageButton pinkBallButton = (ImageButton)addPointDialog.findViewById(R.id.pink_ball);
        ImageButton blackBallButton = (ImageButton)addPointDialog.findViewById(R.id.black_ball);

        View.OnClickListener dialogListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                Log.d("ADDPOINT DIALOG", "nr przycisku " + viewId);
                switch (viewId){
                    case RED_POINT:
                        Log.d("ADD POINTS", "Punkty za bile czerwona +" + redBallPoint);
                        updatePlayerPoints(whoPlays, redBallPoint);
                        redBallCount--;
                        colorBallMove = true;
                        break;
                    case YELLOW_POINT:
                        Log.d("ADD POINTS", "Punkty za bile zolta +" + yellowBallPoint);
                        updatePlayerPoints(whoPlays, yellowBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            yellowLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case GREEN_POINT:
                        Log.d("ADD POINTS", "Punkty za bile zielona +" + greenBallPoint);
                        updatePlayerPoints(whoPlays, greenBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            greenLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case BROWN_POINT:
                        Log.d("ADD POINTS", "Punkty za bile brazowa +" + brownBallPoint);
                        updatePlayerPoints(whoPlays, brownBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            brownLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case BLUE_POINT:
                        Log.d("ADD POINTS", "Punkty za bile niebieska +" + blueBallPoint);
                        updatePlayerPoints(whoPlays, blueBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            blueLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case PINK_POINT:
                        Log.d("ADD POINTS", "Punkty za bile rozowa +" + pinkBallPoint);
                        updatePlayerPoints(whoPlays, pinkBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            pinkLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case BLACK_POINT:
                        Log.d("ADD POINTS", "Punkty za bile czarna +" + blackBallPoint);
                        updatePlayerPoints(whoPlays, blackBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            blackLeft = true;
                            finishGame(v);
                        }
                        colorBallMove = false;
                        break;
                }

                addPointDialog.dismiss();
            }
        };
        redBallButton.setOnClickListener(dialogListener);
        yellowBallButton.setOnClickListener(dialogListener);
        greenBallButton.setOnClickListener(dialogListener);
        brownBallButton.setOnClickListener(dialogListener);
        blueBallButton.setOnClickListener(dialogListener);
        pinkBallButton.setOnClickListener(dialogListener);
        blackBallButton.setOnClickListener(dialogListener);

        addPointDialog.setTitle(getApplicationContext().getResources().getString(R.string.addPoints));


        if( redBallCount > 0 ) {
            //some red balls on table
            if (colorBallMove == true) {
                // scored color ball
                deactivateButton(redBallButton);

                activateButton(yellowBallButton, dialogListener);
                activateButton(greenBallButton, dialogListener);
                activateButton(brownBallButton, dialogListener);
                activateButton(blueBallButton, dialogListener);
                activateButton(pinkBallButton, dialogListener);
                activateButton(blackBallButton, dialogListener);
            } else {
                // scored red ball
                activateButton(redBallButton, dialogListener);

                deactivateButton(yellowBallButton);
                deactivateButton(greenBallButton);
                deactivateButton(brownBallButton);
                deactivateButton(blueBallButton);
                deactivateButton(pinkBallButton);
                deactivateButton(blackBallButton);
            }
        }else{
            // no red balls on table
            if( colorBallMove == true ){
                // last color ball shot after red ball
                deactivateButton(redBallButton);
                activateButton(yellowBallButton, dialogListener);
                activateButton(greenBallButton, dialogListener);
                activateButton(brownBallButton, dialogListener);
                activateButton(blueBallButton, dialogListener);
                activateButton(pinkBallButton, dialogListener);
                activateButton(blackBallButton, dialogListener);
            }else {
                // no red balls on table && we shot color balls in turn
                deactivateButton(redBallButton);
                deactivateButton(yellowBallButton);
                deactivateButton(greenBallButton);
                deactivateButton(brownBallButton);
                deactivateButton(blueBallButton);
                deactivateButton(pinkBallButton);
                deactivateButton(blackBallButton);
                if (pinkLeft) {
                    activateButton(blackBallButton, dialogListener);
                }else if (blueLeft) {
                    activateButton(pinkBallButton, dialogListener);
                }else if (brownLeft) {
                    activateButton(blueBallButton, dialogListener);
                }else if (greenLeft) {
                    Log.d("ADDDOPINT POPUP", "Metoda aktywowania brown buttona");
                    activateButton(brownBallButton, dialogListener);
                }else if (yellowLeft) {
                    activateButton(greenBallButton, dialogListener);
                }else{
                    activateButton(yellowBallButton, dialogListener);
                }





            }
        }



        addPointDialog.show();
    }
    public void showFoulPopup(View view){
        Log.d("MATCH ACTIVITY", "wejscie do metody showFoulPopup");

        final Dialog addFoulDialog = new Dialog(MatchActivity.this);
        addFoulDialog.setContentView(R.layout.foul_popup);

        ImageButton whiteBallButton = (ImageButton)addFoulDialog.findViewById(R.id.white_ball_foul);
        ImageButton blueBallButton = (ImageButton)addFoulDialog.findViewById(R.id.blue_ball_foul);
        ImageButton pinkBallButton = (ImageButton)addFoulDialog.findViewById(R.id.pink_ball_foul);
        ImageButton blackBallButton = (ImageButton)addFoulDialog.findViewById(R.id.black_ball_foul);

        View.OnClickListener dialogListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                Log.d("ADD_FOUL_POINT DIALOG", "nr przycisku " + viewId);

                switch (viewId){
                    case WHITE_FOUL:
                        updateFoulPoints(whoPlays, whiteFoulPoint);
                        nextPlayer();
                        break;
                    case BLUE_FOUL:
                        updateFoulPoints(whoPlays, blueFoulPoint);
                        nextPlayer();
                        break;
                    case PINK_FOUL:
                        updateFoulPoints(whoPlays, pinkFoulPoint);
                        nextPlayer();
                        break;
                    case BLACK_FOUL:
                        updateFoulPoints(whoPlays, blackFoulPoint);
                        nextPlayer();
                        break;
                }
                addFoulDialog.dismiss();
            }
        };
        whiteBallButton.setOnClickListener(dialogListener);
        blueBallButton.setOnClickListener(dialogListener);
        pinkBallButton.setOnClickListener(dialogListener);
        blackBallButton.setOnClickListener(dialogListener);

        addFoulDialog.setTitle(getApplicationContext().getResources().getString(R.string.addFoulPoints));
        addFoulDialog.show();
    }

    public void activateButton(ImageButton button, View.OnClickListener listener){
        button.setActivated(false);
        button.setAlpha(1.0f);
        button.setOnClickListener(listener);
    }
    public void deactivateButton(ImageButton button){
        button.setActivated(false);
        button.setAlpha(0.5f);
        button.setOnClickListener(null);
    }


    public void finishGame(View view){
        Log.d("MATCH ACTIVITY", "wejscie do metody finishGame");

        gameWinner = getWinner();
        if( gameWinner == null ){
            Log.d("MATCH ACTIVITY", "GAMEWINNER == NULL");
        }else{
            Log.d("MATCH ACTIVITY", "GAMEWINNER != NULL");
        }
        AlertDialog alertDialog = new AlertDialog.Builder(MatchActivity.this).create();
        alertDialog.setTitle(getApplicationContext().getResources().getString(R.string.gameOver));
        alertDialog.setMessage(getApplicationContext().getResources().getString(R.string.gameOver) + "\n"
                + gameWinner.getPlayerName() +" " + getApplicationContext().getResources().getString(R.string.hasWon)
                + "\n" + getApplicationContext().getResources().getString(R.string.resultAchieved)
                + " " + Integer.toString(gameWinner.getPoints()));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getApplicationContext().getResources().getString(R.string.okSaveScore),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveWinner(gameWinner);
                        saveLoosers();
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }
    public Player getWinner(){
        Player winner = players[0];

        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( players[i].getPoints() > winner.getPoints() ){
                winner = players[i];
            }
        }
        return winner;
    }
    public void saveLoosers(){

        Player p;
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( players[i] != getWinner() ){
                p = players[i];
                if(p.getPoints() > 0){
                    this.saveWinner(p);
                }
            }
        }
    }
    public void saveListToFile(ArrayList<Player> bestPlayers){
        //MainActivity.saveScore(players);
        String filename = RESULTS_FILE_NAME;
        FileOutputStream outputStream;
        OutputStreamWriter outputWriter;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputWriter = new OutputStreamWriter(outputStream);
            for(int i = 0; i < bestPlayers.size(); i++) {
                if(i < HIGHSCORES_PERSON_LIMIT){
                    String playerData = bestPlayers.get(i).getPlayerName() + "||" +bestPlayers.get(i).getPoints() + "\n";
                    outputWriter.write(playerData);
                }
            }
            outputWriter.close();
            outputStream.close();
            Log.d("MATCH ACTIVITY", "Pomyslnie zapisano zwyciezce!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.successfullySaved) + gameWinner.getPlayerName(), Toast.LENGTH_LONG).show();
    }
    public void saveWinner(Player winner){
        String filename = RESULTS_FILE_NAME;
        ArrayList<Player> bestPlayers = new ArrayList<>();
        File f = new File(filename);
        if(!f.exists()){
            try {
                f.createNewFile();
                Log.d("MATCH ACTIVITY", "Utworzylem plik");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("MATCH ACTIVITY", "Plik juz istnial");
        }

        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            String[] playerData;
            Player currentPlayer;
            //zapelnienie ArrayListy graczami
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line,"||");
                String name = st.nextToken();
                String pts = st.nextToken();
                currentPlayer = new Player();
                Log.d("MATCH ACTIVITY",name );
                Log.d("MATCH ACTIVITY", pts);
                currentPlayer.setPlayerName(name);
                currentPlayer.setPoints(Integer.valueOf(pts));
                bestPlayers.add(currentPlayer);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        //dodaje obecnego winnera do listy
        bestPlayers.add(winner);
        //sortowanie
        if(bestPlayers.size()>1) {
            Collections.sort(bestPlayers, new PlayerComparator());
        }
        saveListToFile(bestPlayers);
    }

    public void decrementRedBallCount(){
        redBallCount--;
        if( redBallCount < 0 ){
            redBallCount = 0;
        }
    }

    public void showTable(){
        Log.d("MATCH ACTIVITY", "wejscie do metody showTable");

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.table_pattern, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }


}
