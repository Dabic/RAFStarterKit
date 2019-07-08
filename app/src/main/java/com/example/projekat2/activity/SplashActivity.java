package com.example.projekat2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projekat2.model.UserResponse;
import com.example.projekat2.viewModel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initViewModel();
    }

    private void initViewModel() {
        SplashViewModel viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        viewModel.getLoggedInUserLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse.isSuccessful()) {
                    Log.e(TAG, "onChanged: user is logged in " + userResponse.getUser().toString() + " start main activity");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "onChanged: user is not logged in, start LogIn activity");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
