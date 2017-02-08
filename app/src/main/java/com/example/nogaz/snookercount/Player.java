package com.example.nogaz.snookercount;

import android.view.View;

/**
 * Created by Nogaz on 16.06.2016.
 */
public class Player {
    int points;
    int playerNumber;
    String playerName;

    public Player(){
        this.points = 0;
    }
    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        this.points = 0;
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

}
