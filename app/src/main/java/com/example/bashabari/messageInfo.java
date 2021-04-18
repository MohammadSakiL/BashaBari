package com.example.bashabari;

public class messageInfo {
    private String sendto,owner_no,message,date,key;

    public messageInfo() {
    }

    public messageInfo(String sendto, String owner_no, String message, String date, String key) {
        this.sendto = sendto;
        this.owner_no = owner_no;
        this.message = message;
        this.date = date;
        this.key = key ;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getOwner_no() {
        return owner_no;
    }

    public void setOwner_no(String owner_no) {
        this.owner_no = owner_no;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
