package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.chatapp.JDBC;
import com.example.chatapp.R;
import com.example.chatapp.adapters.MessageAdapter;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MainChatView extends AppCompatActivity implements JDBC.CallBackReadMessages, JDBC.CallBackListenMsg{

    private RecyclerView messages;
    private EditText messageText;
    private FloatingActionButton sendMessage;

    private LinearLayoutManager linearLayoutManager;

    private ObservableList<Message> messagesList;
    private MessageAdapter msgAdapter;

    private String usUid;
    private String usReceiver = "null";
    private int partcCount = 0;

    private final JDBC msgControl = new JDBC();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_view);

        messages = findViewById(R.id.listMessages);
        sendMessage = findViewById(R.id.sendMessageButt);
        messageText = findViewById(R.id.messageText);

        linearLayoutManager = new LinearLayoutManager(this);
        messages.setLayoutManager(linearLayoutManager);
        messages.setHasFixedSize(true);

        partcCount = getIntent().getExtras().getInt("partc_count");
        int chatId = getIntent().getExtras().getInt("chat_id");

        SharedPreferences sPref =
                getSharedPreferences(String.valueOf(R.string.app_settings), MODE_PRIVATE);
        usUid = sPref.getString(String.valueOf(R.string.us_uid), "");

        messagesList = new ObservableArrayList<>();
        msgAdapter = new MessageAdapter(messagesList, usUid);
        messages.setAdapter(msgAdapter);

        messagesList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Message>>() {
            @Override
            public void onChanged(ObservableList<Message> sender) {
                msgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Message> sender, int positionStart, int itemCount) {
                msgAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Message> sender, int positionStart, int itemCount) {
                msgAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Message> sender, int fromPosition, int toPosition, int itemCount) {
                msgAdapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Message> sender, int positionStart, int itemCount) {
                msgAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });

        readMessages(chatId);

        //Listener на кнопку для отправки сообщений
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = new Date().getTime();

                Message msg = new Message(chatId, usUid, usReceiver, messageText.getText().toString(), time);
                msgControl.insertMessage(msg);
                msgControl.updateChat(chatId, messageText.getText().toString(), time);

                messageText.setText("");
            }
        });
    }

    //EventListener для считывания сообщений
    private void readMessages(int chatId){


        msgControl.registerCallBackReadMessages(this);
        msgControl.readMessages(chatId, usUid);

        msgControl.registerCallBackListenMsg(this);
        msgControl.listenMessages(chatId);
    }

    @Override
    public void onPause() {
        super.onPause();

        //TODO выгрузка Listener
    }

    @Override
    public void readMsg(List<Message> msgs) {
        messagesList.addAll(msgs);

        for (Message msg: messagesList) {
            if (!msg.getReceiver().equals(usUid) && usReceiver.equals("null")) {
                usReceiver = msg.getReceiver();
                break;
            }
        }
        if (messagesList.size() == 0) {
            messages.smoothScrollToPosition(messagesList.size());
        } else {
            messages.smoothScrollToPosition(messagesList.size() - 1);
        }

    }

    @Override
    public void beginListen(Message msg) {
        messagesList.add(msg);

        messages.smoothScrollToPosition(messagesList.size() - 1);
    }
}

