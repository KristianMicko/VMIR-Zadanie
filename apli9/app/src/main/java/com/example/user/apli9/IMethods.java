package com.example.user.apli9;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMethods {
    @GET("uvod.php")
    Call<JsonArray> getData(@Query("id") String id);


    //@GET("uvod.php")
    //Call<List<ZazitkyModel>> getJSON();
}
