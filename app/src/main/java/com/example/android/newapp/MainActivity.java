package com.example.android.newapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String GUARDIAN_NEWS_API = "http://content.guardianapis.com/search?q=debates&api-key=test";
    private NewsAdapter newsAdapter;
    private TextView emptyTextView;
    private NetworkInfo networkInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);

        emptyTextView =(TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyTextView);

        newsAdapter = new NewsAdapter(this,new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null,this);


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News current = newsAdapter.getItem(position);

                Uri newsUri = Uri.parse(current.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this,GUARDIAN_NEWS_API);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        View loadingIndicator = findViewById(R.id.loading_icon);
        loadingIndicator.setVisibility(View.GONE);

        if(networkInfo != null && networkInfo.isConnected() ){
            emptyTextView.setText(R.string.no_news);
        }else{
            emptyTextView.setText(R.string.no_connection);
        }

        newsAdapter.clear();
        if (data != null && !data.isEmpty()){
            newsAdapter.addAll(data);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
