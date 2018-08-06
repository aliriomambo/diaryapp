package com.aliriomambo.diaryapp.ui.addeditentry;

import com.aliriomambo.diaryapp.base.BasePresenter;
import com.aliriomambo.diaryapp.base.BaseView;
import com.aliriomambo.diaryapp.data.model.Entry;

public interface AddEditEntryContract {

    interface View extends BaseView<AddEditEntryContract.Presenter> {
        void showEmptyEntryError();

        void showEntryList();

        void showExistingEntry(Entry entry);

        void showNoNetworkError();

        void makeButtonAvailable();

    }

    interface Presenter extends BasePresenter {

        void saveEntry(String title, String description, String feeling, String creatorId);

        void populateEntry();

    }
}
