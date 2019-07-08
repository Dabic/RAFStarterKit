package com.example.projekat2.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.projekat2.model.Raspored;
import com.example.projekat2.repository.RasporedRepository;
import com.example.projekat2.repository.network.api.Resource;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;

import java.util.List;

public class RasporedViewModel extends AndroidViewModel {

    private RasporedRepository mRasporedRepository;
    private MutableLiveData<Raspored> mFilterLiveData;
    private MutableLiveData<String> mFilterLiveData1;

    private LiveData<List<RasporedEntity>> mFilteredRasporedsLiveData;
    private LiveData<List<RasporedEntity>> mFilteredRasporedsLiveData1;


    public RasporedViewModel(@NonNull Application application) {
        super(application);
        mRasporedRepository = new RasporedRepository(application);
        mFilterLiveData = new MutableLiveData<>();
        mFilterLiveData1 = new MutableLiveData<>();

        mFilteredRasporedsLiveData = Transformations.switchMap(mFilterLiveData,
                new Function<Raspored, LiveData<List<RasporedEntity>>>() {
                    @Override
                    public LiveData<List<RasporedEntity>> apply(Raspored filter) {
                        return mRasporedRepository.getFilteredRasporeds(filter);
                    }
                });
        mFilteredRasporedsLiveData1 = Transformations.switchMap(mFilterLiveData1,
                new Function<String, LiveData<List<RasporedEntity>>>() {
                    @Override
                    public LiveData<List<RasporedEntity>> apply(String filter) {
                        return mRasporedRepository.getFilteredRasporeds1(filter);
                    }
                });

    }

    public LiveData<Resource<Void>> getRasporeds() {
        return  mRasporedRepository.getRasporeds();
    }

    public LiveData<List<RasporedEntity>> getFilteredRasporeds(){
        return mFilteredRasporedsLiveData;
    }
    public LiveData<List<RasporedEntity>> getFilteredRasporeds1(){
        return mFilteredRasporedsLiveData1;
    }
    public LiveData<List<RasporedEntity>> getAllRasporeds() {
        return mRasporedRepository.getAllRasporeds();
    }

    public void setFilter(Raspored filter) {
        mFilterLiveData.setValue(filter);
    }
    public void setFilter1(String filter) {
        mFilterLiveData1.setValue(filter);
    }


}
