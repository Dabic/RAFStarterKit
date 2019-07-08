package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat2.model.User;
import com.example.projekat2.model.UserResponse;
import com.example.projekat2.repository.AuthRepository;
import com.google.firebase.database.annotations.NotNull;

public class LoginViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    public LoginViewModel(@NotNull Application application){
        super(application);
        authRepository = new AuthRepository(application);
    }
    public void logInUser(String indexId, String name){
        User user = new User(indexId, name);
        authRepository.storeUser(user);
    }
    public LiveData<UserResponse> getUserStoreLiveData() {
        return authRepository.getUserStoreLiveData();
    }
}
