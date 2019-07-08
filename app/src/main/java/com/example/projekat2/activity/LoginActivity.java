package com.example.projekat2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projekat2.R;
import com.example.projekat2.model.User;
import com.example.projekat2.model.UserResponse;
import com.example.projekat2.viewModel.LoginViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private String indexPattern = "[Rr][MmNn]-[0-9][0-9]-[0-9][0-9]";
    private EditText usernameEt;
    private EditText indexEt;
    private Button loginBtn;
    private LoginViewModel logInViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
    }

    private void init(){
        initUI();
        initViewModel();
    }

    private void initUI(){
        usernameEt = findViewById(R.id.login_username);
        indexEt = findViewById(R.id.login_index);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = usernameEt.getText().toString();
                String indexId = indexEt.getText().toString();

                Pattern pattern = Pattern.compile(indexPattern);
                Matcher matcher = pattern.matcher(indexId);
                boolean matches = matcher.matches();

                if (name.trim().isEmpty() || !matches) {
                    Toast.makeText(LoginActivity.this, "Username/Index not valid", Toast.LENGTH_LONG).show();
                } else {
                    loginBtn.setEnabled(false);
                    logInViewModel.logInUser(indexId, name);
                    Toast.makeText(LoginActivity.this, "Welcome " + usernameEt.getText().toString() + " !", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initViewModel(){
        logInViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        logInViewModel.getUserStoreLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse.isSuccessful()) {
                    Log.e(TAG, "onChanged: user stored in FireBase db and shared pref" + userResponse.getUser().toString());
                    // We can either send user info through intent, or observe UserStoreLiveData in MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    loginBtn.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
