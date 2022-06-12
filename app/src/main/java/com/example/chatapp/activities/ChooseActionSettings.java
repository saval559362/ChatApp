package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.chatapp.R;

public class ChooseActionSettings extends AppCompatActivity {

    private RelativeLayout blockChangePass;
    private RelativeLayout blockChangeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action_settings);

        blockChangePass = findViewById(R.id.blockChangePass);
        blockChangeLogin = findViewById(R.id.blockChangeLogin);

        //Смена пароля
        blockChangePass.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangePassActivity.class);
            startActivity(intent);
        });
        //Смена логина
        blockChangeLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangeNameActivity.class);
            startActivity(intent);
        });
    }
}