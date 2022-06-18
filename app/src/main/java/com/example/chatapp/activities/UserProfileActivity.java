package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.R;
import com.example.chatapp.models.User;
import com.example.chatapp.tools.FileControl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView userImage;
    private TextView userName;
    private TextView userEmail;
    private Button userSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImage = findViewById(R.id.userProfileAvatar);
        userName = findViewById(R.id.userProfileName);
        userEmail = findViewById(R.id.userProfileEmail);
        userSendMessage = findViewById(R.id.openUserChat);

        FileControl fc = new FileControl();

        String userJson = getIntent().getExtras().getString("user_info");
        User user = new User();
        if (userJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                user = mapper.readValue(userJson, User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        userName.setText(user.Name);
        userEmail.setText(user.Email);

        if (fc.getUserFile(user.Uid) != null) {
            RoundedBitmapDrawable roundImage;
            roundImage = getRoundImage(fc.getUserFile(user.Uid).getPath());
            userImage.setImageDrawable(roundImage);
        }
    }

    private RoundedBitmapDrawable getRoundImage(String filePath){
        Bitmap batmapBitmap = BitmapFactory.decodeFile(filePath);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);
        circularBitmapDrawable.setCircular(true);

        return circularBitmapDrawable;
    }
}