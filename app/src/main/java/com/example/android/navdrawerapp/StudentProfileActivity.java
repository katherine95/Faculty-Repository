package com.example.android.navdrawerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Button studentpage = findViewById(R.id.student_create_profile);

        studentpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentclass = new Intent(StudentProfileActivity.this, BSITActivity.class);
                startActivity(studentclass);
            }
        });

    }
}
