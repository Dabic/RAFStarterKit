package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat2.model.UserResponse;
import com.example.projekat2.repository.AuthRepository;

public class SplashViewModel extends AndroidViewModel {
    private AuthRepository mAuthRepository;

    public SplashViewModel(Application application) {
        super(application);
        mAuthRepository = new AuthRepository(application);
    }

    public LiveData<UserResponse> getLoggedInUserLiveData() {
        return mAuthRepository.getUser();
    }
}
