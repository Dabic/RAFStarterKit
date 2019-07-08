package com.example.projekat2.repository.network.api;

public class Resource <T> {

    private T mData;
    private boolean mIsSuccessful;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public void setSuccessful(boolean successful) {
        mIsSuccessful = successful;
    }

    public Resource(T data, boolean isSuccessful) {
        mData = data;
        mIsSuccessful = isSuccessful;
    }
}
