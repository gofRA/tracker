package com.example.rdanilov.tracker.controller;

import com.example.rdanilov.tracker.model.Entry;
import com.example.rdanilov.tracker.model.StringWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiController {

    @GET("/entry/test")
    Call<StringWrapper> testConnection();

    @POST("/entry")
    Call<Entry> saveEntry(@Body Entry e);
}
