package com.example.chatapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
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

                            profilePhoto.setImageDrawable(getRoundImage(selectedFilePath));
                            Log.d("UPLOAD_FILE", "Test message after upload begin");
                        }else {
                            Toast.makeText(this,"Cannot upload file to server",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        updateProfImg(usUid);

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

    private RoundedBitmapDrawable getRoundImage(String filePath){
        Bitmap batmapBitmap = BitmapFactory.decodeFile(filePath);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);
        circularBitmapDrawable.setCircular(true);

        return circularBitmapDrawable;
    }

    private void updateProfImg(String usUid) {
        RoundedBitmapDrawable roundImage;
        if (fc.getUserFile(usUid) != null) {
            roundImage = getRoundImage(fc.getUserFile(usUid).getPath());
            profilePhoto.setImageDrawable(roundImage);
        }
    }
}