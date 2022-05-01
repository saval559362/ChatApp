package com.example.chatapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Message implements Parcelable {

    private String Sender;
    private String Receiver;
    private String MessageText;
    private long MessageTime;

    public Message(String sender, String receiver, String textMessage, long msgTime) {
        Sender = sender;
        Receiver = receiver;
        MessageText = textMessage;

        MessageTime = msgTime;
    }

    public Message() {

    }

    protected Message(Parcel in) {
        Sender = in.readString();
        Receiver = in.readString();
        MessageText = in.readString();
        MessageTime = in.readLong();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Sender);
        parcel.writeString(Receiver);
        parcel.writeString(MessageText);
        parcel.writeLong(MessageTime);
    }
}
