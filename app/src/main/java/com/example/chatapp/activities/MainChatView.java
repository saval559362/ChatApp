package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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


public class MainChatView extends AppCompatActivity {

    private RecyclerView messages;
    private EditText messageText;
    private FloatingActionButton sendMessage;

    private LinearLayoutManager linearLayoutManager;

    private List<Message> messagesList;
    private MessageAdapter msgAdapter;

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

        String ref = getIntent().getExtras().getString("msgRef");
        String reciever = getIntent().getExtras().getString("receiver");

        //TODO инициализация базы

        messagesList = new ArrayList<>();
        msgAdapter = new MessageAdapter(messagesList);
        messages.setAdapter(msgAdapter);

        readMessages();

        //Listener на кнопку для отправки сообщений
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = new Date().getTime();
                /*Message msg = new Message(currUser, reciever, messageText.getText().toString(),
                        time, false);


                String ref = msgRef.getRef().toString().replace("/messages", "");
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
                mRef.child("lastMessage").setValue(messageText.getText().toString());
                mRef.child("lastMessageTime").setValue(time);

                msgRef.child(UUID.randomUUID().toString().replace('-','f')).setValue(msg);*/
                //TODO запись нового сообщения в базу
            }
        });
    }

    //EventListener для считывания сообщений
    private void readMessages(){
        /*msgListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    Message msg = snapshot.getValue(Message.class);
                    *//*messagesList.add(new Message(snapshot.child("sender").getValue(String.class),
                            snapshot.child("receiver").getValue(String.class),
                            snapshot.child("messageText").getValue(String.class),
                            snapshot.child("messageTime").getValue(Long.class),
                            snapshot.child("isseen").getValue(boolean.class)));*//*
                    messagesList.add(msg);

                    messagesList.sort(Comparator.comparingLong(Message::getMessageTime));
                    msgAdapter.notifyDataSetChanged();
                    messages.smoothScrollToPosition(messagesList.size() - 1);
                    messageText.setText("");

                    if (messagesList.get(messagesList.size() - 1).getReceiver().equals(currUser) &&
                            !messagesList.get(messagesList.size() - 1).getIsseen()) {
                        snapshot.getRef().child("isseen").setValue(true);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    messagesList.get(messagesList.size() - 1).setIsseen(true);
                    msgAdapter.notifyDataSetChanged();                                  //Проверяется последнее сообщение, костыль еще тот
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

        msgRef.addChildEventListener(msgListener);*/
        //TODO Listener на таблицу сообщений
    }

    @Override
    public void onPause() {
        super.onPause();
        //msgRef.removeEventListener(msgListener);
        //TODO выгрузка Listener
    }
    
}

