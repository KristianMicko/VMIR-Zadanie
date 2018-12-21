package com.example.user.apli5;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IUploadAPI {
    @Multipart
    @POST("upload.php")
    Call<ResponseBody> file(@Part MultipartBody.Part file);


    @POST("skuska.php")
    @FormUrlEncoded
    Call<ResponseBody> string(@Field("file") String file);
}
