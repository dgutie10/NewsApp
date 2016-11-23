package com.example.android.newapp;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by diegog on 11/23/2016.
 */

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils() {
    }


    public static ArrayList<News>  extractNews (String newsString){
        if (TextUtils.isEmpty(newsString)) return null;

        ArrayList<News> newsList  = new ArrayList<>();

        return newsList
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{ url = new URL(stringUrl);}
        catch (MalformedURLException e ){
            Log.e(LOG_TAG, "Problem building url ",e);
        }
        return  url;
    }
    private static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Error retrieving data",e);
        }finally {
            if(urlConnection != null) urlConnection.disconnect();
            if(inputStream != null) inputStream.close();
        }
        return  jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null ){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
