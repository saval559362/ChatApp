package com.example.chatapp.models;

import java.util.List;

public class ParticipiantsInfo {
    public List<String> Uids;
    public String DatabaseRef;
    public int PartCount;

    public ParticipiantsInfo(List<String> uids, String dRef) {
        Uids = uids;
        DatabaseRef = dRef;
        PartCount = uids.size();
    }


    public List<String> getUids() {
        return Uids;
    }

    public String getDatabaseRef() {
        return DatabaseRef;
    }

    public int getPartCount() {
        return PartCount;
    }
}
