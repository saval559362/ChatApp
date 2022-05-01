package com.example.chatapp.models;

public class Message {

    private String Sender;
    private String Receiver;
    private String MessageText;
    private long MessageTime;
    private boolean Isseen;

    public Message(String sender, String receiver, String textMessage, long msgTime, boolean isseen) {
        Sender = sender;
        Receiver = receiver;
        MessageText = textMessage;
        MessageTime = msgTime;
        Isseen = isseen;
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

    public boolean getIsseen() {return Isseen;}

}
