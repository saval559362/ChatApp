package com.example.chatapp;

import java.util.Date;

public class Message {

    private String Sender;
    private String Receiver;
    private String MessageText;
    private long MessageTime;

    public Message(String sender, String receiver, String textMessage) {
        Sender = sender;
        Receiver = receiver;
        MessageText = textMessage;

        MessageTime = new Date().getTime();
    }

    public Message() {
    }

    public String getSender() {
        return Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public String getMessageText() {
        return MessageText;
    }

    public long getMessageTime() {
        return MessageTime;
    }



}
