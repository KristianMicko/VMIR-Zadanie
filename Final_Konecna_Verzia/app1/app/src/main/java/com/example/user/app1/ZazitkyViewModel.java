package com.example.user.app1;

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

    public void  deleteAll(){
        mRepository.deleteAll();
    }

    public void update(Zazitky zazitky){
        mRepository.update(zazitky);
    }

    public void delete(Zazitky zazitky){
        mRepository.delete(zazitky);
    }

    public LiveData<List<Zazitky>> getGetAll() {
        return getAll;
    }

    public void insert(Zazitky zazitky){
        mRepository.insert(zazitky);
    }
}
