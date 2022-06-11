package com.example.chatapp.tools;

import com.example.chatapp.models.ResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DownloadInterface {
    @GET("file_control.php")
    Call<ResponseBody> downloadFile(@Query("file_info") String fileName);
}
