package com.example.nogaz.snookercount;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class PlayerView extends View {

    int points;
    int playerNumber;

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init() {
        //inflate(getContext(), R.layout.sample_player_view, this);
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

    public void showAddPointPopup(View view){
        Log.d("PLAYER VIEW", "wejscie do metody showAddPointPopup");
    }
    public void showFoulPopup(View view){
        Log.d("PLAYER VIEW", "wejscie do metody showFoulPopup");
    }
}
