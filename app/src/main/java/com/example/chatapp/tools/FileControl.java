package com.example.chatapp.tools;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chatapp.models.FileInfo;
import com.example.chatapp.models.ResponseModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileControl {

    public void fileUpload(String filePath, FileInfo senderInfo) {

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        File file = new File(filePath);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadedfile",
                file.getName(), requestFile);

        Gson gson = new Gson();
        String patientData = gson.toJson(senderInfo);
        Log.d("GSON_STRING", patientData);

        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        // finally, execute the request
        Call<ResponseModel> call = apiInterface.fileUpload(description, body);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Log.d("RESPONSE ", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                /*Logger.d("Exception: " + t);
                EventBus.getDefault().post(new EventModel("response", t.getMessage()));*/
            }
        });
    }

    public void fileDownload(FileInfo getterInfo) {

        DownloadInterface downInterface =
                RetrofitApiClient.getClient().create(DownloadInterface.class);

        Gson gson = new Gson();
        String patientData = gson.toJson(getterInfo);
        Log.d("GSON_STRING", patientData);

        // finally, execute the request
        Call<ResponseBody> call = downInterface.downloadFile(patientData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d("RESPONSE_DOWNLOAD", response.message());

                ResponseBody respBody = response.body();
                String fullFileName = response.headers().value(4);


                boolean write = writeToDisk(respBody, getterInfo, fullFileName);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                /*Logger.d("Exception: " + t);
                EventBus.getDefault().post(new EventModel("response", t.getMessage()));*/
            }
        });
    }

    private boolean writeToDisk(ResponseBody body, FileInfo getterInfo, String fullFileName) {
        String fileExt = getFileExtension(fullFileName);

        try {
            File mediaStorageDir = null;

            if (getterInfo.FileDestination.equals("users")) {
                mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "Users/user_" + getterInfo.UserUid);
            }

            if (getterInfo.FileDestination.equals("chat_res")) {
                mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "ChatRes/chat_" + getterInfo.ChatId);
            }

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e("CREATE_FOLDER", "Oops! Failed create");
                }
            }

            File futureStudioIconFile = new File(mediaStorageDir, getterInfo.UserUid
                    + "." + fileExt);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("WRITING_ON_DISK", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private String getFileExtension(String file) {
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(file.lastIndexOf(".") != -1 && file.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return file.substring(file.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
}
