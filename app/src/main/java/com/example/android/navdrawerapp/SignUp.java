package com.example.android.navdrawerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;
import com.example.android.navdrawerapp.Model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth;

    MaterialEditText edtRegNo, edtPassword, edtEmail;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        edtRegNo = findViewById(R.id.edtRegNo);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference table_user = database.getReference("users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                if(validate()){
                    //upload data to database
                    String regNo = edtRegNo.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(regNo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mDialog.dismiss();
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();


                            }else{
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                /*String regNo = edtRegNo.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(regNo)) {
                    Toast.makeText(getApplicationContext(), "Enter registration Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //if (TextUtils.isEmpty(userName)) {
                    //Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    //return;
                //}

                if (password.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 4 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(regNo, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete (@NonNull Task < AuthResult > task) {
                            Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                finish();
                            }
                        }
                });*/
            }
        });
    //}


                //table_user.addValueEventListener(new ValueEventListener() {
                    //@Override
                    //public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already user regno already exists
                        //if(dataSnapshot.child(edtRegNo.getText().toString()).exists()){
                            //mDialog.dismiss();
                            //Toast.makeText(SignUp.this, "Registration number already exists.", Toast.LENGTH_SHORT).show();
                        //}else{
                            //mDialog.dismiss();
                            //users user = new users(edtRegNo.getText().toString(), edtPassword.getText().toString(), edtUsername.getText().toString());
                            //table_user.child(edtRegNo.getText().toString()).setValue(user);
                            //Toast.makeText(SignUp.this, "Sign up Successful.", Toast.LENGTH_SHORT).show();
                            //Intent main = new Intent(SignUp.this,MainActivity.class);
                            //startActivity(main);

                        //}
                    //}

                    //@Override
                    //public void onCancelled(DatabaseError databaseError) {

                    //}
                //});

        /*btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUp.this,SignIn.class);
                startActivity(signIn);
            }
        });*/

    }

    private boolean validate(){
        Boolean result = false;
        String regNo = edtRegNo.getText().toString();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if(regNo.isEmpty() && password.isEmpty() && (email.isEmpty())){
            Toast.makeText(this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }
}
