package com.example.rdanilov.tracker.controller;

import com.example.rdanilov.tracker.model.Entry;
import com.example.rdanilov.tracker.model.StringWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestController {

    private ApiController buildApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.1.237:8080")
                .baseUrl("http://81.177.141.194:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiController.class);
    }

    public void makeTestConnection(Callback<StringWrapper> callback) {
        buildApi().testConnection().enqueue(callback);
    }

    public void sendEntry(Entry entry) {
        buildApi().saveEntry(entry).enqueue(new Callback<Entry>() {
            @Override
            public void onResponse(Call<Entry> call, Response<Entry> response) {
                Entry e = response.body();
                if (e != null) {
                    System.out.println("Сохранено");
                }
            }

            @Override
            public void onFailure(Call<Entry> call, Throwable t) {
                System.out.println("Ошибка");
                t.printStackTrace();
            }
        });
    }
}
