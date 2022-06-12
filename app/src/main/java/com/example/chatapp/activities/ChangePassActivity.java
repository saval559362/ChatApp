package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.tools.Crypto;
import com.example.chatapp.tools.JDBC;

import java.io.UnsupportedEncodingException;

public class ChangePassActivity extends AppCompatActivity implements JDBC.CallBackChangePass {

    private EditText currentPass;
    private EditText newPass;
    private EditText newPassEnd;
    private Button buttEndChangePass;

    private JDBC changeControl = new JDBC();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        currentPass = findViewById(R.id.currentPass);
        newPass = findViewById(R.id.newPass);
        newPassEnd = findViewById(R.id.newPassEnd);
        buttEndChangePass = findViewById(R.id.buttEndChangePass);

        changeControl.registerCallBackChangePass(this);

        //Завершение изменения пароля
        buttEndChangePass.setOnClickListener(view -> {
            SharedPreferences sPref =
                    getSharedPreferences(String.valueOf(R.string.app_settings),
                            Context.MODE_PRIVATE);
            String usPass = sPref.getString(String.valueOf(R.string.us_pass), "");
            String usUid = sPref.getString(String.valueOf(R.string.us_uid), "");
            String decrPass = "";
            Crypto cr = new Crypto();
            try {
                decrPass = cr.AESDecryption(usPass, usUid);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (currentPass.getText().toString().equals(decrPass)) {
                if (newPass.getText().toString().equals(newPassEnd.getText().toString())) {
                    if (!newPass.getText().toString().equals(currentPass.getText().toString())) {
                        changeControl.changeUserPass(usUid, newPass.getText().toString());
                    } else {
                        Toast.makeText(this, "Пароль не может быть таким же!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Пароли не совпадают!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Введенный пароль не совпадает с текущим!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void passChanged() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Пароль успешно изменен!", Toast.LENGTH_SHORT).show();
            this.finish();
        });
    }
}