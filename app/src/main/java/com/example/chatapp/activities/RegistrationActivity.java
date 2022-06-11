package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.tools.JDBC;
import com.example.chatapp.R;
import com.example.chatapp.models.User;

import java.util.UUID;

public class RegistrationActivity extends AppCompatActivity implements JDBC.CallBackCheck, JDBC.CallBackRegister {
    private EditText usEmail;
    private EditText usPass;
    private EditText nick;
    private EditText phone;
    private Button enReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usEmail = findViewById(R.id.user_email);
        usPass = findViewById(R.id.user_pass);
        nick = findViewById(R.id.user_nick);
        phone = findViewById(R.id.user_phone);
        enReg = findViewById(R.id.endReg);

        enReg.setOnClickListener(view -> {
            String emCheck = usEmail.getText().toString();
            String passCheck = usPass.getText().toString();

            if (!emCheck.contains("@") || emCheck.contains(" ") || !emCheck.contains(".")) {
                Toast.makeText(this, "Неверный email!", Toast.LENGTH_SHORT).show();
            } else if (passCheck.length() <= 5) {
                Toast.makeText(this, "Пароль должен быть больше 5 символов!",
                        Toast.LENGTH_SHORT).show();
            } else {
                registerUser(usEmail.getText().toString());
            }
        });

    }

    JDBC reg;

    public void registerUser(String email) {
        reg = new JDBC();
        reg.registerCallBackCheck(this::checkUs);
        reg.registerRegCallback(this::regUser);
        reg.checkUser(email);

    }

    @Override
    public void regUser(User user) {
        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings), MODE_PRIVATE);     //Сохранение текущего пользователя в SharedPreferences
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(String.valueOf(R.string.us_email), user.Email.replaceAll(" ", ""));
        ed.putString(String.valueOf(R.string.us_pass), user.Password.replaceAll(" ", ""));
        ed.putString(String.valueOf(R.string.us_uid), user.Uid);
        ed.apply();

        runOnUiThread(() -> Toast.makeText(this, "Registration complete!", Toast.LENGTH_SHORT).show());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void checkUs(String email, boolean exist) {
        if (exist) {
            runOnUiThread(() -> Toast.makeText(this,
                    "Пользователь с таким email уже существует!", Toast.LENGTH_SHORT).show());
            runOnUiThread(RegistrationActivity.this::finish);
        } else {
            String uid = UUID.randomUUID().toString();
            User us = new User(uid, nick.getText().toString(), phone.getText().toString(),
                    usEmail.getText().toString(), usPass.getText().toString());
            reg.registerUser(us);
        }
    }
}