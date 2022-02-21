package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class MainChatView extends AppCompatActivity {

    private RecyclerView messages;
    private Button sendMessage;
    private EditText messageText;

    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("messages");
    private FirebaseRecyclerAdapter messageAdapter;

    private LinearLayoutManager linearLayoutManager;

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
        //fetch(); //хз

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

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(mRef, Message.class)
                        .build();

        messageAdapter = new FirebaseRecyclerAdapter<Message, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(MainChatView.this).inflate(R.layout.message_layout, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Message model) {
                holder.setUserName(model.getAuthor());
                holder.setUserMessage(model.getTextMessage());
            }

        };

        messages.setAdapter(messageAdapter);
        messageAdapter.startListening();
    }

}

