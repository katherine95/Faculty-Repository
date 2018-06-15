package com.example.facultyapp.ui.auth.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.facultyapp.R;
import com.example.facultyapp.data.model.User;
import com.example.facultyapp.settings.Settings;
import com.example.facultyapp.ui.auth.viewmodel.AddUserViewModel;
import com.example.facultyapp.ui.main.ui.LecturerActivity;
import com.example.facultyapp.ui.main.ui.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AuthActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 121;

    @BindView(R.id.loginButton)
    Button loginButton;

    GoogleApiClient mGoogleApiClient;
    private AddUserViewModel addUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        addUserViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In Api and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivitgit y */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        loginButton.setOnClickListener(v -> {
            //Check Internet Connection
            //  if (NetworkUtil.isConnected(this))
            googleSignIn();

        });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//         .requestScopes(Drive.SCOPE_FILE)
//                .requestScopes(Drive.SCOPE_APPFOLDER)
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Timber.d("handleSignInResult:" + result.isSuccess() + " " + result.getStatus());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;

            String name = acct.getDisplayName();
            String imageUrl = String.valueOf(acct.getPhotoUrl());

            /*
             * Save to Room DB
             * Set the Logged in status to true
             * Navigate user to Main Activity
             */
            addUserViewModel.addUser(new User(
                    "1",
                    name,
                    imageUrl
            ));

            Settings.setLoggedInSharedPref(true);

            Intent intent = new Intent(getApplicationContext(), LecturerActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.isLoggedIn()) {
            Intent auth = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(auth);
            finish();
        }
    }
}
