package com.example.nogaz.snookercount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by wald on 2017-02-08.
 */

/**
 * DbManager class performs operations on a SQLiteDatabase. Uses ScoreReaderDbHelper class.
 */
public class DbManager {
    private SQLiteDatabase db;
    ScoreReaderDbHelper dbhelper;
    Context context;

    /**
     * Constructor of DbManager class.
     * @param passedContext
     */
    public DbManager(Context passedContext  ){
        context = passedContext;
        dbhelper = new ScoreReaderDbHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    /**
     * Inserts record to the database
     * @param name Nickname of a player.
     * @param points Points scored by a player in a game.
     */
    public void insertEntry(String name, int points){
        ContentValues cv = new ContentValues();
        cv.put(ScoreReaderDbHelper.NAME,name);
        cv.put(ScoreReaderDbHelper.POINTS,points);
        db.insert(ScoreReaderDbHelper.TABLE_NAME,null,cv);
        Toast.makeText(context,"Successfully added to DB",Toast.LENGTH_LONG).show();
    }
    //Tells if deletion was successfull
    public void deleteEntry(String name, int points){
        db.execSQL("DELETE FROM " + dbhelper.TABLE_NAME + " WHERE " + dbhelper.NAME + " = '" + name + "' AND " + dbhelper.POINTS + " = '" + points + "'");
    }

    /**
     * Prints all records from the database to the console.
     */
    public void viewAllEntries(){
        Cursor resultOfQuery = db.query(dbhelper.TABLE_NAME, dbhelper.columns,null,null,null,null,null);
        resultOfQuery.moveToFirst();
        while(!resultOfQuery.isAfterLast()){
            String name = resultOfQuery.getString(1);
            int score = resultOfQuery.getInt(2);
            Log.d("DB","name: " + name + " Score: " + String.valueOf(score));
            resultOfQuery.moveToNext();
        }
        resultOfQuery.close();
    }

    /**
     * Method making query to the database and returining list of players sorted by given criteria.
     * @param howManyPlayers - maximum number of players returned by the query.
     * @return ArrayList of players queried from the database.
     */
    public ArrayList<Player> getPlayerResults(long howManyPlayers){
        ArrayList<Player> playersScores;//list storing players from database
        playersScores = new ArrayList<>();
        //Creating a query to the database. Caution: Ordering by string is on default case sensitive! It means Z is before a. To get rid of that add 'COLLATE NOCASE'
        //phrase where the sort by clause is situated. (if sorting alphabetically.)
        /*QUERY METHOD THAT RETURNS CURSOR AND ITS ARGUMENTS:
        Cursor query (
                String table,
                String[] columns,
                String selection,
                String[] selectionArgs,
                String groupBy,
                String having,
                String orderBy,
                String limit)
        */
        Cursor resultOfQuery = db.query(dbhelper.TABLE_NAME, dbhelper.columns,null,null,null,null,dbhelper.columns[2] + " COLLATE NOCASE DESC",String.valueOf(howManyPlayers));//order by 2 columny, with given limit passed as string
        resultOfQuery.moveToFirst();
        while(!resultOfQuery.isAfterLast()){//looping through query results and adding players to an ArrayList
            String name = resultOfQuery.getString(1);
            int score = resultOfQuery.getInt(2);
            Player tmpPlayer = new Player();
            tmpPlayer.setPlayerName(name);
            tmpPlayer.setPoints(score);
            playersScores.add(tmpPlayer);//adding tmpPlayer to a list
            resultOfQuery.moveToNext();
            Log.d("DbManager",tmpPlayer.getPlayerName() + " " + tmpPlayer.getPoints());
        }
        resultOfQuery.close();
        return playersScores;
    }
}
