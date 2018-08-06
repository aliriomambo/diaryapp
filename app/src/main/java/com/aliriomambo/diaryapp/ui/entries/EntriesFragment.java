package com.aliriomambo.diaryapp.ui.entries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;
import com.aliriomambo.diaryapp.ui.addeditentry.AddEditEntryActivity;
import com.aliriomambo.diaryapp.ui.entrydetail.EntryDetailActivity;
import com.aliriomambo.diaryapp.ui.entrydetail.EntryDetailFragment;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class EntriesFragment extends Fragment implements EntriesContract.View, EntriesRecyclerViewListener {

    private EntriesAdapter entriesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EntriesContract.Presenter mPresenter;

    private FloatingActionButton mFab;
    private SearchView mSearchView;

    public static EntriesFragment newInstance() {
        return new EntriesFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(EntriesContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }


    @Override
    public void showEntries(List<Entry> entryList) {
        entriesAdapter.replaceData((ArrayList) entryList);
    }

    @Override
    public void showNoNetworkError() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_no_network_entries_fragment), Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.start();
                        }
                    }).show();
        }

    }


    @Override
    public void onItemClick(View view, int position, String id) {
        Intent startEntryDetailActivity = new Intent(getContext(), EntryDetailActivity.class);
        startEntryDetailActivity.putExtra(EntryDetailFragment.ARGUMENT_ENTRY_ID, id);
        startActivity(startEntryDetailActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entries, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_entries);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_entries);
        entriesAdapter = new EntriesAdapter(getContext(), entryArrayList, this);
        mFab = view.findViewById(R.id.fab_add_entries_fragment);
        Repository repository = new Repository(getContext());
        repository.synchronize();
        recyclerView.setAdapter(entriesAdapter);
        mSearchView = view.findViewById(R.id.search_view_entries);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                entriesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                entriesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewEntryScreen();
            }
        });

    }

    private void openAddNewEntryScreen() {
        Intent startAddEditEntryActivity = new Intent(getActivity(), AddEditEntryActivity.class);
        startActivity(startAddEditEntryActivity);


    }

}
