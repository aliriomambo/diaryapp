package com.aliriomambo.diaryapp.ui.credential;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.Utils.ActivityUtils;


public class CredentialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential);
        SignInFragment signInFragment = (SignInFragment) getSupportFragmentManager().
                findFragmentById(R.id.frame_layout_credential);

        if (signInFragment == null) {
            signInFragment = SignInFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), signInFragment, R.id.frame_layout_credential);
        }


        new SignInPresenter(this, signInFragment);


    }


}

