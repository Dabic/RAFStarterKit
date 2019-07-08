package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekat2.livedata.MessagesLiveData;
import com.example.projekat2.model.Message;
import com.example.projekat2.model.User;
import com.example.projekat2.repository.AuthRepository;
import com.example.projekat2.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private ChatRepository chatRepository;
    public ChatViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        chatRepository = new ChatRepository();
    }
    public MessagesLiveData getMessageLiveData() {
        return chatRepository.getMessagesLiveData();
    }
    public void addMessage(Message message) {
        chatRepository.addMessage(message);
    }
    public User getLoggedInUser(){
        return authRepository.getLoggedUser();
    }
}
