package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

public class RegistrationActivity extends AppCompatActivity {
    private EditText usEmail;
    private EditText usPass;
    private Button enReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usEmail = findViewById(R.id.user_email);
        usPass = findViewById(R.id.user_pass);
        enReg = findViewById(R.id.endReg);

        enReg.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            intent.putExtra("USER_EMAIL", usEmail.getText().toString());
            intent.putExtra("USER_PASS", usPass.getText().toString());
            startActivity(intent);
            Toast.makeText(this, "Data send!", Toast.LENGTH_SHORT).show();
            this.finish();
        });


    }
}