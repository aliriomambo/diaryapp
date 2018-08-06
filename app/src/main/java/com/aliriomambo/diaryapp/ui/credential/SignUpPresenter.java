package com.aliriomambo.diaryapp.ui.credential;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aliriomambo.diaryapp.Utils.NetworkUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpPresenter implements SignUpContract.Presenter, NetworkUtils.CheckNetworkResponse {
    @NonNull
    private final SignUpContract.View mSignUpView;
    private FirebaseAuth mAuth;
    private Context mContext;


    public SignUpPresenter(Context context, @NonNull SignUpContract.View signUpView) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mSignUpView = Preconditions.checkNotNull(signUpView);
        mSignUpView.setPresenter(this);
    }

    @Override
    public void start() {
        checkNetworkStatus();
    }

    private void checkNetworkStatus() {
        NetworkUtils.CheckNetworkTask mCheckNetworkTask = new NetworkUtils().new CheckNetworkTask(this);
        mCheckNetworkTask.execute(mContext);
    }

    private boolean isPasswordInPattern(String password) {
        if (password.length() < 8 || password.length() > 16) {
            mSignUpView.passwordPatternError();
            return false;
        } else {
            return true;

        }
    }


    @Override
    public void signUp(String mUsername, String mPassword, final String mDisplayName, Context context) {
        checkNetworkStatus();
        if (isPasswordInPattern(mPassword)) {
            if (!mUsername.isEmpty() && !mPassword.isEmpty() && !mDisplayName.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(mUsername, mPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("FB Create Succ", "createUserWithEmail:success");
                                    setDisplayName(mDisplayName);

                                    mSignUpView.signUpSuccess();
                                    mSignUpView.moveToMainScreen();
                                } else {
                                    mSignUpView.signUpFail();
                                }
                            }
                        });
            } else {
                mSignUpView.showEmptyFieldError();
            }
        }

    }

    private void setDisplayName(String displayName) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName).build();

            user.updateProfile(profileUpdates);
        }

    }


    @Override
    public void processFinish(boolean hasInternetAccess) {
        if (hasInternetAccess) {
            mSignUpView.makeButtonAvailable();
        } else {
            mSignUpView.showNoNetworkError();
        }
    }
}
