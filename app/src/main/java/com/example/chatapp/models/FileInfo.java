package com.example.chatapp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class FileInfo {
    @SerializedName("file_dest")
    public String FileDestination;

    @SerializedName("user_uid")
    public String UserUid;

    @SerializedName("chat_id")
    public int ChatId;

    @JsonCreator
    public FileInfo(String dest, String userUid, int chatId) {
        FileDestination = dest;
        UserUid = userUid;
        ChatId = chatId;
    }
}
