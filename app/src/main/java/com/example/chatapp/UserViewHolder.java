package com.example.chatapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileImage;
    public TextView userName;

    public UserViewHolder(View view){
        super(view);

        profileImage = view.findViewById(R.id.userImage);
        userName = view.findViewById(R.id.userName);
    }
}
