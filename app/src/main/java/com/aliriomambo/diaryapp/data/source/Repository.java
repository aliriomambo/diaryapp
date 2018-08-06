package com.aliriomambo.diaryapp.data.source;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.local.AppDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Blue on 6/26/2018.
 */

public class Repository {
    private AppDatabase appDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference mEntriesRef;
    private FirebaseUser mFirebaseUser;

    public Repository(Context context) {
        Context mContext = context;
        this.appDatabase = AppDatabase.getInstance(mContext);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mEntriesRef = mDatabase.child("entries");
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void synchronize() {

        final ArrayList<Entry> entryArrayList = new ArrayList<>();
        mEntriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                while (it.hasNext()) {
                    Entry entry = it.next().getValue(Entry.class);
                    if (entry.getCreatorId().equals(mFirebaseUser.getUid())) {
                        entryArrayList.add(entry);
                    }
                }
                appDatabase.getEntryDao().insertAll(entryArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void addEntry(Entry entry) {
        String key = mEntriesRef.push().getKey();
        entry.setId(key);
        entry.setDateTime(getCurrentLocalDateTimeStamp());
        mEntriesRef.child(key).setValue(entry);
    }

    public void updateEntry(Entry entry) {
        entry.setDateTime(getCurrentLocalDateTimeStamp());
        mEntriesRef.child(entry.getId()).setValue(entry);
    }

    public LiveData<List<Entry>> getEntryListLiveData(String creatorId) {
        return appDatabase.getEntryDao().getEntryListLiveData(creatorId);
    }


    public Entry getEntryById(String id) {
        return appDatabase.getEntryDao().getEntryById(id);
    }


    public void removeEntry(Entry entry) {
        entry.setDateTime(getCurrentLocalDateTimeStamp());
        mEntriesRef.child(entry.getId()).removeValue();
        appDatabase.getEntryDao().delete(entry);
    }

    private String getCurrentLocalDateTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return simpleDateFormat.format(now);
    }
}


