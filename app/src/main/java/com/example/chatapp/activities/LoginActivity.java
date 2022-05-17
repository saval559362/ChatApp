package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;

import java.util.HashMap;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

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
            signIn(endEmail, endPass);});

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

    @Override
    protected void onResume()
    {
        super.onResume();

        receiveDataAndSignUp();
    }

    public void receiveDataAndSignUp()
    {

        /*Intent i = getIntent();
        String usEmail = i.getStringExtra("USER_EMAIL");
        String usPass = i.getStringExtra("USER_PASS");
        String usNick = i.getStringExtra("USER_NICK");
        String usPhone = i.getStringExtra("USER_NUMBER");
        if (usEmail == null && usPass == null){

        } else {
            signUp(usEmail, usPass, usNick, usPhone);
            Toast.makeText(this, "Registration complete", Toast.LENGTH_SHORT).show();
        }*/
        //TODO Прием данных из Activity RegistrationActivity
        //сохранение данных
    }
    //TODO обработка непрвильного логина или пароля
    private void signIn(String email, String password) {
        /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();
                            updateUi(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUi(null);
                        }
                    }
                });*/
        //TODO Вход в систему
    }

    private void updateUi()
    {
        //TODO реализация открытия главного окна
        /*if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {

        }*/

    }
}