package com.example.chatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewFragment extends Fragment {

    private RecyclerView chatsListRecycler;
    private DatabaseReference chtRef = FirebaseDatabase.getInstance().getReference("Chats");
    private String TAG = "ChatsViewFragmentTag";
    private MessageAdapter msgAdapter;
    private List<ChatModel> chats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats_view, container, false);

        //Чтение всех чатов с Firebase
        //TODO не считывает чаты
        /*FirebaseRecyclerOptions<ChatRoot> options =
                new FirebaseRecyclerOptions.Builder<ChatRoot>()
                        .setQuery(chtRef, ChatRoot.class)
                        .build();

        FirebaseRecyclerAdapter<ChatRoot, ChatsViewHolder> chatsAdapter = new FirebaseRecyclerAdapter<ChatRoot, ChatsViewHolder>(options) {
            @NonNull
            @Override
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        //TODO условие отображение чатов по участникам чата
                View view = LayoutInflater.from(getContext()).inflate(R.layout.chats_view_layout, parent, false);
                return new ChatsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatsViewHolder holder, int position, @NonNull ChatRoot model) {

                List<ChatModel> temp = new ArrayList<ChatModel>();
                temp = model.getChats();

                holder.setChatName(temp.toString());
                holder.setChatLastMsg("model.getLastMessage()");

            }

            @Override
            public int getItemViewType(int position){
                //TODO get view type
                return 0;
            }

        };

        chatsList.setAdapter(chatsAdapter);
        chatsAdapter.startListening();*/


        // Inflate the layout for this fragment
        return view;

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        Log.d("---RECYCLER INIT---", "RECYCLER INIT BEGIN");
        chatsListRecycler = view.findViewById(R.id.chatsListRec);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatsListRecycler.setLayoutManager(linearLayoutManager);
        chatsListRecycler.setHasFixedSize(true);

        chats = new ArrayList<>();
        msgAdapter = new MessageAdapter(getActivity(), chats);
        chatsListRecycler.setAdapter(msgAdapter);

        List<String> partc = new ArrayList<>();
        partc.add("Ddd");
        partc.add("Fff");
        List<Message> msgs = new ArrayList<>();
        msgs.add(new Message("sender", "reciever", "dsfsdf"));


        ChatModel chat = new ChatModel("String chatName", partc, msgs, "String lastMessage");
        chats.add(chat);
        msgAdapter.notifyDataSetChanged();
        //readChats();

    }

    public void readChats(){

        ValueEventListener listener = new ValueEventListener() {                 //TODO все вручную считывать(прописать считвание для каждого поля)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String chName = "";
                String lsMsg = "";
                List<Message> msgs = new ArrayList<>();
                List<String> partc = new ArrayList<>();
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    msgs.clear();
                    partc.clear();

                    chName = (String) childSnap.child("chatName").getValue();
                    lsMsg = (String) childSnap.child("lastMessage").getValue();
                    for (DataSnapshot msg : childSnap.child("messages").getChildren()){
                        msgs.add(new Message(msg.child("sender").getValue(String.class), msg.child("reciever").getValue(String.class),      //считывает другу. ноду
                                msg.child("messageText").getValue(String.class)));
                    }

                    for (DataSnapshot part : childSnap.child("participiants").getChildren()){
                        partc.add(part.getValue(String.class));
                    }
                    chats.add(new ChatModel(chName, partc, msgs, lsMsg));
                    msgAdapter.notifyDataSetChanged();
                    Log.d("---RECYCLER ADAPTER CHANGED---", "msgAdapter.notifyDataSetChanged() is work");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("-----ERROR-----", error.getMessage());
            }
        };
        chtRef.addValueEventListener(listener);

        /*chtRef.addValueEventListener(new ValueEventListener() {                 //TODO все вручную считывать(прописать считвание для каждого поля)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String chName = "";
                String lsMsg = "";
                List<Message> msgs = new ArrayList<>();
                List<String> partc = new ArrayList<>();
                for (DataSnapshot childSnap : snapshot.getChildren()){
                    msgs.clear();
                    partc.clear();

                    chName = (String) childSnap.child("chatName").getValue();
                    lsMsg = (String) childSnap.child("lastMessage").getValue();
                    for (DataSnapshot msg : childSnap.child("messages").getChildren()){
                        msgs.add(new Message(msg.child("sender").getValue(String.class), msg.child("reciever").getValue(String.class),      //считывает другу. ноду
                                msg.child("messageText").getValue(String.class)));
                    }

                    for (DataSnapshot part : childSnap.child("participiants").getChildren()){
                        partc.add(part.getValue(String.class));
                    }
                    chats.add(new ChatModel(chName, partc, msgs, lsMsg));
                    msgAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("-----ERROR-----", error.getMessage());
            }
        });*/
    }
}