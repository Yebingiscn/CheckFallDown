package com.example.checkfalldown;

public class SmsInfo {
    String sms_sender;
    String sms_body;
    String sms_time;

    public SmsInfo(String sms_sender, String sms_body, String sms_time) {
        this.sms_sender = sms_sender;
        this.sms_body = sms_body;
        this.sms_time = sms_time;
    }

    public String getSms_sender() {
        return sms_sender;
    }

    public void setSms_sender(String sms_sender) {
        this.sms_sender = sms_sender;
    }

    public String getSms_body() {
        return sms_body;
    }

    public void setSms_body(String sms_body) {
        this.sms_body = sms_body;
    }

    public String getSms_time() {
        return sms_time;
    }

    public void setSms_time(String sms_time) {
        this.sms_time = sms_time;
    }
}
