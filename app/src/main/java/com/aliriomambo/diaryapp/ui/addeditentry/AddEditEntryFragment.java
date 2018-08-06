package com.aliriomambo.diaryapp.ui.addeditentry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.ui.entries.EntriesActivity;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddEditEntryFragment extends Fragment implements AddEditEntryContract.View {
    public static final String ARGUMENT_EDIT_ENTRY_ID = "EDIT_ENTRY_ID";
    private AddEditEntryContract.Presenter mPresenter;
    private EditText mTitle;
    private EditText mDescription;
    private Button mSubmitEntry;
    private Spinner mFeelingsSpinner;
    private FirebaseUser mFirebaseUser;

    @NonNull
    public static AddEditEntryFragment newInstance() {
        return new AddEditEntryFragment();
    }

    @Override
    public void setPresenter(AddEditEntryContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showEmptyEntryError() {
        Snackbar.make(mTitle, getString(R.string.error_empty_fields), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showEntryList() {
        Intent startEntriesActivity = new Intent(getActivity(), EntriesActivity.class);
        startActivity(startEntriesActivity);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void showExistingEntry(Entry entry) {

        mTitle.setText(entry.getTitle());
        mFeelingsSpinner.setSelection(getFeelingSpinnerPosition(entry.getFeeling()));
        mDescription.setText(entry.getDescription());
    }

    private int getFeelingSpinnerPosition(String feeling) {
        String[] feelingsArray = getResources().getStringArray(R.array.spinner_feelings);
        for (int i = 0; i < feelingsArray.length; i++) {
            if (feelingsArray[i].equals(feeling)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void showNoNetworkError() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_no_network_addedit), Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.start();
                        }
                    }).show();
            mSubmitEntry.setEnabled(false);
        }

    }

    @Override
    public void makeButtonAvailable() {
        mSubmitEntry.setEnabled(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addeditentry, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = view.findViewById(R.id.edt_title_addeditentry);
        mDescription = view.findViewById(R.id.edt_description_addeditentry);
        mSubmitEntry = view.findViewById(R.id.btn_submit_entry_addeditentry);
        mFeelingsSpinner = view.findViewById(R.id.spin_feelings_addeditentry);

        mSubmitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveEntry(mTitle.getText().toString(),
                        mDescription.getText().toString(),
                        mFeelingsSpinner.getSelectedItem().toString(), mFirebaseUser.getUid());

            }
        });
        mPresenter.start();
    }
}
