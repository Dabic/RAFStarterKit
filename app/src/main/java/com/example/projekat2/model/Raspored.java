package com.example.projekat2.model;

import java.util.ArrayList;

public class Raspored {
    private String subject;
    private String classRoom;
    private String professor;
    private String groups;
    private String day;
    private String time;
    private String tip;
    private int id;

    public Raspored(String subject, String classRoom, String professor, String groups, String day, String time, String tip) {
        this.subject = subject;
        this.classRoom = classRoom;
        this.professor = professor;
        this.groups = groups;
        this.day = day;
        this.time = time;
        this.tip = tip;
    }

    public String getSubject() {
        return subject;
    }

    public int getId() {
        return id;
    }

    public String getTip() {
        return tip;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getProfessor() {
        return professor;
    }

    public String getGroups() {
        return groups;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
