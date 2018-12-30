package com.example.user.app1;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IUploadAPI {
    /*@Multipart
    @POST("upload.php")
    Call<String> file(@Part MultipartBody.Part file);*/

    @Multipart
    @POST("upload.php")
    Call<ResponseBody> file(@Part MultipartBody.Part file);
}
