package com.example.facultyapp.ui.main.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.facultyapp.R;
import com.example.facultyapp.data.model.Notes;
import com.example.facultyapp.data.model.User;
import com.example.facultyapp.databinding.ActivityMainBinding;
import com.example.facultyapp.settings.Settings;
import com.example.facultyapp.ui.auth.ui.AuthActivity;
import com.example.facultyapp.ui.auth.ui.WelcomeActivity;
import com.example.facultyapp.ui.chat.ChatActivity;
import com.example.facultyapp.ui.main.adapter.StudentAdapter;
import com.example.facultyapp.ui.main.viewmodel.MainViewModel;
import com.example.facultyapp.util.Constants;
import com.example.facultyapp.view.ProfileDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tingyik90.snackprogressbar.SnackProgressBar;
import com.tingyik90.snackprogressbar.SnackProgressBarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    User user;
    private ProfileDialog profileDialog;
    private SnackProgressBarManager snackProgressBarManager;
    private GoogleApiClient mGoogleApiClient;

    //database reference
    private DatabaseReference mDatabase;

    private List<Notes> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        user = mainViewModel.getUserLiveData();
        binding.setUser(user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FloatingActionButton chatForum = findViewById(R.id.chatForum);
        chatForum.setOnClickListener(view -> {
            Intent chatForum1 = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(chatForum1);
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show();
        });

        profileDialog = ProfileDialog.newInstance(((dialog, which) -> logout()));

        //Initialize Snackbar Manager -> Attach/pin to the bottom of the layout :)
        snackProgressBarManager = new SnackProgressBarManager(coordinatorLayout)
                .setProgressBarColor(R.color.colorAccent)
                .setOverlayLayoutAlpha(0.6f);

        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In Api and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        notesList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Notes notes = postSnapshot.getValue(Notes.class);
                    notesList.add(notes);
                }

                StudentAdapter studentAdapter = new StudentAdapter(getApplicationContext(), notesList);
                recyclerView.setAdapter(studentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem profileItem = menu.findItem(R.id.action_profile);
        Glide.with(this)
                .asBitmap()
                .load(user.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        profileItem.setIcon(new BitmapDrawable(getResources(), resource));
                    }
                });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            profileDialog.show(getSupportFragmentManager(), "profile");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        if (!Settings.isLoggedIn()) {
            return;
        }

        SnackProgressBar snackProgressBar = new SnackProgressBar(
                SnackProgressBar.TYPE_INDETERMINATE,
                "Logging Out...")
                .setSwipeToDismiss(false);

        // Show snack progress during logout
        snackProgressBarManager.dismissAll();
        snackProgressBarManager.show(snackProgressBar, SnackProgressBarManager.LENGTH_INDEFINITE);

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
            //Clear Shared Pref File
            Settings.setLoggedInSharedPref(false);
            //Clear Local DB
            //TODO :: CLEAR DB

            //Redirect User to Login Page
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
            finish();
        });

        //Unreachable anyway
        snackProgressBarManager.dismiss();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
