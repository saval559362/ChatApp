package com.example.chatapp.tools;

import com.example.chatapp.models.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("file_control.php")
    Call<ResponseModel> fileUpload(
            @Part("file_info") RequestBody description,
            @Part MultipartBody.Part file);
}
