package com.example.rdanilov.tracker.model;

import java.util.Date;

public class Entry {

    private String serial;
    private Double latitude;
    private Double longitude;
    private Integer battery;
    private Date entryTime;

    public Entry() {}

    public Entry(String serial, Double latitude, Double longitude, Integer battery, Date entryTime) {
        this.serial = serial;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
        this.entryTime = entryTime;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}
