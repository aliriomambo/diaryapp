package com.aliriomambo.diaryapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity(tableName = "entries")
public class Entry implements Comparable<Entry> {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entry_id")
    private String id;

    @Nullable
    @ColumnInfo(name = "title")
    private String title;

    @Nullable
    @ColumnInfo(name = "description")
    private String description;

    @Nullable
    @ColumnInfo(name = "feeling")
    private String feeling;

    @Nullable
    @ColumnInfo(name = "date_time")
    private String dateTime;

    @NonNull
    @ColumnInfo(name = "creator_id")
    private String creatorId;

    public Entry() {

    }


    /**
     * Use this constructor to create a new active Task.
     *
     * @param title       title of the entry
     * @param description description of the entry
     * @param feeling     what was the writer feeling during the entry creation
     */

    @Ignore
    public Entry(@Nullable String title, @Nullable String description, @Nullable String feeling) {
        this.title = title;
        this.description = description;
        this.feeling = feeling;
    }

    @Ignore
    public Entry(@Nullable String title, @Nullable String description, @Nullable String feeling, @NonNull String id) {
        this.title = title;
        this.description = description;
        this.feeling = feeling;
        this.id = id;
    }

    @Ignore
    public Entry(@Nullable String title, @Nullable String description, @Nullable String feeling, @NonNull String id, String creatorId) {
        this.title = title;
        this.description = description;
        this.feeling = feeling;
        this.id = id;
        this.creatorId = creatorId;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(@Nullable String feeling) {
        this.feeling = feeling;
    }

    @Nullable
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nullable String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title) &&
                Strings.isNullOrEmpty(description)
                && Strings.isNullOrEmpty(feeling)
                && Strings.isNullOrEmpty(creatorId);
    }

    @NonNull
    public String getCreatorId() {
        return creatorId;
    }


    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public int compareTo(@NonNull Entry entry) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(this.dateTime).compareTo(sdf.parse(entry.getDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
