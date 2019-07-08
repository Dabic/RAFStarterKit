package com.example.projekat2.model;

public class UserResponse {
    private User user;

    private boolean isSuccessful;

    public UserResponse(User user, boolean isSuccessful) {
        this.user = user;
        this.isSuccessful = isSuccessful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
