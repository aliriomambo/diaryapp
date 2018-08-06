package com.aliriomambo.diaryapp.ui.credential;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.Utils.ActivityUtils;
import com.aliriomambo.diaryapp.Utils.FileConfig;
import com.aliriomambo.diaryapp.ui.entries.EntriesActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignInFragment extends Fragment implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    private EditText mUsernameText;

    private EditText mPasswordText;

    private Button mLoginBtn;

    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;

    // Firebase Auth Object.
    public FirebaseAuth mFirebaseAuth;

    // Google API Client object.
    public GoogleApiClient mGoogleApiClient;

    // Google Sign In button .
    com.google.android.gms.common.SignInButton mSignInButton;


    public void setupLoginGoogle() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(FileConfig.GOOGLE_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }


    public void loginWithGoogle() {
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode) {

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()) {

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                connectFirebaseToGoogle(googleSignInAccount);
            }

        }
    }

    public void connectFirebaseToGoogle(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Success FB to Google", "YES");
                            Toast.makeText(getContext(), "Sign In with Google Success", Toast.LENGTH_LONG).show();
                            moveToMainScreen();
                        } else {
                            Toast.makeText(getContext(), "Sign In with Google Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public SignInFragment() {

    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull SignInContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }


    @Override
    public void showEmptyFieldError() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_empty_fields_credential), Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void wrongCredentials() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_wrong_credentials), Snackbar.LENGTH_LONG).show();

        }

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
            mLoginBtn.setEnabled(false);
        }

    }

    @Override
    public void makeButtonAvailable() {
        mLoginBtn.setEnabled(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUsernameText = view.findViewById(R.id.edt_username_signin);
        mPasswordText = view.findViewById(R.id.edt_password_signin);
        mLoginBtn = view.findViewById(R.id.btn_login_signin);
        TextView mCreateAccText = view.findViewById(R.id.txt_create_account_signin);
        mSignInButton = view.findViewById(R.id.btn_sign_in_google);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(mUsernameText.getText().toString(), mPasswordText.getText().toString());
            }
        });

        mCreateAccText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAccountScreen();
            }
        });
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupLoginGoogle();
                loginWithGoogle();
            }
        });



    }

    private void openCreateAccountScreen() {
        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        if (getActivity() != null) {
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), signUpFragment, R.id.frame_layout_credential);
            new SignUpPresenter(getActivity(), signUpFragment);
        }

    }


}
