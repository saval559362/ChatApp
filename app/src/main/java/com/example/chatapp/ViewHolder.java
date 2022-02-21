package com.example.chatapp;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView usName;
    public TextView usMsg;

    public ViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.lineRoot);
        usName = itemView.findViewById(R.id.userName);
        usMsg = itemView.findViewById(R.id.userMessageText);
    }

    public void setUserName(String string) {
        usName.setText(string);
    }


    public void setUserMessage(String string) {
        usMsg.setText(string);
    }
}
