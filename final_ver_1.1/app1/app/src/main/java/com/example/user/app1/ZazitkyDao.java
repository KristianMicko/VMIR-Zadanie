package com.example.user.app1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ZazitkyDao {
    @Query("SELECT * FROM Zazitky")
    LiveData<List<Zazitky>> getAll();

    @Query("SELECT * FROM Zazitky WHERE Id LIKE :Id")
    Zazitky getById(int Id);

    @Insert
    void insertZazitky(Zazitky... zazitkies);

    @Delete
    void deleteZazitky(Zazitky zazitky);

    @Update
    void updateZazitky(Zazitky zazitky);

    @Query("Delete from Zazitky")
    void deleteAllZazitky();
}
