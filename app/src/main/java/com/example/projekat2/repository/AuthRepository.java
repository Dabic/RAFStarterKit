package com.example.projekat2.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projekat2.model.User;
import com.example.projekat2.model.UserResponse;
import com.example.projekat2.util.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthRepository {

    private static final String USERNAME_KEY = "userNameKey";
    private static final String INDEX_ID_KEY = "indexIdKey";

    private MutableLiveData<UserResponse> mUserLiveData;
    private MutableLiveData<UserResponse> mUserStoreLiveData;
    private SharedPreferences mSharedPreferences;

    private DatabaseReference mUsersDatabaseReference;

    public AuthRepository(Application application) {
        mUserLiveData = new MutableLiveData<>();
        mUserStoreLiveData = new MutableLiveData<>();

        String packageName = application.getPackageName();
        mSharedPreferences = application.getSharedPreferences(packageName, Context.MODE_PRIVATE);

        FirebaseDatabase firebaseDatabase = Utils.getDatabase();
        mUsersDatabaseReference = firebaseDatabase.getReference().child("users");
    }

    public LiveData<UserResponse> getUser() {
        String username = mSharedPreferences.getString(USERNAME_KEY, null);
        String indexId = mSharedPreferences.getString(INDEX_ID_KEY, null);

        UserResponse userResponse;
        if (username == null || indexId == null) {
            User user = new User("", "");
            userResponse = new UserResponse(user, false);
        } else {
            User user = new User(username, indexId);
            userResponse = new UserResponse(user, true);
        }

        mUserLiveData.setValue(userResponse);

        return mUserLiveData;
    }

    public User getLoggedUser(){
        String username = mSharedPreferences.getString(USERNAME_KEY, null);
        String indexId = mSharedPreferences.getString(INDEX_ID_KEY, null);
        User user = new User(indexId,username);
        return user;
    }

    public void storeUser(User user) {
        String username = user.getName();
        String indexId = user.getIndexId();

        mUsersDatabaseReference.child(indexId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString(USERNAME_KEY, username);
                        editor.putString(INDEX_ID_KEY, indexId);
                        editor.commit();

                        UserResponse userResponse = new UserResponse(user, true);
                        mUserStoreLiveData.setValue(userResponse);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        UserResponse userResponse = new UserResponse(user, false);
                        mUserStoreLiveData.setValue(userResponse);
                    }
                });
    }

    public LiveData<UserResponse> getUserStoreLiveData() {
        return mUserStoreLiveData;
    }

    public void clearUser() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}