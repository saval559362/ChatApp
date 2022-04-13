package com.example.chatapp.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chatapp.models.ChatModel;
import com.example.chatapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatModel> chats;
    private OnChatListener mOnChatListener;

    public ChatAdapter(List<ChatModel> chatsList, OnChatListener onChatListener){
        chats = chatsList;
        this.mOnChatListener = onChatListener;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_view_layout, parent, false);
        return new ChatViewHolder(view, mOnChatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        ChatModel chat = chats.get(position);

        holder.setChatName(chat.getChatName());
        holder.setChatLastMsg(chat.getLastMessage());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{

        public RelativeLayout root;
        public LinearLayout chatInfo;
        public ImageView chatImage;
        public TextView chatName;
        public TextView chatLastMsg;
        OnChatListener onChatListener;

        public ChatViewHolder(View itemView, OnChatListener onChatListener){
            super(itemView);

            root = itemView.findViewById(R.id.root);
            chatInfo = itemView.findViewById(R.id.chatInfo);
            chatImage = itemView.findViewById(R.id.chatImage);
            chatName = itemView.findViewById(R.id.chatName);
            chatLastMsg = itemView.findViewById(R.id.lastChatMessageText);

            this.onChatListener = onChatListener;
            itemView.setOnClickListener(this);
        }

        public void setChatName(String name){
            chatName.setText(name);
        }

        public void setChatLastMsg(String lastMsg){
            chatLastMsg.setText(lastMsg);
        }

        public void setChatImage(){

        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClick(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public interface OnChatListener {
        void onChatClick(int position);
    }
}
