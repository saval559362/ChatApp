package com.example.chatapp.models;


import androidx.databinding.ObservableList;

import java.util.List;

public class ChatModel {

    public int ChatId;
    public String Name;
    public String CreatorUid;
    public String[] Participants;
    public String LastMessage;
    public long LastMessageTime;
    public int IsSeen;


    public ChatModel(int chatId, String name, String creatorUid, String[] participants,
                     String lastMessage, long lastMessageTime, int isSeen) {
        ChatId = chatId;
        Name = name;
        CreatorUid = creatorUid;
        Participants = participants;
        LastMessage = lastMessage;
        LastMessageTime = lastMessageTime;
        IsSeen = isSeen;
    }

    public ChatModel() {

    }

    public int getChatId() {
        return ChatId;
    }

    public void setChatId(int chatId) {
        ChatId = chatId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreatorUid() {
        return CreatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        CreatorUid = creatorUid;
    }

    public String[] getParticipants() {
        return Participants;
    }

    public void setParticipants(String[] participants) {
        Participants = participants;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public long getLastMessageTime() {
        return LastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        LastMessageTime = lastMessageTime;
    }

    public int getIsSeen() {
        return IsSeen;
    }

    public void setIsSeen(int isSeen) {
        IsSeen = isSeen;
    }

}

