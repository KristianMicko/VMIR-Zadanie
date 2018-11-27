package com.example.user.apli9;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ZazitkyDao {
    @Query("SELECT * FROM Zazitky")
    LiveData<List<Zazitky>> getAll();

    @Query("SELECT * FROM Zazitky WHERE Id LIKE :Id")
    Zazitky getById(int Id);

    @Insert
    void insertMovies(Zazitky... zazitkies);

    @Delete
    void deleteMovie(Zazitky zazitky);
}
