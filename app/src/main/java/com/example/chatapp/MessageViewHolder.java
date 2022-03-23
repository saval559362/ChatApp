package com.example.chatapp;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView usName;
    public TextView usMsg;
    public CardView cView;


    public MessageViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.lineRoot);
        usName = itemView.findViewById(R.id.userName);
        usMsg = itemView.findViewById(R.id.userMessageText);
        cView = itemView.findViewById(R.id.messageCard);
    }

    public void setUserName(String string) {
        usName.setText(string);
    }

    public void setUserMessage(String string) {
        usMsg.setText(string);
    }

    public void setBackgroundColor (int color) {
        cView.setBackgroundColor(color);
    }
}
