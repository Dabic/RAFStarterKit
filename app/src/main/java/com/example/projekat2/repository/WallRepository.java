package com.example.projekat2.repository;

import com.example.projekat2.livedata.WallLiveData;
import com.example.projekat2.model.Message;
import com.example.projekat2.util.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WallRepository {
    private DatabaseReference databaseReference;
    private WallLiveData wallLiveData;


    public WallRepository() {
        FirebaseDatabase firebaseDatabase = Utils.getDatabase();
        databaseReference = firebaseDatabase.getReference("wall/");
        wallLiveData = new WallLiveData();
    }

    public WallLiveData getWallLiveData() {
        return wallLiveData;
    }

    public void addPost(Message message) {
        databaseReference.push().setValue(message);
    }
}
