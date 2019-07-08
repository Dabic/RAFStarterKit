package com.example.projekat2.repository.network.service;

import com.example.projekat2.repository.network.api.RasporedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {
    @GET("json.php")
    public Call<List<RasporedModel>> getRasporedFromWeb();
}
