package com.example.android.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(current.getDate());




        return super.getView(position, convertView, parent);
    }
}
