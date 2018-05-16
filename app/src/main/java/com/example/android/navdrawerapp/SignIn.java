package com.example.android.navdrawerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.navdrawerapp.Model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText edtRegNo, edtUsername, edtPassword;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtRegNo = findViewById(R.id.edtRegNo);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user does not exist in the database
                        if (dataSnapshot.child(edtRegNo.getText().toString()).exists()) {

                            //get user information
                            mDialog.dismiss();
                            users user = dataSnapshot.child(edtRegNo.getText().toString()).getValue(users.class);
                            assert user != null;
                            if (user.getpassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                Intent main = new Intent(SignIn.this,MainActivity.class);
                                startActivity(main);
                            }else{
                                    Toast.makeText(SignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                                }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User does not exist in database", Toast.LENGTH_SHORT).show();
                            }
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


        });

    }

}

