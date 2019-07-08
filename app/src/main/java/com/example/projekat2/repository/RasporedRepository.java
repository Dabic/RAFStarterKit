package com.example.projekat2.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projekat2.model.Raspored;
import com.example.projekat2.repository.network.api.RasporedApi;
import com.example.projekat2.repository.network.api.RasporedModel;
import com.example.projekat2.repository.network.api.Resource;
import com.example.projekat2.repository.raspored_db.dao.RasporedDao;
import com.example.projekat2.repository.raspored_db.db.RasporedDatabase;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RasporedRepository {
    private static final String TAG = "RasporedRepository";

    private RasporedApi mRasporedApi;
    private RasporedDao mRasporedDao;
    private MutableLiveData<Resource<Void>> mResourceLiveData;
    private ExecutorService mExecutorService;

    public RasporedRepository(Application application) {
        mRasporedApi = new RasporedApi();
        mRasporedDao = RasporedDatabase.getDb(application).getRasporedDao();
        mResourceLiveData = new MutableLiveData<>();
        mExecutorService = Executors.newCachedThreadPool();
    }
    private void insertRasporeds(List<RasporedModel> rasporedApiModelList) {
        List<RasporedEntity> rasporedEntityList = transformApiModelToEntity(rasporedApiModelList);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                mRasporedDao.deleteAll();
                mRasporedDao.insertRasporeds(rasporedEntityList);
            }
        });
    }

    public LiveData<Resource<Void>> getRasporeds(){
        mRasporedApi.getRasporeds().enqueue(new Callback<List<RasporedModel>>() {
            @Override
            public void onResponse(Call<List<RasporedModel>> call, Response<List<RasporedModel>> response) {
                notifyResult(true);
                insertRasporeds(response.body());
            }

            @Override
            public void onFailure(Call<List<RasporedModel>> call, Throwable t) {
                notifyResult(false);
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
        return mResourceLiveData;
    }

    public LiveData<List<RasporedEntity>> getFilteredRasporeds(Raspored filter) {
        return mRasporedDao.getFilteredRasporeds(filter.getProfessor(), filter.getDay(), filter.getGroups());
    }

    public LiveData<List<RasporedEntity>> getFilteredRasporeds1(String filter) {
        return mRasporedDao.getFilteredRasporeds1(filter);
    }

    public LiveData<List<RasporedEntity>> getAllRasporeds() {
        return mRasporedDao.getAllRasporeds();
    }

    private void notifyResult(boolean isSuccessful){
        Resource<Void> resource = new Resource<>(null, isSuccessful);
        mResourceLiveData.setValue(resource);
    }

    private List<RasporedEntity> transformApiModelToEntity(List<RasporedModel> rasporedApiModelList) {
        List<RasporedEntity> rasporedEntityList = new ArrayList<>();

        for (RasporedModel rasporedApiModel: rasporedApiModelList) {
            String predmet = rasporedApiModel.getSubject();
            String tip = rasporedApiModel.getTip();
            String nastavnik = rasporedApiModel.getProfessor();
            String grupe = rasporedApiModel.getGroups();
            String  dan = rasporedApiModel.getDay();
            String termin = rasporedApiModel.getTime();
            String ucionica = rasporedApiModel.getClassRoom();

            RasporedEntity rasporedEntity = new RasporedEntity(predmet, tip, nastavnik, grupe, dan, termin, ucionica);
            rasporedEntityList.add(rasporedEntity);
        }
        return rasporedEntityList;
    }
}
