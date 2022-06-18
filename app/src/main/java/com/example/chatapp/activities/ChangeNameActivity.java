package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.tools.JDBC;

public class ChangeNameActivity extends AppCompatActivity implements JDBC.CallBackChangeName {

    private EditText newUserName;
    private Button buttEndChangeName;
    private TextView currentUserName;

    private JDBC changeControl = new JDBC(getString(R.string.ip_address));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        newUserName = findViewById(R.id.newUserName);
        currentUserName = findViewById(R.id.currentUserName);
        buttEndChangeName = findViewById(R.id.buttEndChangeName);

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        String usUid = sPref.getString(String.valueOf(R.string.us_uid), "");
        String usName = sPref.getString(String.valueOf(R.string.us_name), "");
        currentUserName.setText(usName);

        changeControl.registerCallBackChangeName(this);

        buttEndChangeName.setOnClickListener(view -> {
            changeControl.changeUserName(usUid, newUserName.getText().toString());

            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(String.valueOf(R.string.us_name), newUserName.getText().toString());
            ed.apply();
        });
    }

    @Override
    public void nameChanged() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Логин успешно изменен!", Toast.LENGTH_SHORT).show();
            this.finish();
        });
    }
}