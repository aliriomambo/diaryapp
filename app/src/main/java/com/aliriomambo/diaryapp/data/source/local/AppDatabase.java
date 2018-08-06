package com.aliriomambo.diaryapp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.aliriomambo.diaryapp.data.model.Entry;

/**
 * Created by Blue on 6/26/2018.
 */

@Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabaseSingleton;

    public static AppDatabase getInstance(Context context) {
        if (appDatabaseSingleton == null) {
            appDatabaseSingleton = Room.databaseBuilder(context.
                            getApplicationContext(), AppDatabase.class,
                    "diarydb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabaseSingleton;
    }

    public abstract EntryDao getEntryDao();

    private void destroyInstance() {
        appDatabaseSingleton = null;
    }

}

