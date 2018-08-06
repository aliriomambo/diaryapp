package com.aliriomambo.diaryapp.ui.credential;

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

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.ui.entries.EntriesActivity;
import com.google.common.base.Preconditions;


public class SignUpFragment extends Fragment implements SignUpContract.View {
    private SignUpContract.Presenter mPresenter;

    private EditText mUsernameText;

    private EditText mPasswordText;

    private EditText mDisplayName;

    private Button mCreateAccBtn;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showEmptyFieldError() {
        Snackbar.make(getView(), getString(R.string.error_empty_fields_credential), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void passwordPatternError() {
        Snackbar.make(getView(), getString(R.string.error_password_pattern), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void signUpSuccess() {
        Snackbar.make(getView(), getString(R.string.msg_created_account_success), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void signUpFail() {
        Snackbar.make(getView(), getString(R.string.msg_created_account_fail), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void moveToMainScreen() {
        Intent startMainActivity = new Intent(getActivity(), EntriesActivity.class);
        startActivity(startMainActivity);
        if (getActivity() != null) {
            getActivity().finish();

        }
    }

    @Override
    public void showNoNetworkError() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_no_network_credential), Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.start();
                        }
                    }).show();
            mCreateAccBtn.setEnabled(false);
        }

    }

    @Override
    public void makeButtonAvailable() {
        mCreateAccBtn.setEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUsernameText = view.findViewById(R.id.edt_username_signup);
        mPasswordText = view.findViewById(R.id.edt_password_signup);
        mCreateAccBtn = view.findViewById(R.id.btn_create_account_signup);
        mDisplayName = view.findViewById(R.id.edt_display_name_signup);

        mCreateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signUp(mUsernameText.getText().toString(), mPasswordText.getText().toString(),
                        mDisplayName.getText().toString(), getContext());
            }
        });
    }
}
