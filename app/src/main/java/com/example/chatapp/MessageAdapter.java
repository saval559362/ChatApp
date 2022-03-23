package com.example.chatapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatViewHolder> {

    private Context cContext;
    private List<ChatModel> chats;

    public MessageAdapter (Context mContext, List<ChatModel> chatsList){
        cContext = mContext;
        chats = chatsList;
    }

    @NonNull
    @Override
    public MessageAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cContext).inflate(R.layout.chats_view_layout, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ChatViewHolder holder, int position) {
        ChatModel chat = chats.get(position);

        holder.setChatName(chat.getChatName());
        holder.setChatLastMsg(chat.getLastMessage());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public LinearLayout chatInfo;
        public ImageView chatImage;
        public TextView chatName;
        public TextView chatLastMsg;

        public ChatViewHolder(View itemView){
            super(itemView);

            root = itemView.findViewById(R.id.root);
            chatInfo = itemView.findViewById(R.id.chatInfo);
            chatImage = itemView.findViewById(R.id.chatImage);
            chatName = itemView.findViewById(R.id.chatName);
            chatLastMsg = itemView.findViewById(R.id.lastChatMessageText);
        }

        public void setChatName(String name){
            chatName.setText(name);
        }

        public void setChatLastMsg(String lastMsg){
            chatLastMsg.setText(lastMsg);
        }

        public void setChatImage(){

        }

    }
}
