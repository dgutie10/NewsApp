package com.example.android.newapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

        try{
            JSONObject root = new JSONObject(newsString);
            JSONArray newsArray = root.getJSONObject("response").getJSONArray("results");
            for (int i=0; i < newsArray.length(); i++){
                JSONObject current = newsArray.getJSONObject(i);
                String date = current.getString("webPublicationDate");
                String title = current.getString("webTitle");
                String url = current.getString("webUrl");
                String section = current.getString("sectionName");

                newsList.add(new News(url,date,title,section));

            }

        }catch (JSONException e ){
            Log.e(LOG_TAG, "Problem Parsing JSON result");
        }

        return newsList;
    }

    public static List<News> getNewsData (String urlAPI){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        URL url = createUrl(urlAPI);
        String JSONResponse =  null;

        try{
            JSONResponse = makeHttpRequest(url);
        }catch (IOException e ){
            Log.e(LOG_TAG, "Problem making HTTP request");
        }

        List<News> newsList = extractNews(JSONResponse);
        return newsList;


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
