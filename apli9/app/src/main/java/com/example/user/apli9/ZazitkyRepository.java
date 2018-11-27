package com.example.user.apli9;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ZazitkyRepository {
    private ZazitkyDao mZazitkyDao;
    private LiveData<List<Zazitky>> getAll;

    ZazitkyRepository(Application application){
        ZazitkyDatabaza db = ZazitkyDatabaza.getDatabase(application);
        mZazitkyDao = db.zazitkyDao();
        getAll = mZazitkyDao.getAll();
    }

    LiveData<List<Zazitky>> getGetAll(){
        return getAll;
    }

    public void insert(Zazitky zazitky){
        new insertAsyncTask(mZazitkyDao).execute(zazitky);
    }

    private static class insertAsyncTask extends AsyncTask<Zazitky,Void,Void>{

        private ZazitkyDao mZazitkyDao;

        public insertAsyncTask(ZazitkyDao mZazitkyDao) {
            this.mZazitkyDao = mZazitkyDao;
        }

        @Override
        protected Void doInBackground(final Zazitky... zazitkies) {
            mZazitkyDao.insertMovies(zazitkies[0]);
            return null;
        }
    }

}
