package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat2.model.UserResponse;
import com.example.projekat2.repository.AuthRepository;

public class MainViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }
    public void logOut() {
        authRepository.clearUser();
    }

    public LiveData<UserResponse> getUserStoreLiveData() {
        return authRepository.getUserStoreLiveData();
    }

}
