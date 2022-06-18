package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.tools.JDBC;
import com.example.chatapp.R;


public class LoginActivity extends AppCompatActivity implements JDBC.CallBackLogin{

    private Button singIn;
    private Button singUp;
    private EditText userEmail;
    private EditText userPass;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        singIn = findViewById(R.id.sign_in_butt);
        singUp = findViewById(R.id.sign_up_butt);
        userEmail = findViewById(R.id.user_email_in);
        userPass = findViewById(R.id.user_pass_in);

        singIn.setOnClickListener(view -> {
            String endEmail = String.valueOf(userEmail.getText());
            String endPass = String.valueOf(userPass.getText());
            endEmail.replaceAll(" ", "");
            endPass.replaceAll(" ", "");
            
            signIn(endEmail, endPass);

            });

        singUp.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
            view.getContext().startActivity(intent);});

    }

    @Override
    public void onStart()
    {
        super.onStart();

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings), Context.MODE_PRIVATE);
        String usEmail = sPref.getString(String.valueOf(R.string.us_email), "");
        String usPass = sPref.getString(String.valueOf(R.string.us_pass), "");

        if (usEmail != null && usPass != null) {
            signIn(usEmail, usPass);
            //Toast.makeText(this, "Вы уж!", Toast.LENGTH_SHORT).show();
        }

    }

    private void signIn(String email, String password) {
        JDBC logAct = new JDBC(getString(R.string.ip_address));
        logAct.registerCallback(this);
        logAct.logUser(email, password);

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings),MODE_PRIVATE);     //Сохранение текущего пользователя в SharedPreferences
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(String.valueOf(R.string.us_email), email);
        ed.putString(String.valueOf(R.string.us_pass), password);
        ed.apply();
    }


    @Override
    public void logUser(boolean authComplete, String userUid) {
        if (authComplete) {

            SharedPreferences sPref = getSharedPreferences(String.valueOf(R.string.app_settings), MODE_PRIVATE);     //Сохранение текущего пользователя в SharedPreferences
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(String.valueOf(R.string.us_uid), userUid);
            ed.apply();

            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Авторизация успешна!",
                    Toast.LENGTH_SHORT).show());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Авторизация провалена!",
                    Toast.LENGTH_SHORT).show());
        }

    }
}