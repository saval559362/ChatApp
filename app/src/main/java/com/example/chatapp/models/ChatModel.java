package com.example.chatapp.models;


import java.util.List;

public class ChatModel {

    public String ChatName;
    public String LastMessage;
    private long LastMessageTime;
    public List<String> Participants;


    public ChatModel(String mchatName, List<String> participants,
                     long lastMessageTime, String lastMessage) {
        ChatName = mchatName;
        Participants = participants;
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

    public long getLastMessageTime() {
        return LastMessageTime;
    }

    public String getLastMessage() {
        return LastMessage;
    }

}

