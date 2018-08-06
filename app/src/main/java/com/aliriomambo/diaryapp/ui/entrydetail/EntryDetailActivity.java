package com.aliriomambo.diaryapp.ui.entrydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.Utils.ActivityUtils;

public class EntryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrydetail);
        EntryDetailFragment entryDetailFragment = (EntryDetailFragment) getSupportFragmentManager().
                findFragmentById(R.id.frame_layout_entrydetail_activity);

        String entryId = getIntent().getStringExtra(EntryDetailFragment.ARGUMENT_ENTRY_ID);

        if (entryDetailFragment == null) {
            entryDetailFragment = EntryDetailFragment.newInstance(entryId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), entryDetailFragment, R.id.frame_layout_entrydetail_activity);
        }

        new EntryDetailPresenter(this, entryDetailFragment);
        getSupportActionBar().setTitle("Entry Detail");

    }
}
