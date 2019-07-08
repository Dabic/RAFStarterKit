package com.example.projekat2.model;

public class Message {
    public static final int SENT = 0;
    public static final int RECEIVED = 1;
    public static final int PHOTO = 2;
    public static final int TEXT = 3;

    private String id;
    private User sender;
    private User reciver;
    private String text;
    private String image;
    private int type;
    private String time;
    private String uri;

    public Message(){

    }
    public Message(User sender, User reciver){
        this.sender = sender;
        this.reciver = reciver;
    }
    public Message(User sender, User reciver, String text, String image, String time, int type){
        this.sender = sender;
        this.reciver = reciver;
        this.text = text;
        this.image = image;
        this.time = time;
        this.type = type;
    }
    public Message(String uri) {

        this.uri = uri;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static int getSENT() {
        return SENT;
    }

    public static int getRECEIVED() {
        return RECEIVED;
    }

    public static int getPHOTO() {
        return PHOTO;
    }

    public static int getTEXT() {
        return TEXT;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciver() {
        return reciver;
    }

    public void setReciver(User reciver) {
        this.reciver = reciver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public String getUri() {
        return uri;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
