package com.example.chatapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.models.FileInfo;
import com.example.chatapp.tools.FileControl;
import com.example.chatapp.tools.FilePath;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    private ImageView profilePhoto;

    private String selectedFilePath;

    private ActivityResultLauncher<Intent> getPictureActivityResultLauncher;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private FileControl fc;

    private TextView settingsUserName;
    private TextView settingsUserEmail;

    private RelativeLayout blockAccount;
    private RelativeLayout blockInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fc = new FileControl();

        profilePhoto = findViewById(R.id.changePhotoProfile);
        settingsUserName = findViewById(R.id.settingsUserName);
        settingsUserEmail = findViewById(R.id.settingsUserEmail);

        blockAccount = findViewById(R.id.blockAccount);
        blockInfo = findViewById(R.id.blockInfo);


        profilePhoto.setOnClickListener(view -> {
            showFileChooser();
        });
        //Выбор действия с аккаунтом
        blockAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChooseActionSettings.class);
            startActivity(intent);
        });
        //Информация о приложении
        blockInfo.setOnClickListener(view -> {

        });

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        String usUid = sPref.getString(String.valueOf(R.string.us_uid), "");

        Uri profImg = null;

        String[] info = new String[2];
        info[0] = sPref.getString(String.valueOf(R.string.us_name), "");
        info[1] = sPref.getString(String.valueOf(R.string.us_email), "");
        if (info[0] != null && info[1] != null) {
            settingsUserName.setText(info[0]);
            settingsUserEmail.setText(info[1]);
        }

        FileControl fc = new FileControl();
        getPictureActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        if(data == null){
                            //no data present
                            return;
                        }

                        Uri selectedFileUri = data.getData();
                        selectedFilePath = FilePath.getPathFromUri(this, selectedFileUri);
                        Log.i("FILE_CHOOSE","Selected File Path:" + selectedFilePath);

                        if(selectedFilePath != null && !selectedFilePath.equals("")){
                            Toast.makeText(this,"Загрузка файла",Toast.LENGTH_SHORT).show();

                            new Thread(() -> {
                                //creating new thread to handle Http Operations
                                FileInfo fi = new FileInfo("users", usUid, 0);
                                fc.fileUpload(String.valueOf(selectedFilePath), fi);

                            }).start();

                            profilePhoto.setImageURI(selectedFileUri);
                            Log.d("UPLOAD_FILE", "Test message after upload begin");
                        }else {
                            Toast.makeText(this,"Cannot upload file to server",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        if (fc.getUserFile(usUid) != null) {
            profImg = Uri.fromFile(fc.getUserFile(usUid));
        }

        if (profImg != null) {
            profilePhoto.setImageURI(profImg);
        } else {
            Toast.makeText(this, "Установите фото профиля",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void showFileChooser() {

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Нет разрешения на чтение файлов",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            //sets the select file to all types of files
            intent.setType("*/*");
            //allows to select data and return it
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //starts new activity to select file and return data
            getPictureActivityResultLauncher.launch(intent);
        }

    }


}