package com.example.nogaz.snookercount;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Console;

/**
 * Created by Nogaz on 16.06.2016.
 */
public class Player {
    int points;
    int playerNumber;
    String playerName;
    int playerBreak;


    RelativeLayout playerLayoutView;
    TextView playerScoreTextView;
    TextView playerBreakTextView;



    public Player(){
        this.points = 0;
    }
    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        this.points = 0;
        this.playerBreak = 0;

    }

    public int getPoints(){
        return this.points;
    }
    public void addPoints(int points){
        this.points += points;
    }
    public int getPlayerNumber(){
        return this.playerNumber;
    }
    public void setPlayerName(String name){ this.playerName = name;}
    public String getPlayerName(){ return this.playerName;}


    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString(){
        int nameDisplayDigitsLimit = 10;
        //String str = this.playerName + "\n\t\t\t\t\t" + this.points;
        //return str;
        //String str1 =  String.format("%.10s", this.playerName);
        String str1 = "";
        for(int i = 0; i < nameDisplayDigitsLimit; i++){
            if(i < this.playerName.length()){
                str1 += String.valueOf(this.playerName.charAt(i));
            }
            else{
                str1 += "_";
            }
        }
        //str1 = String.format("%-28s",str1+"\t");
        //String str2 =  String.format("\t\t\t\t\t%4d",this.points);
        return str1 + "                " + String.valueOf(this.points);
        //return String.format("%-15.15s:%4.4s",this.playerName,String.valueOf(this.points));
    }

    public void setActive(){
        playerLayoutView.setAlpha(1.0f);
    }
    public void setUnactive(){
        playerLayoutView.setAlpha(0.5f);
    }
    public void updatePlayerViewPoints(){
        Log.d("PLAYER", "Aktualizuje punkty!!! punkty: " + points);
        playerScoreTextView.setText(String.valueOf(points));
    }
    public void updatePlayerBreak(int newBreak){
        if( newBreak >= playerBreak ){
            playerBreak = newBreak;
            playerBreakTextView.setText(String.valueOf(playerBreak));
        }
    }
    public void updatePlayerBreak(){
        playerBreakTextView.setText(String.valueOf(playerBreak));
    }

    public TextView getPlayerScoreTextView() {
        return playerScoreTextView;
    }

    public void setPlayerScoreTextView(TextView playerScoreTextView) {
        this.playerScoreTextView = playerScoreTextView;
    }

    public TextView getPlayerBreakTextView() {
        return playerBreakTextView;
    }

    public void setPlayerBreakTextView(TextView playerBreak) {
        this.playerBreakTextView = playerBreak;
    }
    public RelativeLayout getPlayerLayoutView() {
        return playerLayoutView;
    }

    public void setPlayerLayoutView(RelativeLayout playerLayoutView) {
        this.playerLayoutView = playerLayoutView;
    }

}
