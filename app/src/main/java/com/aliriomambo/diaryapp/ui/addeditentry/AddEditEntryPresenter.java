package com.aliriomambo.diaryapp.ui.addeditentry;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Spinner;

import com.aliriomambo.diaryapp.Utils.NetworkUtils;
import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditEntryPresenter implements AddEditEntryContract.Presenter, NetworkUtils.CheckNetworkResponse {

    @NonNull
    private final AddEditEntryContract.View mAddEditEntryView;

    private Repository mEntryRepository;

    @Nullable
    private String mEntryId;


    private Context mContext;

    private Spinner spinner;


    public AddEditEntryPresenter(@Nullable String entryId, Context context, @NonNull AddEditEntryContract.View addEditEntryView) {
        mEntryId = entryId;
        mAddEditEntryView = checkNotNull(addEditEntryView);
        mAddEditEntryView.setPresenter(this);
        mContext = context;
        mEntryRepository = new Repository(context);
        mEntryRepository.synchronize();
    }

    public AddEditEntryPresenter(@Nullable String entryId, Repository repository, @NonNull AddEditEntryContract.View addEditEntryView) {
        mEntryId = entryId;
        mAddEditEntryView = checkNotNull(addEditEntryView);
        mAddEditEntryView.setPresenter(this);
        mEntryRepository = repository;
    }

    public AddEditEntryPresenter(@Nullable String entryId, @NonNull AddEditEntryContract.View addEditEntryView) {
        mEntryId = entryId;
        mAddEditEntryView = checkNotNull(addEditEntryView);
        mAddEditEntryView.setPresenter(this);
    }

    private void checkNetworkStatus() {
        NetworkUtils.CheckNetworkTask mCheckNetworkTask = new NetworkUtils().new CheckNetworkTask(this);
        mCheckNetworkTask.execute(mContext);
    }

    @Override
    public void start() {
        checkNetworkStatus();
        if (!isNewEntry()) {
            populateEntry();
        }
    }

    private boolean isNewEntry() {
        return mEntryId == null;
    }


    private void createEntry(String title, String description, String feeling, String creatorId) {
        Entry newEntry = new Entry(title, description, feeling);
        newEntry.setCreatorId(creatorId);

        if (checkFieldsFilled(title, description, feeling, creatorId)) {
            mEntryRepository.addEntry(newEntry);
            mAddEditEntryView.showEntryList();
        } else {
            mAddEditEntryView.showEmptyEntryError();
        }
    }

    private boolean checkFieldsFilled(String title, String description,
                                      String feeling, String creatorId) {

        if (Strings.isNullOrEmpty(title)) {
            return false;
        } else if (Strings.isNullOrEmpty(description)) {
            return false;
        } else if (Strings.isNullOrEmpty(feeling)) {
            return false;
        } else if (Strings.isNullOrEmpty(creatorId)) {
            return false;
        }
        return true;
    }


    private void updateEntry(String title, String description, String feeling, String creatorId) {
        if (isNewEntry()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }

        if (mEntryId != null) {
            Entry entry = new Entry(title, description, feeling, mEntryId);
            entry.setCreatorId(creatorId);
            mEntryRepository.updateEntry(entry);
        }
        mAddEditEntryView.showEntryList(); // After an edit, go back to the list.
    }

    @Override
    public void saveEntry(String title, String description, String feeling, String creatorId) {
        if (isNewEntry()) {
            createEntry(title, description, feeling, creatorId);
        } else {
            updateEntry(title, description, feeling, creatorId);
        }
    }

    @Override
    public void populateEntry() {
        mAddEditEntryView.showExistingEntry(mEntryRepository.getEntryById(mEntryId));
    }

    @Override
    public void processFinish(boolean hasInternetAccess) {
        if (hasInternetAccess) {
            mAddEditEntryView.makeButtonAvailable();
        } else {
            mAddEditEntryView.showNoNetworkError();
        }
    }
}
