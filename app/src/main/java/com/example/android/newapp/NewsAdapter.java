package com.example.android.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.android.newapp.QueryUtils.LOG_TAG;

/**
 * Created by diegog on 11/23/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News current = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(current.getTitle());

        TextView section = (TextView) convertView.findViewById(R.id.section);
        section.setText(current.getSection());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
        String newsDate = current.getDate();
        try {
            Date dt = dateFormat.parse(newsDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm zzz EEE dd MMMM yyyy");
            newsDate = newFormat.format(dt);

        }catch (ParseException e ){
            Log.e(LOG_TAG, "Kept Date format from API: "+e);
        }
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setText(newsDate);




        return convertView;
    }
}
