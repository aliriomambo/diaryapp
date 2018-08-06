package com.aliriomambo.diaryapp.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aliriomambo.diaryapp.data.model.Entry;

import java.util.List;

/**
 * Created by Blue on 6/26/2018.
 */

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Entry> favoriteAlertList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Entry... entries);

    @Delete
    void delete(Entry entry);


    @Query("DELETE FROM entries WHERE entry_id = :mId")
    void deleteByEntryId(String mId);


    @Query("SELECT * FROM entries WHERE creator_id=:creatorId")
    LiveData<List<Entry>> getEntryListLiveData(String creatorId);

    @Query("SELECT * FROM entries")
    List<Entry> getEntryList();

    @Query("SELECT * FROM entries WHERE entry_id = :mId")
    Entry getEntryById(String mId);

}
