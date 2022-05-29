package com.example.chatapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("message_id")
    private int MessageId;
    @JsonProperty("chat_id")
    private int ChatId;
    @JsonProperty("sender")
    private String Sender;
    @JsonProperty("receiver_id")
    private String Receiver;
    @JsonProperty("content")
    private String MessageText;
    @JsonProperty("date_create")
    private long DateCreate;
    @JsonProperty("is_seen")
    private boolean Isseen;

    public Message(int messageId, int chatId, String sender, String receiver, String textMessage,
                   long msgTime, boolean isseen) {
        MessageId = messageId;
        ChatId = chatId;
        Sender = sender;
        Receiver = receiver;
        MessageText = textMessage;
        DateCreate = msgTime;
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

    public void setDateCreate(long DateCreate) {
        DateCreate = DateCreate;
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

    public long getDateCreate() {
        return DateCreate;
    }

    public boolean getIsseen() {return Isseen;}

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

}
