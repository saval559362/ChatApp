package com.example.chatapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.chatapp.R;
import com.example.chatapp.activities.MainActivity;
import com.example.chatapp.activities.MainChatView;
import com.example.chatapp.adapters.ChatAdapter;
import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ChatsViewFragment extends Fragment implements ChatAdapter.OnChatListener {

    private RecyclerView chatsListRecycler;
    private ChatAdapter msgAdapter;
    private List<ChatModel> chats;
    private String currUser;

    private RelativeLayout loadingSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats_view, container, false);

        chatsListRecycler = view.findViewById(R.id.chatsListRec);
        loadingSpinner = view.findViewById(R.id.loadingPanelChat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatsListRecycler.setLayoutManager(linearLayoutManager);
        chatsListRecycler.setHasFixedSize(true);

        /*currUser = Objects.requireNonNull(MainActivity.getFirebaseAuth().getCurrentUser()).getUid();                //FirebaseAuth.getInstance().getCurrentUser().getUid();
        chtRef = MainActivity.getFirebaseReference();*/
        //TODO получение текущего пользователя и списка чатов, где он участник

        Log.d("----CHATSVIEWFRAGMENT----", "onCreateView done");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        chats = new ArrayList<>();
        msgAdapter = new ChatAdapter(chats, this);
        chatsListRecycler.setAdapter(msgAdapter);

        readChats();
        Log.d("----CHATSVIEWFRAGMENT----", "onViewCreated done");
    }

    public void readChats(){

        /*chtRefListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                readChatsChanges(snapshot);
                loadingSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                readChatsChanges(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                chats.clear();
                msgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        chtRef.addChildEventListener(chtRefListener);*/
        //TODO Listener на чаты
    }

    private void readChatsChanges() {
        /*chats.clear();

        String chName = "";
        String lsMsg = "";
        DatabaseReference msgRef;
        long lastMsgTime;
        List<String> partc = new ArrayList<>();

        partc.clear();

        chName = (String) snapshot.child("chatName").getValue();
        lsMsg = (String) snapshot.child("lastMessage").getValue();
        msgRef = snapshot.child("messages").getRef();
        lastMsgTime = snapshot.child("lastMessageTime").getValue(long.class);

        for (DataSnapshot part : snapshot.child("paricipiants").getChildren()){
            partc.add(part.getValue(String.class));
        }

        if (partc.contains(currUser)) {
            chats.add(new ChatModel(chName, partc, msgRef, lastMsgTime, lsMsg));
        }

        msgAdapter.notifyDataSetChanged();
        Log.d("-----ChildEventListener-----", msgRef.toString());

        chats.sort(Comparator.comparingLong(ChatModel::getLastMessageTime));
        Collections.reverse(chats);*/
        //TODO разичные изменения чата
    }

    @Override
    public void onChatClick(int position) {
        /*ChatModel currentChat = chats.get(position);
        Intent intent = new Intent(getContext(), MainChatView.class);
        String sender = currUser;
        String reciever = "";

        for (String part : currentChat.Participants) {
            if (!part.equals(sender)) {
                reciever = part;
            }
        }

        intent.putExtra("msgRef", currentChat.Messages.toString());
        intent.putExtra("receiver", reciever);
        startActivity(intent);*/
        //TODO переход в выбранный чат
    }

    /*@Override
    public void onPause() {
        super.onPause();
        //chtRef.removeEventListener(chtRefListener);
    }*/
}