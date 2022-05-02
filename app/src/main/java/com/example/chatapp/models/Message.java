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

    public void setSender(String sender) {
        Sender = sender;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public void setMessageTime(long messageTime) {
        MessageTime = messageTime;
    }

    public void setIsseen(boolean isseen) {
        Isseen = isseen;
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
