package com.example.chatapp.models;


import androidx.databinding.ObservableList;

import java.util.List;

public class ChatModel {

    public int ChatId;
    public String Name;
    public String CreatorUid;
    public String[] Participants;


    public ChatModel(int chatId, String name, String creatorUid, String[] participants) {
        ChatId = chatId;
        Name = name;
        CreatorUid = creatorUid;
        Participants = participants;
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

}

