package com.example.nogaz.snookercount;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADDPOINTID = 2131492992;
    public static final int ADDFOULPOINT = 2131492993;
    public static final int NEXTPLAYER = 2131492994;

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

    ImageButton buttonAddPoint;
    ImageButton buttonAddFoul;
    ImageButton buttonNextPlayer;

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

    public static final String WHO_PLAYS = "WHOPLAYS";
    public static final String IS_NEW_GAME = "ISNEWGAME";
    public static final String RED_BALL_COUNT = "REDBALLSCOUNT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MATCH ACTIVITY", "metoda onCreate");
        setContentView(R.layout.three_players_portrait_match_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String p1Name;
        String p2Name;
        String p3Name;
        if( bundle != null ){
            howMuchPlayers = bundle.getInt("PLAYERS");

        }
        if( howMuchPlayers == 2 ){
            setContentView(R.layout.two_players_portrait_match_layout);
        }else if( howMuchPlayers == 3 ){
            setContentView(R.layout.three_players_portrait_match_layout);
        }

        LayoutInflater vi = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup insertPoint = (ViewGroup)findViewById(R.id.playersCardHolder);
        players = new Player[howMuchPlayers];
        //views = new View[howMuchPlayers];
        //playerViews = new PlayerView[howMuchPlayers];
        for( int i = 0 ; i < howMuchPlayers ; ++i ){

            int tmp = i+1;
            players[i] = new Player(i);
            players[i].setPlayerName(bundle.getString("P_" + tmp + "_NAME"));
            //playerViews[i] = new PlayerView(getApplicationContext());

            String playerNickViewId = "nickname_player_" + String.valueOf(tmp);
            TextView tv = (TextView)findViewById(getResources().getIdentifier(playerNickViewId, "id", getPackageName()));
            tv.setText(players[i].getPlayerName());

            String playerLayoutStr = "scoretable_player_" + String.valueOf(tmp);
            players[i].setPlayerLayoutView((RelativeLayout)findViewById(getResources().getIdentifier(playerLayoutStr, "id", getPackageName())));
            String playerBreakTV = "break_player_" + String.valueOf(tmp);
            players[i].setPlayerBreakTextView((TextView)findViewById(getResources().getIdentifier(playerBreakTV, "id", getPackageName())));
            String playerScoreTV = "score_player_" + String.valueOf(tmp);
            players[i].setPlayerScoreTextView((TextView)findViewById(getResources().getIdentifier(playerScoreTV, "id", getPackageName())));
            Log.d("MATCH_ACTIVITY", "ustawiam pole o id: " + playerScoreTV);
            players[i].updatePlayerViewPoints();
            players[i].updatePlayerBreak(0);
        }

        buttonAddFoul = (ImageButton)findViewById(R.id.button_add_foul);
        buttonAddFoul.setOnClickListener(this);
        buttonAddPoint = (ImageButton)findViewById(R.id.button_add_point);
        buttonAddPoint.setOnClickListener(this);
        buttonNextPlayer = (ImageButton)findViewById(R.id.button_next_player);
        buttonNextPlayer.setOnClickListener(this);
        setActivePlayer();


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MATCH ACTIVITY", "metoda onPause");
        zapiszDaneGry();
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
        wczytajDaneGry();
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
        if( viewId == R.id.button_next_player ){
            nextPlayer();
            Log.d("CHANGE PLAYER", "kolej gracza nr " + whoPlays);
        }else if( viewId == R.id.button_add_point ){
            showAddPointPopup(v);
        }else if( viewId == R.id.button_add_foul ){
            showFoulPopup(v);
        }
    }
    public void setActivePlayer(){
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( i == whoPlays ){
                players[i].setActive();
//                views[i].setAlpha(1.0f);
//                views[i].setActivated(true);
//                views[i].findViewById(R.id.addPoint).setOnClickListener(this);
//                views[i].findViewById(R.id.nextPlayer).setOnClickListener(this);
//                views[i].findViewById(R.id.foulPoint).setOnClickListener(this);
            }else{
                players[i].setUnactive();
//                views[i].setAlpha(0.5f);
//                views[i].setActivated(false);
//                views[i].findViewById(R.id.addPoint).setOnClickListener(null);
//                views[i].findViewById(R.id.nextPlayer).setOnClickListener(null);
//                views[i].findViewById(R.id.foulPoint).setOnClickListener(null);
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
        players[player].updatePlayerViewPoints();
    }
    public void updateFoulPoints(int player, int points){
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            if( i != player ){
                players[i].addPoints(points);
                players[i].updatePlayerViewPoints();
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
                    case R.id.red_ball:
                        Log.d("ADD POINTS", "Punkty za bile czerwona +" + redBallPoint);
                        updatePlayerPoints(whoPlays, redBallPoint);
                        redBallCount--;
                        String buttonId = "remained_redBall_" + (15-redBallCount);
                        int redBallNumberID = getResources().getIdentifier(buttonId, "id", getPackageName());
                        ImageView redBallImgToDeactivate = (ImageView)findViewById(redBallNumberID);
                        redBallImgToDeactivate.setActivated(false);
                        redBallImgToDeactivate.setAlpha(0.5f);
                        colorBallMove = true;
                        break;
                    case R.id.yellow_ball:
                        Log.d("ADD POINTS", "Punkty za bile zolta +" + yellowBallPoint);
                        updatePlayerPoints(whoPlays, yellowBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            yellowLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case R.id.green_ball:
                        Log.d("ADD POINTS", "Punkty za bile zielona +" + greenBallPoint);
                        updatePlayerPoints(whoPlays, greenBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            greenLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case R.id.brown_ball:
                        Log.d("ADD POINTS", "Punkty za bile brazowa +" + brownBallPoint);
                        updatePlayerPoints(whoPlays, brownBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            brownLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case R.id.blue_ball:
                        Log.d("ADD POINTS", "Punkty za bile niebieska +" + blueBallPoint);
                        updatePlayerPoints(whoPlays, blueBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            blueLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case R.id.pink_ball:
                        Log.d("ADD POINTS", "Punkty za bile rozowa +" + pinkBallPoint);
                        updatePlayerPoints(whoPlays, pinkBallPoint);
                        if( redBallCount <= 0 && colorBallMove == false ){
                            pinkLeft = true;
                        }
                        colorBallMove = false;
                        break;
                    case R.id.black_ball:
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
                    case R.id.white_ball_foul:
                        updateFoulPoints(whoPlays, whiteFoulPoint);
                        nextPlayer();
                        break;
                    case R.id.blue_ball_foul:
                        updateFoulPoints(whoPlays, blueFoulPoint);
                        nextPlayer();
                        break;
                    case R.id.pink_ball_foul:
                        updateFoulPoints(whoPlays, pinkFoulPoint);
                        nextPlayer();
                        break;
                    case R.id.black_ball_foul:
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
                        saveScoresToDB();//save participants of a game to DB
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


    /*Saves scores of all players that participated in a game to the database. Caution: score must be greater than 0.*/
    public void saveScoresToDB(){
        Player p;
        DbManager dbManager = new DbManager(this);//passing context as argument to DbManager constructor
            for( int i = 0 ; i < howMuchPlayers ; ++i ){
                    p = players[i];
                    if(p.getPoints() > 0){
                        dbManager.insertEntry(p.getPlayerName(),p.getPoints());
                        Log.d("MATCH ACTIVITY",p.getPlayerName() + " " + p.getPoints() + " saved to a database.");
                    }
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MATCH ACTIVITY", "metoda onSaveInstanceState");
        outState.putInt(RED_BALL_COUNT, redBallCount);
        outState.putInt(WHO_PLAYS, whoPlays);
        int tmp;
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            tmp = i+1;
            outState.putInt("P" + tmp + "SCORE", players[i].getPoints());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MATCH ACTIVITY", "metoda onRestoreInstanceState");
        redBallCount = savedInstanceState.getInt(RED_BALL_COUNT, redBallCount);
        whoPlays = savedInstanceState.getInt(WHO_PLAYS, whoPlays);
        int tmp;
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            tmp = i+1;
            players[i].setPoints(savedInstanceState.getInt("P" + tmp + "SCORE", players[i].getPoints()));
            Log.d("MATCH ACTIVITY", "metoda onRestoreInstanceState,     GRACZ" + tmp + " pkt: " + players[i].getPoints());
        }
        wczytajDaneGry();

    }

    public void zapiszDaneGry(){
        Log.d("MATCH ACTIVITY", "metoda zapiszDaneGry");
        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            players[i].getPoints();
        }

    }
    public void wczytajDaneGry(){
        Log.d("MATCH ACTIVITY", "metoda wczytajDaneGry");

        for( int i = 0 ; i < howMuchPlayers ; ++i ){
            players[i].updatePlayerViewPoints();
            players[i].updatePlayerBreak();
        }
        setActivePlayer();
    }
}
