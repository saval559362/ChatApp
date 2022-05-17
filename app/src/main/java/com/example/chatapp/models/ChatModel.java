package com.example.chatapp.models;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ChatModel {

    public String ChatName;
    public String LastMessage;
    public DatabaseReference Messages;
    private long LastMessageTime;
    public List<String> Participants;


    public ChatModel(String mchatName, List<String> participants, DatabaseReference messages,
                     long lastMessageTime, String lastMessage) {
        ChatName = mchatName;
        Participants = participants;
        Messages = messages;
        LastMessageTime = lastMessageTime;
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

    public long getLastMessageTime() {
        return LastMessageTime;
    }

    public String getLastMessage() {
        return LastMessage;
    }

}

