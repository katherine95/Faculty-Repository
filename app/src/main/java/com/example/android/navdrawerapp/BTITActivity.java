package com.example.android.navdrawerapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BTITActivity extends AppCompatActivity {
    //private RecyclerView mCourseRV;
    //private FirebaseRecyclerAdapter<Course, CourseViewHolder> mCourseAdapter;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCourseReference;
    private ListView mCourseListView;
    private FirebaseDatabase mFirebaseDatabase;
    private CourseAdapter mCourseAdapter;
    public static final int RC_SIGN_IN = 1;
    String mUsername;
    String ANONYMOUS = "anonymous";

    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btit);
        //initialiseScreen();

        //Initialize Firebase Components
        mFirebaseStorage = FirebaseStorage.getInstance();
        mCourseReference = mFirebaseStorage.getReference().child("cryptography");

        // Initialize references to views
        mCourseListView = (ListView) findViewById(R.id.messageListView);

        // Initialize course ListView and its adapter
        List<Course> courses = new ArrayList<>();
        mCourseAdapter = new CourseAdapter(this,R.layout.course_list_item, courses);
        mCourseListView.setAdapter(mCourseAdapter);

        //mCourseReference.addValueEventlistener(new ValueEventlistener(){

        //});

       /* mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(BTITActivity.this, "You are now signed in. Welcome to the Course section!", Toast.LENGTH_SHORT).show();
                } else {
                    //user is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays
                                            .asList(
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .setTosUrl("https://superapp.example.com/terms-of-service.html")
                                    .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };*/

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            } /*else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
                Uri selectedImageUri = data.getData();
                StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

                photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    //@Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername,downloadUrl.toString());
                        //mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    }
                });
            }*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                //sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mCourseAdapter.clear();
        detachDatabasereadlistener();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Course course = dataSnapshot.getValue(Course.class);
                    mCourseAdapter.add(course);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            //mCourseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabasereadlistener() {
        if (mChildEventListener != null) {
            //mCourseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabasereadlistener();
        mCourseAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    /*private void initialiseScreen(){
        mCourseRV = findViewById(R.id.rv_Course);
        mCourseRV.setLayoutManager(new LinearLayoutManager(BTITActivity.this));
        mCourseReference = FirebaseStorage.getInstance().getReference(SyncStateContract.Constants.COURSES);
        setUpAdapter();
    }

    private void setUpAdapter() {
        mCourseAdapter = new FirebaseRecyclerAdapter<Course, CourseViewHolder>(
                Course.class,
                R.layout.course_list_item,
                CourseViewHolder.class,
                mCourseReference) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, Course model, int position) {
                viewHolder.setCourse(model.getCourseUrl());
                viewHolder.course.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
    }

    private static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView course;
        public ImageView download;
        public ImageView preview;
        public ImageView archive;

        public CourseViewHolder(View itemView) {
            super(itemView);
            course=itemView.findViewById(R.id.course);
            download=itemView.findViewById(R.id.download);
            preview=itemView.findViewById(R.id.preview);
            archive=itemView.findViewById(R.id.archive);
        }

        public void setCourse(String url){
            StorageReference mCourseReference = FirebaseStorage.getInstance().getReference(url);
            //how to get reference to storage 6th minute video3

        }
        public void setDownload(String url){

        }
        public void setPreview(String url){

        }
        public void setArchive(String url){

        }
    }*/
}
