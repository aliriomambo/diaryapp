package com.aliriomambo.diaryapp.ui.entries;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.aliriomambo.diaryapp.Utils.NetworkUtils;
import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;


public class EntriesPresenter implements EntriesContract.Presenter, NetworkUtils.CheckNetworkResponse {
    private Context mContext;
    private Repository mRepository;
    private EntriesContract.View mEntriesView;
    private EntriesFragment mEntriesFragment;
    private FirebaseUser mFirebaseUser;


    public EntriesPresenter(@NonNull Context context, @NonNull EntriesContract.View entriesView, EntriesFragment entriesFragment) {
        mRepository = new Repository(context);
        mRepository.synchronize();
        mEntriesView = entriesView;
        mEntriesFragment = entriesFragment;
        entriesView.setPresenter(this);
        mContext = context;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public EntriesPresenter(Repository repository, @NonNull EntriesContract.View entriesView, EntriesFragment entriesFragment) {
        mRepository = repository;
        mEntriesView = entriesView;
        mEntriesFragment = entriesFragment;
    }

    public EntriesPresenter(Repository repository, @NonNull EntriesContract.View entriesView) {
        mRepository = repository;
        mEntriesView = entriesView;
    }

    @Override
    public void start() {
        checkNetworkStatus();
    }

    private void checkNetworkStatus() {
        NetworkUtils.CheckNetworkTask mCheckNetworkTask = new NetworkUtils().new CheckNetworkTask(this);
        mCheckNetworkTask.execute(mContext);
    }

    @Override
    public void loadEntries(Fragment fragment) {
        mRepository.getEntryListLiveData(mFirebaseUser.getUid()).observe(fragment, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null) {
                    Collections.sort(entries, Collections.<Entry>reverseOrder());
                    mEntriesView.showEntries(entries);
                }

            }
        });
    }


    @Override
    public void processFinish(boolean hasInternetAccess) {
        loadEntries(mEntriesFragment);
        if (!hasInternetAccess) {
            mEntriesView.showNoNetworkError();
        }
    }

}
