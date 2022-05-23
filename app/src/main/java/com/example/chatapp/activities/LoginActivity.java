package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.JDBC;
import com.example.chatapp.R;
import com.example.chatapp.models.User;

import java.util.HashMap;
import java.util.Objects;


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
        /*FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            Toast.makeText(this, "You already sign in", Toast.LENGTH_SHORT).show();
            updateUi(currentUser);
        }*/

        //TODO Проверка логина пользователя
    }


    //TODO обработка непрвильного логина или пароля
    private void signIn(String email, String password) {
        JDBC logAct = new JDBC();
        logAct.registerCallback(this);
        logAct.logUser(email, password);

        //TODO Вход в систему
    }


    @Override
    public void logUser(boolean authComplete) {
        if (authComplete) {
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Auth complete!", Toast.LENGTH_SHORT).show());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Auth fail!", Toast.LENGTH_SHORT).show());
        }

    }
}