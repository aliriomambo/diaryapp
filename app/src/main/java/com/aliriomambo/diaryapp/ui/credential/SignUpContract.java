package com.aliriomambo.diaryapp.ui.credential;

import android.content.Context;

import com.aliriomambo.diaryapp.base.BasePresenter;
import com.aliriomambo.diaryapp.base.BaseView;


public interface SignUpContract {
    interface View extends BaseView<SignUpContract.Presenter> {

        void showEmptyFieldError();

        void passwordPatternError();

        void signUpSuccess();

        void signUpFail();

        void moveToMainScreen();

        void showNoNetworkError();

        void makeButtonAvailable();

    }

    interface Presenter extends BasePresenter {
        void signUp(String mUsername, String mPassword, String mDisplayName, Context context);
    }
}
