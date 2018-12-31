package com.example.user.app1;

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

    public void delete(Zazitky zazitky){
        new deleteAsyncTask(mZazitkyDao).execute(zazitky);
    }

    public void update(Zazitky zazitky){
        new updateAsyncTask(mZazitkyDao).execute(zazitky);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mZazitkyDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Zazitky,Void,Void>{

        private ZazitkyDao mZazitkyDao;

        public insertAsyncTask(ZazitkyDao mZazitkyDao) {
            this.mZazitkyDao = mZazitkyDao;
        }

        @Override
        protected Void doInBackground(final Zazitky... zazitkies) {
            mZazitkyDao.insertZazitky(zazitkies[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Zazitky, Void, Void>{
        private ZazitkyDao mZazitkyDao;

        public deleteAsyncTask(ZazitkyDao mZazitkyDao) {
            this.mZazitkyDao = mZazitkyDao;
        }

        @Override
        protected Void doInBackground(Zazitky... zazitkies) {
            mZazitkyDao.deleteZazitky(zazitkies[0]);
            return null;
        }


    }

    public static class updateAsyncTask extends AsyncTask<Zazitky, Void,Void>{
        private ZazitkyDao mZazitkyDao;

        public updateAsyncTask(ZazitkyDao mZazitkyDao) {
            this.mZazitkyDao = mZazitkyDao;
        }

        @Override
        protected Void doInBackground(Zazitky... zazitkies) {
            mZazitkyDao.updateZazitky(zazitkies[0]);
            return null;
        }
    }

    public static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private ZazitkyDao mZazitkyDao;

        public deleteAllAsyncTask(ZazitkyDao mZazitkyDao) {
            this.mZazitkyDao = mZazitkyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mZazitkyDao.deleteAllZazitky();
            return null;
        }
    }

}
