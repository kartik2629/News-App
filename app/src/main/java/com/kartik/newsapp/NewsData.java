package com.kartik.newsapp;

import java.util.ArrayList;

public class NewsData {
    String status;
    int totalResults;
    ArrayList<MyModel> articles;

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return Integer.toString(totalResults);
    }

    public ArrayList<MyModel> getArticles() {
        return articles;
    }
}
