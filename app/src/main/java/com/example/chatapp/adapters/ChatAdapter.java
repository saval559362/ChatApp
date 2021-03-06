package com.example.chatapp.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chatapp.models.ChatModel;
import com.example.chatapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_view_layout,
                parent, false);
        return new ChatViewHolder(view, mOnChatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        ChatModel chat = chats.get(position);

        long time = chat.getLastMessageTime();
        Date messageDate = new Date(time);
        Date todayDate = new Date(new Date().getTime());

        SimpleDateFormat dateFormater = new SimpleDateFormat("dd MM yyyy");
        SimpleDateFormat dateForCht;

        if (dateFormater.format(messageDate).equals(dateFormater.format(todayDate))) {
            dateForCht = new SimpleDateFormat("HH:mm");
        } else {
            dateForCht = new SimpleDateFormat("EEE, d MMM");
        }

        holder.setChatName(chat.getChatName());
        holder.setChatLastMsg(chat.getLastMessage());
        holder.setChatLastMsgTime(dateForCht.format(messageDate));

        DatabaseReference msgRefOnceRead = chat.Messages;           //TODO ?????????????? ?????????????????????????? ??????????????????

        msgRefOnceRead.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        public TextView chatLastMsgTime;
        public TextView unreadMessageCount;
        OnChatListener onChatListener;

        public ChatViewHolder(View itemView, OnChatListener onChatListener){
            super(itemView);

            root = itemView.findViewById(R.id.root);
            chatInfo = itemView.findViewById(R.id.chatInfo);
            chatImage = itemView.findViewById(R.id.chatImage);
            chatName = itemView.findViewById(R.id.chatName);
            chatLastMsg = itemView.findViewById(R.id.lastChatMessageText);
            chatLastMsgTime = itemView.findViewById(R.id.chatDateTime);
            unreadMessageCount = itemView.findViewById(R.id.messageCount);

            this.onChatListener = onChatListener;
            itemView.setOnClickListener(this);
        }

        public void setChatName(String name){
            chatName.setText(name);
        }

        public void setChatLastMsg(String lastMsg){
            chatLastMsg.setText(lastMsg);
        }

        public void setChatLastMsgTime (String time) {
            chatLastMsgTime.setText(time);
        }

        public void setChatImage(){

        }

        public void setUnreadMessageCount(int count) {
            unreadMessageCount.setText(count);
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
