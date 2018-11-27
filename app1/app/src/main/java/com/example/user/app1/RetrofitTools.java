package com.example.user.app1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTools {
    private static Retrofit instance;
    private static IMethods api;

    private static  Retrofit getretrofitInstance() {
        if(instance == null)
            return new Retrofit.Builder().baseUrl("https://hron.fei.tuke.sk/~km863qc/").addConverterFactory(GsonConverterFactory.create()).build();
        return instance;
    }

    public static IMethods getApi(){
        if(api == null)
            return getretrofitInstance().create(IMethods.class);
        else
            return api;
    }
}
