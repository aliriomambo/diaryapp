package com.aliriomambo.diaryapp.ui.entrydetail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;

public class EntryDetailPresenter implements EntryDetailContract.Presenter {
    private Repository mEntryRepository;
    private EntryDetailContract.View mEntryDetailView;


    public EntryDetailPresenter(Context context, @NonNull EntryDetailContract.View entryDetailView) {
        mEntryDetailView = entryDetailView;
        entryDetailView.setPresenter(this);
        mEntryRepository = new Repository(context);
        mEntryRepository.synchronize();
    }

    public EntryDetailPresenter(Repository entryRepository, @NonNull EntryDetailContract.View entryDetailView) {
        mEntryDetailView = entryDetailView;
        entryDetailView.setPresenter(this);
        mEntryRepository = entryRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void editEntry(Entry entry) {
        mEntryDetailView.openEditScreen(entry.getId());
    }

    @Override
    public void loadEntry(String id) {
        mEntryDetailView.showEntryDetail(mEntryRepository.getEntryById(id));
    }

    @Override
    public void deleteEntry(Entry entry) {
        mEntryRepository.removeEntry(mEntryRepository.getEntryById(entry.getId()));
        mEntryDetailView.openEntriesScreen();

    }
}
