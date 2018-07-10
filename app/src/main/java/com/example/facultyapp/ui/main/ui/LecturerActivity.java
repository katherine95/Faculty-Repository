package com.example.facultyapp.ui.main.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.facultyapp.R;
import com.example.facultyapp.data.model.Notes;
import com.example.facultyapp.data.model.User;
import com.example.facultyapp.ui.auth.ui.WelcomeActivity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tingyik90.snackprogressbar.SnackProgressBar;
import com.tingyik90.snackprogressbar.SnackProgressBarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.facultyapp.R.id;

public class LecturerActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;
    @BindView(id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(id.imageLec)
    CircleImageView imageLec;
    @BindView(id.textViewLec)
    TextView textViewLec;
    @BindView(id.textViewTotalBooks)
    TextView textViewTotalBooks;

    @BindView(id.etBookName)
    EditText etBookName;
    @BindView(id.etSemester)
    EditText etSemester;
    @BindView(id.etYear)
    EditText etYear;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    User user;
    @BindView(id.chooseBook)
    Button chooseBook;
    @BindView(id.saveBook)
    Button saveBook;
    String name, year, semester, imagePath;
    Uri imageData;
    private List<Notes> notesList;
    private ProfileDialog profileDialog;
    private SnackProgressBarManager snackProgressBarManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        user = mainViewModel.getUserLiveData();

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


        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        Glide.with(this)
                .asBitmap()
                .load(user.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imageLec);

        textViewLec.setText(user.getName());

        notesList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Notes notes = postSnapshot.getValue(Notes.class);
                    notesList.add(notes);
                }

                textViewTotalBooks.setText("" + notesList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chooseBook.setOnClickListener(view -> getPDF());
        saveBook.setOnClickListener(view -> {
            if (imageData != null) {
                validateInput();
            } else {
                Toast.makeText(this, "Choose a book first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateInput() {

        name = etBookName.getText().toString().trim();
        year = etYear.getText().toString().trim();
        semester = etSemester.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etBookName.setError("Please choose book name");
            return;
        }

        if (TextUtils.isEmpty(year)) {
            etYear.setError("Please choose year");
            return;
        }

        if (TextUtils.isEmpty(semester)) {
            etSemester.setError("Please choose semester");
            return;
        }

        if (!year.equals("1") || !year.equals("2") || !year.equals("3") || !year.equals("4") ) {
            etYear.setError("Please choose correct year");
            return;
        }

        if (!semester.equals("1") || !semester.equals("2")) {
            etSemester.setError("Please choose correct semester");
            return;
        }

        uploadFile(imageData);
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                imageData = data.getData();
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {
                    Notes notes = new Notes(name, sRef.getDownloadUrl().toString(), year, semester);
                    mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(notes);
                })
                .addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show())
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

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
        MenuItem profileItem = menu.findItem(id.action_profile);
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
        if (!com.example.facultyapp.settings.Settings.isLoggedIn()) {
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
            com.example.facultyapp.settings.Settings.setLoggedInSharedPref(false);
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
