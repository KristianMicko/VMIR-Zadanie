package com.example.user.apli9;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Zazitky.class},version = 2)
public abstract class ZazitkyDatabaza extends RoomDatabase {
    public abstract ZazitkyDao zazitkyDao();
    private static volatile ZazitkyDatabaza INSTANCE;

    static ZazitkyDatabaza getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ZazitkyDatabaza.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ZazitkyDatabaza.class, "zazitky.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
