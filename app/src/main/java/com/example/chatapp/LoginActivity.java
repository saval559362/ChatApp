package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private Button singIn;
    private Button singUp;
    private EditText userEmail;
    private EditText userPass;
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            Toast.makeText(this, "You already sign in", Toast.LENGTH_SHORT).show();
            updateUi(currentUser);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        receiveDataAndSignUp();
    }

    public void receiveDataAndSignUp()
    {

        Intent i = getIntent();
        String usEmail = i.getStringExtra("USER_EMAIL");
        String usPass = i.getStringExtra("USER_PASS");
        if (usEmail == null && usPass == null){

        } else {
            signUp(usEmail, usPass);
            Toast.makeText(this, "Registration complete", Toast.LENGTH_SHORT).show();
        }

        //сохранение данных
    }
    //TODO обработка непрвильного логина или пароля
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
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
                });
    }
    //TODO выводить ошибки неверного формата пароли и почты    task.getException()).toString
    private void signUp(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Registration complete",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(),
                                    Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUi(FirebaseUser user)
    {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {

        }

    }
}