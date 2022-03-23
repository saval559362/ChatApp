package com.example.chatapp;

import java.util.ArrayList;

public class ChatRoot {

    public ChatRoot(){

    }

    private ArrayList<ChatModel> chats;

    public ArrayList<ChatModel> getChats() {
        return chats;
    }

    public void setChats(ArrayList<ChatModel> value) {
        this.chats = value;
    }
}
