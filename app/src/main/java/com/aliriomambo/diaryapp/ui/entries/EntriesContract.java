package com.aliriomambo.diaryapp.ui.entries;

import android.support.v4.app.Fragment;

import com.aliriomambo.diaryapp.base.BasePresenter;
import com.aliriomambo.diaryapp.base.BaseView;
import com.aliriomambo.diaryapp.data.model.Entry;

import java.util.List;



public interface EntriesContract {
    interface View extends BaseView<EntriesContract.Presenter> {

        void showEntries(List<Entry> entryList);

        void showNoNetworkError();


    }

    interface Presenter extends BasePresenter {
        void loadEntries(Fragment fragment);

    }
}
