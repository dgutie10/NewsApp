package com.example.android.newapp;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        
    }
}
