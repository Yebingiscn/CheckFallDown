package com.example.checkfalldown.entity;

public class OldManInfo {
    String oldManName;
    String oldManStatus;
    String oldManLocation;
    String oldManContact;

    public OldManInfo(String oldManName, String oldManStatus, String oldManLocation, String oldManContact) {
        this.oldManName = oldManName;
        this.oldManStatus = oldManStatus;
        this.oldManLocation = oldManLocation;
        this.oldManContact = oldManContact;
    }

    public OldManInfo() {
    }

    public OldManInfo(String oldManName, String oldManContact) {
    }

    public String getOldManName() {
        return oldManName;
    }

    public String getOldManStatus() {
        return oldManStatus;
    }

    public String getOldManLocation() {
        return oldManLocation;
    }

    public String getOldManContact() {
        return oldManContact;
    }
}
