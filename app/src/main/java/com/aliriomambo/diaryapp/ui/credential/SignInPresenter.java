package com.aliriomambo.diaryapp.ui.credential;

import android.content.Context;
import android.support.annotation.NonNull;

import com.aliriomambo.diaryapp.Utils.NetworkUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInPresenter implements SignInContract.Presenter, NetworkUtils.CheckNetworkResponse {
    @NonNull
    private final SignInContract.View mSignInView;
    private FirebaseAuth mAuth;
    private Context mContext;


    public SignInPresenter(Context context, @NonNull SignInContract.View signInView) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mSignInView = Preconditions.checkNotNull(signInView);
        mSignInView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mAuth.getCurrentUser() == null) {
            checkNetworkStatus();
        } else {
            mSignInView.moveToMainScreen();
        }
    }

    private void checkNetworkStatus() {
        NetworkUtils.CheckNetworkTask mCheckNetworkTask = new NetworkUtils().new CheckNetworkTask(this);
        mCheckNetworkTask.execute(mContext);
    }

    @Override
    public void login(String mUsername, String mPassword) {
        checkNetworkStatus();
        if (mUsername.isEmpty() || mPassword.isEmpty()) {
            mSignInView.showEmptyFieldError();
        } else {
            mAuth.signInWithEmailAndPassword(mUsername, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mSignInView.moveToMainScreen();
                    } else {
                        mSignInView.wrongCredentials();
                    }
                }
            });
        }

    }

    @Override
    public void processFinish(boolean hasInternetAccess) {
        if (hasInternetAccess) {
            mSignInView.makeButtonAvailable();
        } else {
            mSignInView.showNoNetworkError();
        }
    }
}
