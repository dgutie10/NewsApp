package com.example.android.newapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by diegog on 11/23/2016.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<News> loadInBackground() {
        if (url == null ) return  null;

        List<News> results = QueryUtils.getNewsData(url);
        return results;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
