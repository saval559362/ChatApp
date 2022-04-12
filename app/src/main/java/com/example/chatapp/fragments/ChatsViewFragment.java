package com.example.chatapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.R;
import com.example.chatapp.activities.MainChatView;
import com.example.chatapp.adapters.ChatAdapter;
import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewFragment extends Fragment implements ChatAdapter.OnChatListener{

    private RecyclerView chatsListRecycler;
    private DatabaseReference chtRef = FirebaseDatabase.getInstance().getReference("Chats");
    private String TAG = "ChatsViewFragmentTag";
    private ChatAdapter msgAdapter;
    private List<ChatModel> chats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats_view, container, false);

        chatsListRecycler = view.findViewById(R.id.chatsListRec);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatsListRecycler.setLayoutManager(linearLayoutManager);
        chatsListRecycler.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        chats = new ArrayList<>();
        msgAdapter = new ChatAdapter(chats, this);
        chatsListRecycler.setAdapter(msgAdapter);

        readChats();
    }

    public void readChats(){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String chName = "";
                String lsMsg = "";
                DatabaseReference msgRef;
                List<String> partc = new ArrayList<>();
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    partc.clear();

                    chName = (String) childSnap.child("chatName").getValue();
                    lsMsg = (String) childSnap.child("lastMessage").getValue();
                    msgRef = childSnap.child("messages").getRef();

                    for (DataSnapshot part : childSnap.child("participiants").getChildren()){
                        partc.add(part.getValue(String.class));                             //TODO скорее всего также неверно получает
                    }
                    chats.add(new ChatModel(chName, partc, msgRef, lsMsg));
                    msgAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("-----ERROR-----", error.getMessage());
            }
        };
        chtRef.addValueEventListener(listener);
    }

    @Override
    public void onChatClick(int position) {
        ChatModel currentChat = chats.get(position);
        Intent intent = new Intent(getContext(), MainChatView.class);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String reciever = "";

        for (String part : currentChat.Participants) {
            if (!part.equals(sender)) {
                reciever = part;
            }
        }

        intent.putExtra("msgRef", currentChat.Messages.toString());
        intent.putExtra("receiver", reciever);
        startActivity(intent);

        Log.d("FROM FRAGMENT",currentChat.Messages.toString());
    }
}