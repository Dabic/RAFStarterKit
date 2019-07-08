package com.example.projekat2.repository;

import com.example.projekat2.livedata.MessagesLiveData;
import com.example.projekat2.model.Message;
import com.example.projekat2.util.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRepository {
    private DatabaseReference databaseReference;
    private MessagesLiveData messagesLiveData;


    public ChatRepository() {
        FirebaseDatabase firebaseDatabase = Utils.getDatabase();
        databaseReference = firebaseDatabase.getReference("messages");
        messagesLiveData = new MessagesLiveData();
    }

    public MessagesLiveData getMessagesLiveData() {
        return messagesLiveData;
    }

    public void addMessage(Message message) {
        databaseReference.push().setValue(message);
    }
}
