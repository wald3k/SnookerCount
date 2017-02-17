package com.example.nogaz.snookercount.clientApi;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nogaz.snookercount.EventsListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nogaz on 14.02.2017.
 */

public class RankingAsyncTaskDownloader extends AsyncTask<URL, String, JSONArray> {

    EventsListActivity invokingActivity;


    String eventUrl = "http://api.snooker.org/?t=5&s=2016";

    JSONObject jsonObject = null;
    JSONArray jsonArray = null;


    HttpURLConnection urlConnection;

    public RankingAsyncTaskDownloader(EventsListActivity invokingActivity){
        this.invokingActivity = invokingActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        invokingActivity.showDialog(EventsListActivity.PLEASE_WAIT_DIALOG);
    }

    @Override
    protected JSONArray doInBackground(URL... params) {
        Log.d("RANKING_ASYNCTASK", "metoda doInBackground");
        StringBuilder resultStr = new StringBuilder();
        try{
            URL url = new URL(eventUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            //jsonObject = new JSONObject(urlConnection.getContent().toString());
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while( (line = reader.readLine()) != null ){
                resultStr.append(line);
                //Log.d("JSON ARRAY TEST", line + "\n");
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }

        try {
            //jsonObject = new JSONObject(resultStr.toString());
            //jsonArray = new JSONArray(resultStr.toString());
            JSONTokener jsonTokener = new JSONTokener(resultStr.toString());
            jsonArray = new JSONArray(jsonTokener);

        } catch (JSONException e) {

        }

        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonObject) {
        //super.onPostExecute(jsonObject);
        invokingActivity.removeDialog(EventsListActivity.PLEASE_WAIT_DIALOG);
        invokingActivity.setUpEventList(jsonArray);

    }
}
