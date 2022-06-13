package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.adapters.MultiAdapterContacts;
import com.example.chatapp.models.User;
import com.example.chatapp.tools.JDBC;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CreatingGroupChatActivity extends AppCompatActivity implements
        JDBC.CallBackUsers, JDBC.CallBackCreateGroupChat{

    private EditText groupChatName;
    private RecyclerView selectContacts;
    private FloatingActionButton createGroupChat;
    private MultiAdapterContacts multiAdapter;

    private List<User> listUsers = new ArrayList<>();
    JDBC usersControl = new JDBC();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_group_chat);

        groupChatName = findViewById(R.id.groupChatName);
        selectContacts = findViewById(R.id.selectContacts);
        createGroupChat = findViewById(R.id.createGroupChat);

        selectContacts.setLayoutManager(new LinearLayoutManager(this));

        multiAdapter = new MultiAdapterContacts(this, listUsers);
        selectContacts.setAdapter(multiAdapter);

        usersControl.registerCallBackUsers(this);
        usersControl.registerCallBackCreateGroupChat(this);

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings),
                        Context.MODE_PRIVATE);
        String usUid = sPref.getString(String.valueOf(R.string.us_uid), "");
        usersControl.getUsers(usUid, true);

        createGroupChat.setOnClickListener(view -> {
            if (!groupChatName.getText().toString().equals("")) {
                List<User> selectedUsers = multiAdapter.getSelectedUsers();
                String[] partc = new String[selectedUsers.size() + 1];
                partc[0] = usUid;
                if (selectedUsers.size() < 2 ) {
                    Toast.makeText(this, "Выберите больше 2-х участников!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 1; i < selectedUsers.size(); i++) {
                        partc[i] = selectedUsers.get(i).Uid;
                    }

                    usersControl.createGroupChat(usUid, partc, groupChatName.getText().toString());
                }

            } else {
                Toast.makeText(this, "Введите название!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void getUsers(List<User> users) {
        listUsers.addAll(users);
        runOnUiThread(() -> multiAdapter.notifyDataSetChanged());
    }

    @Override
    public void groupCreated() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Чат создан!", Toast.LENGTH_SHORT).show();
            this.finish();
        });
    }
}