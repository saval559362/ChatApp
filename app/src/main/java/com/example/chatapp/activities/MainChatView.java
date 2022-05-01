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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.type.DateTime;

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

    private LinearLayoutManager linearLayoutManager;

    private DatabaseReference msgRef;
    private List<Message> messagesList;
    private String currUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private MessageAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_view);

        messages = findViewById(R.id.listMessages);
        Button sendMessage = findViewById(R.id.sendMessageButt);
        messageText = findViewById(R.id.messageText);

        linearLayoutManager = new LinearLayoutManager(this);
        messages.setLayoutManager(linearLayoutManager);
        messages.setHasFixedSize(true);

        String ref = getIntent().getExtras().getString("msgRef");
        String reciever = getIntent().getExtras().getString("receiver");

        msgRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);

        messagesList = new ArrayList<>();
        msgAdapter = new MessageAdapter(messagesList);
        messages.setAdapter(msgAdapter);

        readMessages();

        //Listener на кнопку для отправки сообщений
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = new Date().getTime();
                Message msg = new Message(currUser, reciever, messageText.getText().toString(),
                        time, false);


                String ref = msgRef.getRef().toString().replace("/messages", "");
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
                mRef.child("lastMessage").setValue(messageText.getText().toString());
                mRef.child("lastMessageTime").setValue(time);

                msgRef.child(UUID.randomUUID().toString().replace('-','f')).setValue(msg);
            }
        });
    }

    //EventListener для считывания сообщений
    private void readMessages(){
        msgRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    messagesList.add(new Message(snapshot.child("sender").getValue(String.class),
                            snapshot.child("receiver").getValue(String.class),
                            snapshot.child("messageText").getValue(String.class),
                            snapshot.child("messageTime").getValue(Long.class),
                            snapshot.child("isseen").getValue(boolean.class)));

                    messagesList.sort(Comparator.comparingLong(Message::getMessageTime));
                    msgAdapter.notifyDataSetChanged();
                    messages.scrollToPosition(messagesList.size() - 1);
                    messageText.setText("");

                    if (messagesList.get(messagesList.size() - 1).getReceiver().equals(currUser)) {
                        snapshot.getRef().child("isseen").setValue(true);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
            });
    }
    
}

