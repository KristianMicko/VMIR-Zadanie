package com.example.user.apli9;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ZazitkyViewModel extends AndroidViewModel {
    private ZazitkyRepository mRepository;
    private LiveData<List<Zazitky>> getAll;


    public ZazitkyViewModel(Application application) {
        super(application);
        mRepository = new ZazitkyRepository(application);
        getAll = mRepository.getGetAll();

    }


    public LiveData<List<Zazitky>> getGetAll() {
        return getAll;
    }

    public void insert(Zazitky zazitky){
        mRepository.insert(zazitky);
    }
}
