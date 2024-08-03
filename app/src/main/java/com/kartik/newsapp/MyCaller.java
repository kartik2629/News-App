package com.kartik.newsapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyCaller {
    private static final String BASE_URL = "https://newsapi.org/";
    private static MyCaller instance; // clas variable
    private final Retrofit retrofit; // retrofit class variable

    //here we are creating the constructor of MyCaller class and we have made the constructor
    // as private  so it can not be called outside the class
    private MyCaller() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    // now here we are crating a method of MyCaller class Type so this method will return the
    // object of the MyCaller class
    public static MyCaller getInstance() {
        if (instance == null) {
            instance = new MyCaller();
        }
        return instance;
    }

    public MyInterface getNewsApiInterface() {
        return retrofit.create(MyInterface.class);
    }
}
