package com.example.projekat2.repository.network.api;

import com.google.gson.annotations.SerializedName;

public class RasporedModel {
    @SerializedName("predmet")
    private String subject;

    @SerializedName("ucionica")
    private String classRoom;

    @SerializedName("nastavnik")
    private String professor;

    @SerializedName("grupe")
    private String groups;

    @SerializedName("dan")
    private String day;

    @SerializedName("termin")
    private String time;

    @SerializedName("tip")
    private String tip;

    public String getSubject() {
        return subject;
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

    public String getTip() {
        return tip;
    }
}
