package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.projekat2.livedata.WallLiveData;
import com.example.projekat2.model.Message;
import com.example.projekat2.model.User;
import com.example.projekat2.repository.AuthRepository;
import com.example.projekat2.repository.WallRepository;

public class WallViewModel extends AndroidViewModel {
    private WallRepository wallRepository;
    private AuthRepository mAuthRepository;

    public WallViewModel(Application application) {
        super(application);
        wallRepository = new WallRepository();
        mAuthRepository = new AuthRepository(application);

    }

    public WallLiveData getMessageLiveData() {
        return wallRepository.getWallLiveData();
    }
    public User getLoggedInUser(){
        return mAuthRepository.getLoggedUser();
    }
    public void addMessage(Message message) {
        wallRepository.addPost(message);
    }
}
