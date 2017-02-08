package com.example.nogaz.snookercount;

import java.util.Comparator;

/**
 * Created by waldi on 2016-06-29.
 */
public class PlayerComparator implements Comparator<Player> {
    public int compare (Player player1, Player player2){
        if(player1.getPoints() < player2.getPoints()){
            return 1;
        }
        else if(player1.getPoints() == player2.getPoints()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
