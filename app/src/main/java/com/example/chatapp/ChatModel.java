package com.example.chatapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {

    public String ChatName;
    public String LastMessage;
    public List<Message> Messages;
    public List<String> Participants;


    public ChatModel(String chatName, List<String> participants, List<Message> messages, String lastMessage) {
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

    public List<Message> getMessages() {
        return Messages;
    }

    public String getLastMessage() {
        return LastMessage;
    }

}

