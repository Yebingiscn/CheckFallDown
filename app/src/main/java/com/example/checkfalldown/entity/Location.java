package com.example.checkfalldown.entity;

public class Location {
    public static final Location location = new Location();
    String LocationE;
    String LocationN;

    public Location() {
    }

    public Location(String locationE, String locationN) {
        LocationE = locationE;
        LocationN = locationN;
    }

    public static Location getLocation() {
        return location;
    }

    public String getLocationE() {
        return LocationE;
    }

    public void setLocationE(String locationE) {
        LocationE = locationE;
    }

    public String getLocationN() {
        return LocationN;
    }

    public void setLocationN(String locationN) {
        LocationN = locationN;
    }
}
