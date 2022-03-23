package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainChatView extends AppCompatActivity {

    private RecyclerView messages;
    private EditText messageText;

    private final int MESSAGE_TYPE_SENDER = 0;
    private final int MESSAGE_TYPE_RECEIVER = 1;

    private String chatName;

    private DatabaseReference chtRef = FirebaseDatabase.getInstance().getReference("Chats");
    private DatabaseReference chtNameRef = chtRef.child(chatName);

    private LinearLayoutManager linearLayoutManager;
    private List<User> partc;

    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_view);

        currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messages = findViewById(R.id.listMessages);
        Button sendMessage = findViewById(R.id.sendMessageButt);
        messageText = findViewById(R.id.messageText);

        linearLayoutManager = new LinearLayoutManager(this);
        messages.setLayoutManager(linearLayoutManager);
        messages.setHasFixedSize(true);
        //fetch(); //хз
        //MessageAdapter adap = new MessageAdapter(this);

        //List<Message> msg = new ArrayList<Message>();

        //messages.setAdapter(adap.addMessageTextView());

        /*sendMessage.setOnClickListener(view -> {
                    chtRef
                    .push()
                    .setValue(new ChatModel("DefaultChatName", partc, msg, msg.toString())
                    );

            // Clear the input
            messageText.setText("");
        });*/


    }

    private void readsChats(){

    }

}

