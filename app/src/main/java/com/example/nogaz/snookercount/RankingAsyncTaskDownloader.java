package com.example.nogaz.snookercount;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Nogaz on 14.02.2017.
 */

public class RankingAsyncTaskDownloader extends AsyncTask<URL, String, JSONObject> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(URL... params) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
