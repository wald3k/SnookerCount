package com.example.nogaz.snookercount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wald on 2017-02-08.
 */

/**
 * Class extending SQLiteOpenHelper needed for Database creation.
 */
public class ScoreReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScoreReader.db";
    final static String TABLE_NAME = "scores";
    final static String NAME = "name";
    final static String POINTS = "points";
    final static String _ID = "_id";
    final static String[] columns = {_ID, NAME, POINTS};
    private Context theContext;
    final static  String  CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + POINTS + " INTEGER NOT NULL)";

    public ScoreReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        theContext = context;
    }
    public void onCreate(SQLiteDatabase db) {
        //creating db
        db.execSQL(CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
    }
    /*Deleting database*/
    public void deleteDatabase(){
        theContext.deleteDatabase(DATABASE_NAME);
    }

}