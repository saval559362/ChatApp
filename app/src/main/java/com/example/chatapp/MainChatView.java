package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class MainChatView extends AppCompatActivity {

    private ListView messages;
    private Button sendMessage;
    private EditText messageText;

    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("messages");
    private FirebaseListAdapter<Message> messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_view);

        messages = findViewById(R.id.listMessages);
        sendMessage = findViewById(R.id.sendMessageButt);
        messageText = findViewById(R.id.messageText);

        addMessageTextView();

        sendMessage.setOnClickListener(view -> {
                    mRef
                    .push()
                    .setValue(new Message(messageText.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getEmail())
                    );

            // Clear the input
            messageText.setText("");
        });

    }

    private void addMessageTextView() {

        FirebaseListOptions<Message> options =
                new FirebaseListOptions.Builder<Message>()
                        .setQuery(mRef, Message.class)
                        .setLayout(R.layout.message_layout)
                        .build();

        messageAdapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView msgUser = v.findViewById(R.id.userName);
                TextView msgText = v.findViewById(R.id.userMessageText);

                msgUser.setText(model.getAuthor());
                msgText.setText(model.getTextMessage());
            }
        };

        messages.setAdapter(messageAdapter);
        messageAdapter.startListening();
    }

}