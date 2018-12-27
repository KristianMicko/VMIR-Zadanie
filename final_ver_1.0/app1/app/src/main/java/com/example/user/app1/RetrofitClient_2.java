package com.example.user.app1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient_2 {
    private static Retrofit retrofitClient = null;
    public static Retrofit getClient(String baseUrl){
        if (retrofitClient == null){
            //retrofitClient = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create()).build();
            retrofitClient = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitClient;
    }
}
