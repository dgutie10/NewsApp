package com.example.android.newapp;

/**
 * Created by diegog on 11/23/2016.
 */

public class News {
    private String url;
    private String date;
    private String title;
    private String section;

    public News(String url, String date, String title, String section) {
        this.url = url;
        this.date = date;
        this.title = title;
        this.section = section;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }
}
