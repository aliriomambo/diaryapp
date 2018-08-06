package com.aliriomambo.diaryapp.ui.addeditentry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.Utils.ActivityUtils;
import com.google.common.base.Preconditions;

/**
 * Created by Alirio Mambo
 */

public class AddEditEntryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addeditentry);
        AddEditEntryFragment addEditEntryFragment = (AddEditEntryFragment) getSupportFragmentManager().
                findFragmentById(R.id.frame_layout_addeditentry_activity);

        String entryId = getIntent().getStringExtra(AddEditEntryFragment.ARGUMENT_EDIT_ENTRY_ID);


        if (addEditEntryFragment == null) {
            addEditEntryFragment = Preconditions.checkNotNull(addEditEntryFragment.newInstance());

            if (getIntent().hasExtra(AddEditEntryFragment.ARGUMENT_EDIT_ENTRY_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(AddEditEntryFragment.ARGUMENT_EDIT_ENTRY_ID, entryId);
                addEditEntryFragment.setArguments(bundle);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditEntryFragment, R.id.frame_layout_addeditentry_activity);
        }

        new AddEditEntryPresenter(entryId, this, addEditEntryFragment);
        getSupportActionBar().setTitle("Add New Entry");
    }

}
