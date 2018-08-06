package com.aliriomambo.diaryapp.ui.credential;

import com.aliriomambo.diaryapp.base.BasePresenter;
import com.aliriomambo.diaryapp.base.BaseView;


public interface SignInContract {
    interface View extends BaseView<Presenter> {

        void showEmptyFieldError();

        void wrongCredentials();

        void moveToMainScreen();

        void showNoNetworkError();

        void makeButtonAvailable();

    }

    interface Presenter extends BasePresenter {
        void login(String mUsername, String mPassword);

    }
}
