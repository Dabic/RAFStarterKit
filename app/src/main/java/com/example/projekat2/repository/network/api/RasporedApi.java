package com.example.projekat2.repository.network.api;

import com.example.projekat2.repository.network.service.JsonPlaceHolder;
import com.example.projekat2.repository.network.service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;

public class RasporedApi {
    private JsonPlaceHolder mRasporedService;

    public RasporedApi() {
        mRasporedService = ServiceGenerator.createService(JsonPlaceHolder.class);
    }

    public Call<List<RasporedModel>> getRasporeds() {
        return mRasporedService.getRasporedFromWeb();
    }
}
