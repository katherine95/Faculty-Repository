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
import com.google.firebase.auth.AuthResult;
import com.example.android.navdrawerapp.Model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText edtRegNo, edtPassword, edtEmail;
    Button btnSignIn, btnSignUp;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        /*if(auth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(SignIn.this, MainActivity.class));

        }*/


        edtRegNo = findViewById(R.id.edtRegNo);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);


        //Get Firebase auth instance
        //auth = FirebaseAuth.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference table_user = database.getReference("users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate(edtRegNo.getText().toString(), edtPassword.getText().toString());

                /*if (TextUtils.isEmpty(regNo)) {
                    Toast.makeText(getApplicationContext(), "Enter registration number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(regNo, password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                //progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 4) {
                                        edtPassword.setError(getString(R.string.password_length));
                                    } else {
                                        Toast.makeText(SignIn.this, getString(R.string.failed_auth), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });*/

                //table_user.addValueEventListener(new ValueEventListener() {


                    //@Override
                    //public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user does not exist in the database
                        //if (dataSnapshot.child(edtRegNo.getText().toString()).exists()) {

                            //get user information
                            //mDialog.dismiss();
                            //users user = dataSnapshot.getValue(users.class);
                            //user.setregNo(regNo);
                            //assert user != null;
                            //if (user.getpassword().equals(edtPassword.getText().toString())) {
                                //Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                //{
                                    //Intent main = new Intent(SignIn.this, MainActivity.class);
                                    //startActivity(main);
                               // }
                            //}else{
                                    //Toast.makeText(SignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                                //}
                        //}else {
                           // mDialog.dismiss();
                            //Toast.makeText(SignIn.this, "User does not exist in database", Toast.LENGTH_SHORT).show();
                            //}
                        //}

                    //@Override
                    //public void onCancelled(DatabaseError databaseError) {

                    //}
                //});

            }


        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SignIn.this, SignUp.class));
                Intent signUp = new Intent(SignIn.this, SignUp.class);
                startActivity(signUp);
            }
        });

    }

    private void validate(String regNo, String password){
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        auth.signInWithEmailAndPassword(regNo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                }else{
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();

                }

            }
        });
        /*if((regNo.equals("regno")) && (password.equals("1234"))){
            Intent main = new Intent(SignIn.this, MainActivity.class);
            startActivity(main);
        }*/
    }
}

