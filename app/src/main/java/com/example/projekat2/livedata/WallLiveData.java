package com.example.projekat2.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projekat2.model.Message;
import com.example.projekat2.model.User;
import com.example.projekat2.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WallLiveData extends LiveData<List<Message>> {
    private ValueEventListener mValueEventListener;
    private DatabaseReference databaseReference;

    public WallLiveData() {
        super();
        initFirebase();
    }

    private void initFirebase() {
        FirebaseDatabase firebaseDatabase = Utils.getDatabase();
        databaseReference = firebaseDatabase.getReference().child("wall/");
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();

                if (dataSnapshot.getValue() == null) {
                    return;
                }

                for (DataSnapshot childDataSnapshot:dataSnapshot.getChildren()) {
                    Message message = childDataSnapshot.getValue(Message.class);
                    String key = childDataSnapshot.getKey();
                    message.setId(key);
                    User user = childDataSnapshot.child("sender").getValue(User.class);
                    String text = childDataSnapshot.child("text").getValue(String.class);
                    String time = childDataSnapshot.child("time").getValue(String.class);
                    int type = childDataSnapshot.child("type").getValue(Integer.class);
                    String uri = childDataSnapshot.child("uri").getValue(String.class);
                    message.setSender(user);
                    message.setTime(time);
                    message.setText(text);
                    message.setType(type);
                    message.setImage(uri);
                    messages.add(message);
                }

                setValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(mValueEventListener);

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(mValueEventListener);
    }
}
