package com.example.chatapp.models;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ChatModel {

    public String ChatName;
    public String LastMessage;
    public DatabaseReference Messages;
    public List<String> Participants;


    public ChatModel(String chatName, List<String> participants, DatabaseReference messages, String lastMessage) {
        ChatName = chatName;
        Participants = participants;
        Messages = messages;
        LastMessage = lastMessage;
    }

    public ChatModel() {

    }

    public String getChatName() {
        return ChatName;
    }

    public List<String> getParticipants() {
        return Participants;
    }

    public DatabaseReference getMessages() {
        return Messages;
    }

    public String getLastMessage() {
        return LastMessage;
    }

}

