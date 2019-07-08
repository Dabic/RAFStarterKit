package com.example.projekat2.repository.raspored_db.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "raspored")
public class RasporedEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "predmet")
    private String mPredmet;

    @ColumnInfo(name = "tip")
    private String mTip;

    @ColumnInfo(name = "nastavnik")
    private String mNastavnik;

    @ColumnInfo(name = "grupe")
    private String mGrupe;

    @ColumnInfo(name = "dan")
    private String mDan;

    @ColumnInfo(name = "termin")
    private String mTermin;

    @ColumnInfo(name = "ucionica")
    private String mUcionica;


    public RasporedEntity(@NonNull String mPredmet, String mTip, String mNastavnik, String mGrupe, String mDan, String mTermin, String mUcionica) {
        this.mPredmet = mPredmet;
        this.mTip = mTip;
        this.mNastavnik = mNastavnik;
        this.mGrupe = mGrupe;
        this.mDan = mDan;
        this.mTermin = mTermin;
        this.mUcionica = mUcionica;
    }

    @NonNull
    public String getPredmet() {
        return mPredmet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPredmet(@NonNull String mPredmet) {
        this.mPredmet = mPredmet;
    }

    public String getTip() {
        return mTip;
    }

    public void setTip(String mTip) {
        this.mTip = mTip;
    }

    public String getNastavnik() {
        return mNastavnik;
    }

    public void setNastavnik(String mNastavnik) {
        this.mNastavnik = mNastavnik;
    }

    public String getGrupe() {
        return mGrupe;
    }

    public void setGrupe(String mGrupe) {
        this.mGrupe = mGrupe;
    }

    public String getDan() {
        return mDan;
    }

    public void setDan(String mDan) {
        this.mDan = mDan;
    }

    public String getTermin() {
        return mTermin;
    }

    public void setTermin(String mTermin) {
        this.mTermin = mTermin;
    }

    public String getUcionica() {
        return mUcionica;
    }

    public void setUcionica(String mUcionica) {
        this.mUcionica = mUcionica;
    }

    @Override
    public String toString() {
        return "RasporedEntity{" +
                "mPredmet=" + mPredmet +
                ", mTip='" + mTip + '\'' +
                ", mNastavnik='" + mNastavnik + '\'' +
                ", mDan='" + mDan + '\'' +
                ", mUcionica='" + mUcionica + '\'' +
                ", mGrupe='" + mGrupe + '\'' +
                ", mTermin='" + mTermin + '\'' +
                '}';
    }
}

