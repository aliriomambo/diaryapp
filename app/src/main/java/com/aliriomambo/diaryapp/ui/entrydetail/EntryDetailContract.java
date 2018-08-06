package com.aliriomambo.diaryapp.ui.entrydetail;

import com.aliriomambo.diaryapp.base.BasePresenter;
import com.aliriomambo.diaryapp.base.BaseView;
import com.aliriomambo.diaryapp.data.model.Entry;



public interface EntryDetailContract {
    interface View extends BaseView<EntryDetailContract.Presenter> {

        void showEntryDetail(Entry entry);

        void openEditScreen(String entryId);

        void openEntriesScreen();


    }

    interface Presenter extends BasePresenter {
        void editEntry(Entry entry);

        void loadEntry(String entryId);

        void deleteEntry(Entry entry);
    }
}
