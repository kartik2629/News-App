package com.kartik.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyInterface {
    @GET("v2/top-headlines")
    Call<NewsData> getTopHeadlines(@Query("country") String country, @Query("apiKey") String apiKey);
}
